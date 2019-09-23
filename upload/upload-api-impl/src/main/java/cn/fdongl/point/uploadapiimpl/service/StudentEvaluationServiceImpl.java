package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.*;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.uploadapi.service.StudentEvalutionService;
import cn.fdongl.point.uploadapi.vo.StudentEvaluation;
import cn.fdongl.point.uploadapiimpl.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentEvaluationServiceImpl implements StudentEvalutionService {

    @Autowired
    MapStudentCourseRepository mapStudentCourseRepository;

    @Autowired
    StudentEvaluationRepository studentEvaluationRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    IndexPointRepository indexPointRepository;

    @Autowired
    CourseInfoRepository courseInfoRepository;

    @Autowired
    MapCourseIndexRepository mapCourseIndexRepository;

    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return PageResult.ofPage(mapStudentCourseRepository.findAllByWorkId(jwtUser.getUsername(),pageable));
    }

    @Override
    @Transactional
    public int evaluate(StudentEvaluation evaluation, JwtUser jwtUser) {
        MapStudentCourse mapStudentCourse = mapStudentCourseRepository.findFirstByCourseNumberAndWorkIdAndCourseSemester(
                evaluation.getCourseNumber(),
                jwtUser.getUsername(),
                evaluation.getCourseSemester());
        if(mapStudentCourse.getStatuz() == 1){
            /*
             * 已评价
             */
            return 0;
        }
        mapStudentCourse.setStatuz(1L);
        mapStudentCourseRepository.save(mapStudentCourse);
        Map<Long,IndexPoint> indices = indexPointRepository.findAllById(
                evaluation.getEvaluations().stream().map(
                        StudentEvaluation.Evaluation::getIndexId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(IndexPoint::getId,i->i,(a,b)->a));
        studentEvaluationRepository.saveAll(evaluation.getEvaluations().stream().map(i->{
            IndexPoint indexPoint = indices.get(i.getIndexId());
            return new MapStudentEvaluation()
                    .setChildIndex(indexPoint.getChildIndex())
                    .setParentIndex(indexPoint.getParentIndex())
                    .setCourseNumber(evaluation.getCourseNumber())
                    .setGrade(jwtUser.getGrade())
                    .setVal(i.getEvaluationValue());
        }).collect(Collectors.toList()));

        return evaluation.getEvaluations().size();
    }

    @Override
    public List points(long studentCourseId, JwtUser jwtUser) throws Exception {
        Optional<MapStudentCourse> studentCourse = mapStudentCourseRepository.findById(studentCourseId);
        if(studentCourse.isPresent()){
            MapStudentCourse val = studentCourse.get();
            Course course = courseRepository.findByNumberAndSemester(val.getCourseNumber(),val.getCourseSemester());
            CourseInfo courseInfo = courseInfoRepository.findFirstByGradeBeforeAndNumberOrderByGrade(course.getGrade(),course.getNumber());
            List<MapCourseIndex> ids = mapCourseIndexRepository.findAllByCourseId(courseInfo.getId());
            return indexPointRepository.findAllById(ids.stream().map(MapCourseIndex::getIndexId).collect(Collectors.toList()));
        } else {
            throw new Exception();
        }
    }
}
