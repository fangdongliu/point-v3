package cn.fdongl.point.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("cn.fdongl.point.common.entity")
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}

// 从 course 中 根据 grade 与 semester 找到 所有 course
// 从 course courseInfo 中 使用 grade 与 courseNumber 找到这些 courseInfo
// 对于 基础课，用 courseNumber 与 grade 进行聚合统计，
// 其他的，用 courseNumber 与 grade 到老师评价表中查找 分数
// 得到一系列课程指标点对应关系，找到所有课程指标点对应项。找到所有指标点
// 导出

// Excel 导入
// 先保存文件，得到文件ID

// 培养方案 与 培养实现矩阵
// 同时导入，同时解析
// 每个 grade 只有 一组 培养方案 / 实现矩阵，上传会导致覆盖
// 解析培养方案，并保存
// 解析实现矩阵，并保存指标点。
// 利用上述2个结果产生一批 映射 存入 MapCourseIndex

// 开课信息导入
// 文件按 grade 唯一，上传会导致覆盖
// 产生一批Course对象

// 课程执行计划导入
// 文件按grade 唯一，上传会导致覆盖
// 产生一批MapTeacherCourse对象

// 老师信息导入
// 可以选择重复数据是否覆盖

// 学生选课信息导入
// 可以选择重复数据是否覆盖
// 提取所有学生信息并保存
// 提取所有选课信息并保存



// 老师评价
// 会覆盖之前的评价
// 传入键值为 courseId
// 后端根据courseId 得到对应的 MapTeacherCourse 得到 courseNumber 与 grade
// 用 courseNumber 与 grade 找到 对应的mapCourseIndex，存studentCourse即可
// 存入的指标点评价 courseNumber 与 grade 作为key