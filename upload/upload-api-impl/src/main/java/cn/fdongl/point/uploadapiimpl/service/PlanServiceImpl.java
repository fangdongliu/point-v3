package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.*;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.SheetHelper;
import cn.fdongl.point.common.util.Workbooks;
import cn.fdongl.point.uploadapi.service.FileService;
import cn.fdongl.point.uploadapi.service.PlanService;
import cn.fdongl.point.uploadapiimpl.repository.CourseInfoRepository;
import cn.fdongl.point.uploadapiimpl.repository.IndexPointRepository;
import cn.fdongl.point.uploadapiimpl.repository.MapCourseIndexRepository;
import cn.fdongl.point.uploadapiimpl.repository.PlanRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.jboss.jandex.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    MapCourseIndexRepository mapCourseIndexRepository;

    @Autowired
    IndexPointRepository indexPointRepository;

    @Autowired
    CourseInfoRepository courseInfoRepository;

    @Autowired
    FileService fileService;

    @Override
    @Transactional
    public int upload(MultipartFile plan, MultipartFile matrix, int grade, JwtUser jwtUser) throws WorkbookCastException, IOException {
        File planFile = fileService.save(plan,"plan");
        File matrixFile = fileService.save(matrix,"matrix");
        Workbook matrixWorkbook = Workbooks.of(matrix);
        SheetHelper sheetHelper = new SheetHelper(matrixWorkbook.getSheetAt(2),3);
        List<List<Object>>lines = sheetHelper.collectLines(4,-2,0,-2);
        courseInfoRepository.deleteAllByGrade(grade);
        indexPointRepository.deleteAllByGrade(grade);
        mapCourseIndexRepository.deleteAllByGrade(grade);
        String []header = getHeader(sheetHelper);
        List<IndexPoint>indexPoints = toIndexPoints(sheetHelper,header);
        List<CourseInfo>courseInfos = getCourseInfos(lines);
        Plan p = new Plan()
                .setGrade((long)grade)
                .setMatrixId(matrixFile.getId())
                .setPlanId(planFile.getId())
                .setPlanFilename(plan.getOriginalFilename())
                .setMatrixFilename(matrix.getOriginalFilename());
        planRepository.save(p);
        for (IndexPoint indexPoint : indexPoints) {
            indexPoint.setGrade((long) grade);
            indexPoint.setCreateFrom(p.getId());
        }
        for (CourseInfo courseInfo : courseInfos) {
            courseInfo.setGrade((long) grade);
            courseInfo.setCreateFrom(p.getId());
        }
        courseInfoRepository.saveAll(courseInfos);
        indexPointRepository.saveAll(indexPoints);
        List<MapCourseIndex>mapCourseIndices = getMapCourseIndices(lines,indexPoints,courseInfos);
        for (MapCourseIndex mapCourseIndex : mapCourseIndices) {
            mapCourseIndex.setGrade((long) grade);
            mapCourseIndex.setCreateFrom(p.getId());
        }


        mapCourseIndexRepository.saveAll(mapCourseIndices);
        return 0;
    }

    private List<MapCourseIndex> getMapCourseIndices(List<List<Object>> lines, List<IndexPoint> indexPoints, List<CourseInfo> courseInfos) {
        List<MapCourseIndex>mapCourseIndices = new ArrayList<>();
        for (int i = lines.size() - 1;i>=0;i--) {
            List<Object>line = lines.get(i);
            for(int j = line.size() - 2; j > 1;j--){
                Object o = line.get(j);
                if(o!=null){
                    MapCourseIndex mapCourseIndex = new MapCourseIndex();
                    mapCourseIndex.setCourseId(courseInfos.get(i).getId());
                    mapCourseIndex.setIndexId(indexPoints.get(j-2).getId());
                    mapCourseIndex.setVal(Double.valueOf(o.toString()));
                    mapCourseIndices.add(mapCourseIndex);
                }
            }
        }
        return mapCourseIndices;
    }

    private List<IndexPoint> toIndexPoints(SheetHelper sheetHelper, String[] header) {
        List<Object>line =
                sheetHelper.filterNull(sheetHelper.collectLines(3,4,0,-2).get(0));
        return line.stream().map(i->{
            String s = i.toString();
            int dot = s.indexOf('.');
            int space = s.indexOf(' ');
            Long parentIndex = Long.valueOf(s.substring(0,dot));
            Long index = Long.valueOf(s.substring(dot+1,space));
            return new IndexPoint()
                    .setParentIndex(parentIndex)
                    .setChildIndex(index)
                    .setParent(header[parentIndex.intValue()-1])
                    .setContent(s.substring(space+1));
        }).collect(Collectors.toList());
    }

    private String[] getHeader(SheetHelper sheetHelper) {
        List<List<Object>>headers = sheetHelper.collectLines(1,3,2,sheetHelper.getColCount());
        List<String> h1 = sheetHelper.filterNull(headers.get(0)).stream().map(Object::toString).collect(Collectors.toList());
        List<String> h2 = sheetHelper.filterNull(headers.get(1)).stream().map(Object::toString).collect(Collectors.toList());
        String [] h = new String[h1.size()];
        for(int i = h1.size()-1;i>=0;i--){
            h[i] = h1.get(i) + '\n' + h2.get(i);
        }
        return h;
    }

    private List<CourseInfo> getCourseInfos(List<List<Object>> lines) {
        return lines.stream().map(i-> new CourseInfo()
                .setNumber(
                        i.get(0) instanceof String ?
                                (String)i.get(0):
                                String.valueOf(((Double)i.get(0)).longValue()))
                .setName(i.get(1).toString())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int deleteByFile(long id, JwtUser jwtUser) {
        Optional<Plan> plan = planRepository.findById(id);
        plan.ifPresent(p -> {
            long createFrom = p.getId();
            mapCourseIndexRepository.deleteAllByCreateFrom(createFrom);
            indexPointRepository.deleteAllByCreateFrom(createFrom);
            courseInfoRepository.deleteAllByCreateFrom(createFrom);
            planRepository.deleteById(id);
        });
        return 0;
    }

    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return PageResult.ofPage(planRepository.findAll(pageable));
    }
}
