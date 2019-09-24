package cn.fdongl.point.computeapiimpl.service;

import cn.fdongl.point.common.entity.Course;
import cn.fdongl.point.common.entity.CourseInfo;
import cn.fdongl.point.common.entity.IndexPoint;
import cn.fdongl.point.common.entity.MapCourseIndex;
import cn.fdongl.point.computeapi.exception.NoDataException;
import cn.fdongl.point.computeapi.service.ComputeService;
import cn.fdongl.point.computeapiimpl.mapper.ComputeMapper;
import cn.fdongl.point.computeapiimpl.vo.Evaluation;
import cn.fdongl.point.computeapiimpl.vo.Evaluation2;
import cn.fdongl.point.uploadapiimpl.repository.CourseInfoRepository;
import cn.fdongl.point.uploadapiimpl.repository.CourseRepository;
import cn.fdongl.point.uploadapiimpl.repository.IndexPointRepository;
import cn.fdongl.point.uploadapiimpl.repository.MapCourseIndexRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jboss.jandex.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ComputeServiceImpl implements ComputeService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseInfoRepository courseInfoRepository;

    @Autowired
    MapCourseIndexRepository mapCourseIndexRepository;

    @Autowired
    IndexPointRepository indexPointRepository;

    @Autowired
    ComputeMapper computeMapper;

    @Override
    @Transactional
    public void computeByGrade(int grade, HttpServletResponse response) throws NoDataException, IOException {
        List<Course> list = courseRepository.findAllByGrade(grade);
        List<Evaluation> student = computeMapper.getStudentEvaluationByGrade(grade);
        List<Evaluation2> teacher = computeMapper.getTeacherEvaluationByGrade(grade);
        String name = grade + "-result";
        buildResult(name,list, teacher, student, response);
    }

    private void buildResult(String name,List<Course> list, List<Evaluation2> teacher, List<Evaluation> student, HttpServletResponse response) throws NoDataException, IOException {
        if(list.isEmpty()){
            throw new NoDataException("没有课程",null);
        }
        long grade = getMinGrade(list);
        List<CourseInfo>courseInfos = courseInfoRepository.getMatrixCourse(grade);
        if (courseInfos == null){
            throw new NoDataException("没有相应的培养实现矩阵",null);
        }
        grade = courseInfos.get(0).getGrade();
        List<MapCourseIndex> ids = mapCourseIndexRepository.findAllByCourseIdIn(courseInfos.stream().map(CourseInfo::getId).collect(Collectors.toList()));
        List<IndexPoint> indexPoints = indexPointRepository.findAllById(ids.stream()
                .map(MapCourseIndex::getIndexId).collect(Collectors.toList()));
//                .stream().collect(Collectors.toMap(i->i.getParentIndex()+"."+i.getChildIndex(),i->i,(a,b)->a));
        Map<String,Evaluation2> teacherValue = teacher.stream().collect(Collectors.toMap(i->i.getNumber()+'-'+i.getSemester()+'-'+i.getParentIndex()+'-'+i.getChildIndex(),i->i,(a,b)->{
            a.setVal(b.getVal()+a.getVal());
            a.setCount(b.getCount()+a.getCount());
            return a;
        }));
        Map<String,Evaluation> studentValue = student.stream().collect(Collectors.toMap(i->i.getNumber()+'-'+i.getSemester()+'-'+i.getParentIndex()+'-'+i.getChildIndex(),i->i,(a,b)->{
            a.setVal(b.getVal()+a.getVal());
            a.setCount(b.getCount()+a.getCount());
            return a;
        }));
        List<List<IndexPoint>>header= indexPoints.stream().collect(Collectors.groupingBy(IndexPoint::getParent)).values().stream().sorted((a,b)->(int)(a.get(0).getParentIndex() - b.get(0).getParentIndex()))
        .collect(Collectors.toList());
        HSSFWorkbook workbook = buildWorkbook(header,teacherValue,studentValue,list,ids);
        response.setContentType("application/vnd.ms-excel");
        System.out.println(name);
        String fileName = name;
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        response.setContentType("application/x-download; charset=UTF-8");

        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();

    }

    private HSSFWorkbook buildWorkbook(List<List<IndexPoint>> header, Map<String, Evaluation2> teacherValue, Map<String, Evaluation> studentValue, List<Course> courses, List<MapCourseIndex> ids){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle headStyle = createHeadStyle(workbook);
        HSSFCellStyle leftStyle = createLeftStyle(workbook);
        HSSFCellStyle leftbarStyle = createLeftBarStyle(workbook);
        HSSFCellStyle valueStyle = createValueStyle(workbook);
        Map<String,MapCourseIndex>mapCourseIndexMap = ids.stream().collect(Collectors.toMap(i->i.getParentIndex()+"."+i.getChildIndex(),i->i,(a,b)->a));
        for (List<IndexPoint> indexPoints : header) {
            indexPoints.sort((a,b)->(int)(a.getChildIndex() - b.getChildIndex()));
            IndexPoint indexPoint = indexPoints.get(0);
            Map<IndexPoint,Map<String,Double>>summary = new HashMap<>();
            List<List<String>>table = buildSheetTable(summary,teacherValue,studentValue,courses,indexPoints,mapCourseIndexMap);
            HSSFSheet sheet = workbook.createSheet(indexPoint.getParent().substring(indexPoint.getParent().indexOf('\n')).replaceAll("/",","));

            fillHeader(sheet,indexPoints,headStyle,leftStyle);
            fillIndex(sheet,indexPoints,leftbarStyle);
            fillContent(sheet,table,leftbarStyle,valueStyle);
            HSSFRow rowLast = sheet.createRow(table.size()+3);
            fillLastLine(rowLast,valueStyle,summary);
        }
        return workbook;
    }

    private void fillLastLine(HSSFRow rowLast, HSSFCellStyle valueStyle, Map<IndexPoint, Map<String, Double>> summary) {
        List<String>lastLine = new ArrayList<>();
        lastLine.add("评价合计");
        for (Map<String, Double> v : summary.entrySet().stream().
                sorted((a,b)->(int)(a.getKey().getChildIndex() - b.getKey().getChildIndex())).
                map(Map.Entry::getValue).collect(Collectors.toList())) {
            double sum = 0.0;
            for (Double aDouble : v.values()) {
                sum += aDouble;
            }
            lastLine.add(String.format("%.2f",sum));
        }
        rowLast.setHeight((short) 0x400);
        for(int i=0;i<lastLine.size();i++){
            HSSFCell cell = rowLast.createCell(i);
            cell.setCellValue(lastLine.get(i));
            cell.setCellStyle(valueStyle);
        }
    }

    private void fillContent(HSSFSheet sheet, List<List<String>> table, HSSFCellStyle leftbarStyle, HSSFCellStyle valueStyle) {
        for(int i=0;i<table.size();i++){
            HSSFRow row = sheet.createRow(i+3);
            row.setHeight((short) 0x400);
            List<String> d = table.get(i);
            for(int j = 0;j < d.size();j++){
                HSSFCell cell = row.createCell(j);
                if(j==0){
                    cell.setCellStyle(leftbarStyle);
                } else{
                    cell.setCellStyle(valueStyle);
                }
                cell.setCellValue(d.get(j));
            }
        }
    }

    private void fillIndex(HSSFSheet sheet, List<IndexPoint> indexPoints, HSSFCellStyle leftbarStyle) {
        HSSFRow row = sheet.createRow(2);
        row.setHeight((short) 0x400);
        for(int i=1;i<=indexPoints.size();i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(indexPoints.get(i-1).getContent());
            cell.setCellStyle(leftbarStyle);
        }
        for(int i=0;i<=indexPoints.size();i++){
            sheet.setColumnWidth(i,256*30);
        }
    }

    private void fillHeader(HSSFSheet sheet, List<IndexPoint> indexPoints, HSSFCellStyle headStyle, HSSFCellStyle leftStyle) {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, indexPoints.size()));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, indexPoints.size()));
        String parent = indexPoints.get(0).getParent();
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 0x400);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("课程毕业要求达成度评价表");
        cell.setCellStyle(headStyle);
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(parent);
        cell.setCellStyle(leftStyle);
        row.setHeight((short) 0x400);
    }

    private List<List<String>>buildSheetTable(Map<IndexPoint, Map<String, Double>> summary, Map<String, Evaluation2> teacherValue, Map<String, Evaluation> studentValue, List<Course> courses, List<IndexPoint> indexPoints, Map<String, MapCourseIndex> mapCourseIndexMap) {
        List<List<String>> table = new ArrayList<>();
        for (Course course: courses) {
            List<String>line = new ArrayList<>();
            boolean flag = false;
            line.add(course.getName()+'\n'+course.getSemester());
            for (IndexPoint indexPoint : indexPoints) {
                Map<String,Double> m = summary.get(indexPoint);
                MapCourseIndex mapCourseIndex = mapCourseIndexMap.get(indexPoint.getParentIndex()+"."+indexPoint.getChildIndex());
                if(mapCourseIndex == null){
                    line.add(null);
                    continue;
                }
                flag = true;
                String suffix = "("+mapCourseIndex.getVal()+")";
                if (m == null){
                    m = new HashMap<>();
                    summary.put(indexPoint,m);
                }
                String key = course.getNumber()+'-'+course.getSemester()+'-'+indexPoint.getParentIndex()+'-'+indexPoint.getChildIndex();
                Evaluation2 evaluation2 = teacherValue.get(key);
                Evaluation evaluation;

                if (evaluation2!=null){
                    Double val = evaluation2.getVal() / evaluation2.getCount();
                    Double lastVal = m.get(course.getNumber());
                    if (lastVal == null || lastVal > val){
                        m.put(course.getNumber(),val);
                    }
                    line.add(String.format("%.2f",val)+suffix);
                } else if ((evaluation = studentValue.get(key))!=null){
                    Double val = ((double)evaluation.getVal()) / evaluation2.getCount() / 4.0;
                    Double lastVal = m.get(course.getNumber());
                    if (lastVal == null || lastVal > val){
                        m.put(course.getNumber(),val);
                    }
                    line.add(String.format("%.2f",val)+suffix);
                } else {
                    line.add("0" + suffix);
                }
            }
            if(flag) {
                table.add(line);
            }
        }
        return table;
    }

    private long getMinGrade(List<Course> list) {
        long ans = list.get(0).getGrade();
        for (Course course : list) {
            if(ans > course.getGrade()){
                ans = course.getGrade();
            }
        }
        return ans;
    }


    HSSFCellStyle createHeadStyle(HSSFWorkbook workbook){
        HSSFCellStyle style1 = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor((short)0x0000);
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        style1.setFont(font);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setWrapText(true);
        return style1;
    }
    HSSFCellStyle createLeftStyle(HSSFWorkbook workbook){
        HSSFCellStyle style= workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor((short)0x0000);
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }
    HSSFCellStyle createLeftBarStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellstyle = workbook.createCellStyle();
        Font font3 = workbook.createFont();
        font3.setColor((short)0x0000);
        font3.setFontName("宋体");
        font3.setFontHeightInPoints((short) 11);
        cellstyle.setFont(font3);
        cellstyle.setAlignment(HorizontalAlignment.LEFT);
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellstyle.setWrapText(true);
        return cellstyle;
    }
    HSSFCellStyle createValueStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellvaluestyle = workbook.createCellStyle();
        Font font4 = workbook.createFont();
        font4.setColor((short)0x0480);
        font4.setFontName("宋体");
        font4.setFontHeightInPoints((short) 11);
        cellvaluestyle.setFont(font4);
        cellvaluestyle.setAlignment(HorizontalAlignment.CENTER);
        cellvaluestyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellvaluestyle.setWrapText(true);
        return cellvaluestyle;
    }

    @Override
    @Transactional
    public void computeByYear(int yearStart, int yearEnd,HttpServletResponse response) throws NoDataException, IOException {
        List<Course> list = courseRepository.findAllBySemesterBetween(yearStart,yearEnd);
        List<Evaluation2> teacher = computeMapper.getTeacherEvaluationByYear(yearStart,yearEnd);
        List<Evaluation> student = computeMapper.getStudentEvaluationByYear(yearStart,yearEnd);
        String name = yearStart+"-"+yearEnd + "-result";
        buildResult(name,list,teacher,student,response);
    }




}
