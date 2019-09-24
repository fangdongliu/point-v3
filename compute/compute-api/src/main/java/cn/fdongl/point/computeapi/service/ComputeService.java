package cn.fdongl.point.computeapi.service;

import cn.fdongl.point.computeapi.exception.NoDataException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ComputeService {

    /**
     * 按照届计算指标点
     * @param grade 届数 egg:2016
     * @throws NoDataException 没有对应的数据
     */
    void computeByGrade(int grade, HttpServletResponse response) throws NoDataException, IOException;

    /**
     * 按照年份范围计算指标点
     * @param yearStart 开始年份 egg:2018
     * @param yearEnd 结束年份 egg:2019
     * @throws NoDataException 没有对应的数据
     */
    void computeByYear(int yearStart,int yearEnd,HttpServletResponse response) throws NoDataException, IOException;

}
