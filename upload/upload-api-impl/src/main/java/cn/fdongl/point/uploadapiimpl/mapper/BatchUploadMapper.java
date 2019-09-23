package cn.fdongl.point.uploadapiimpl.mapper;

import cn.fdongl.point.common.entity.MapStudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchUploadMapper {


    @Insert("<script>" +
          "     insert ignore into map_student_course (id, create_from, course_name, \n" +
            "      course_number, course_semester, create_by, \n" +
            "      create_date, modified_by, modify_date, \n" +
            "      statuz, sub_course_semester, work_id, \n" +
            "      real_name)\n" +
            "    values <foreach collection=\"list\" item=\"item\" separator=\",\">\n" +
            "(#{item.id,jdbcType=BIGINT}, #{item.createFrom,jdbcType=BIGINT}, #{item.courseName,jdbcType=VARCHAR}, \n" +
            "      #{item.courseNumber,jdbcType=VARCHAR}, #{item.courseSemester,jdbcType=BIGINT}, #{item.createBy,jdbcType=BIGINT}, \n" +
            "      #{item.createDate,jdbcType=TIMESTAMP}, #{item.modifiedBy,jdbcType=BIGINT}, #{item.modifyDate,jdbcType=TIMESTAMP}, \n" +
            "      #{item.statuz,jdbcType=BIGINT}, #{item.subCourseSemester,jdbcType=BIGINT}, #{item.workId,jdbcType=VARCHAR}, \n" +
            "      #{item.realName,jdbcType=VARCHAR})"+
            "</foreach></script>")
    int insertStudentCourseIgnore(Iterable<MapStudentCourse> list);

    @Insert("<script>" +
            "     insert into map_student_course (id, create_from, course_name, \n" +
            "      course_number, course_semester, create_by, \n" +
            "      create_date, modified_by, modify_date, \n" +
            "      statuz, sub_course_semester, work_id, \n" +
            "      real_name)\n" +
            "    values <foreach collection=\"list\" item=\"item\" separator=\",\">\n" +
            "(#{item.id,jdbcType=BIGINT}, #{item.createFrom,jdbcType=BIGINT}, #{item.courseName,jdbcType=VARCHAR}, \n" +
            "      #{item.courseNumber,jdbcType=VARCHAR}, #{item.courseSemester,jdbcType=BIGINT}, #{item.createBy,jdbcType=BIGINT}, \n" +
            "      #{item.createDate,jdbcType=TIMESTAMP}, #{item.modifiedBy,jdbcType=BIGINT}, #{item.modifyDate,jdbcType=TIMESTAMP}, \n" +
            "      #{item.statuz,jdbcType=BIGINT}, #{item.subCourseSemester,jdbcType=BIGINT}, #{item.workId,jdbcType=VARCHAR}, \n" +
            "      #{item.realName,jdbcType=VARCHAR})"+
            "</foreach></script>")
    int insertStudentCourse(Iterable<MapStudentCourse> list);

}
