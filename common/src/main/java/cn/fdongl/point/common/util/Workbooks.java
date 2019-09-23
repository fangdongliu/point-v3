package cn.fdongl.point.common.util;

import cn.fdongl.point.common.exception.WorkbookCastException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class Workbooks {

    public static Workbook of(MultipartFile file) throws WorkbookCastException {
        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String fileType = filename.substring(filename.lastIndexOf('.'));
            switch (fileType){
                case ".xls":
                    return new HSSFWorkbook(inputStream);
                case ".xlsx":
                    return new XSSFWorkbook(inputStream);
                default:
                    throw new WorkbookCastException();
            }
        } catch (IOException e) {
            throw new WorkbookCastException();
        }


    }

}
