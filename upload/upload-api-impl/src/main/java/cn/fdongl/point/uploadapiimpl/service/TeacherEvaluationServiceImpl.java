package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.*;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.SheetHelper;
import cn.fdongl.point.common.util.Workbooks;
import cn.fdongl.point.uploadapi.service.TeacherEvaluationService;
import cn.fdongl.point.uploadapiimpl.repository.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherEvaluationServiceImpl implements TeacherEvaluationService {

    @Autowired
    MapTeacherCourseRepository mapTeacherCourseRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    IndexPointRepository indexPointRepository;

    @Autowired
    CourseInfoRepository courseInfoRepository;

    @Autowired
    MapCourseIndexRepository mapCourseIndexRepository;

    @Autowired
    TeacherEvaluationRepository teacherEvaluationRepository;


    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return PageResult.ofPage(mapTeacherCourseRepository.findAllByWorkId(jwtUser.getUsername(),pageable));
    }

    @Override
    @Transactional
    public int evaluate(MultipartFile multipartFile, long id, JwtUser jwtUser) throws Exception {
        Workbook workbook = Workbooks.of(multipartFile);
        Sheet sheet = getSheet(workbook);
        SheetHelper sheetHelper = new SheetHelper(sheet);
        List<String>indices = getIndices(sheetHelper);
        List<Double>maxes = getMaxes(sheetHelper);
        List<Double>vals = getVals(sheetHelper);
        MapTeacherCourse mapTeacherCourse = mapTeacherCourseRepository.findById(id).get();
        teacherEvaluationRepository.deleteAllByCreateByAndCourseNumberAndSemester(jwtUser.getId(),mapTeacherCourse.getCourseNumber(),mapTeacherCourse.getSemester());
        String courseNumber = mapTeacherCourse.getCourseNumber();
        Long semester = mapTeacherCourse.getSemester();
        Course course = courseRepository.findByNumberAndSemester(courseNumber,semester);
        CourseInfo courseInfo = courseInfoRepository.findFirstByGradeBeforeAndNumberOrderByGrade(course.getGrade(),course.getNumber());
        List<MapCourseIndex> ids = mapCourseIndexRepository.findAllByCourseId(courseInfo.getId());
        Map<String,IndexPoint>indexPointMap = indexPointRepository.findAllById(ids.stream()
                .map(MapCourseIndex::getIndexId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(i->i.getParentIndex()+"."+i.getChildIndex(),i->i,(a,b)->a));
        List<MapTeacherEvaluation>mapTeacherEvaluations = new ArrayList<>();
        for(int i=indices.size()-1;i>=0;i--){
            String s = indices.get(i);
            MapTeacherEvaluation mapTeacherEvaluation = new MapTeacherEvaluation();
            IndexPoint indexPoint = indexPointMap.get(s.substring(0,s.indexOf(' ')));
            if(indexPoint == null){
                throw new Exception();
            }
            mapTeacherEvaluation
                    .setChildIndex(indexPoint.getChildIndex())
                    .setParentIndex(indexPoint.getParentIndex())
                    .setCourseNumber(courseNumber)
                    .setSemester(mapTeacherCourse.getSemester())
                    .setVal(vals.get(i))
                    .setMaz(maxes.get(i));
            mapTeacherEvaluations.add(mapTeacherEvaluation);
        }
        teacherEvaluationRepository.saveAll(mapTeacherEvaluations);
        mapTeacherCourse.setStatuz(1L);
        mapTeacherCourseRepository.save(mapTeacherCourse);
        return mapTeacherEvaluations.size();
    }

    private List<Double> getVals(SheetHelper sheetHelper) {
        return sheetHelper.filterNull(sheetHelper.collectColumnValues(-3,1,i->Double.valueOf(i.toString())));
    }

    private List<Double> getMaxes(SheetHelper sheetHelper) {
        return sheetHelper.filterNull(sheetHelper.collectColumnValues(1,1,i->Double.valueOf(i.toString())));
    }

    private List<String> getIndices(SheetHelper sheetHelper) {
        return sheetHelper.filterNull(sheetHelper.collectColumnValues(0,String::valueOf));
    }

    private Sheet getSheet(Workbook workbook) throws Exception {
        for(int i=0,numOfSheet = workbook.getNumberOfSheets();i<numOfSheet;i++){
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getSheetName().contains("级评价表")){
                return sheet;
            }
        }
        throw new Exception();
    }
}
