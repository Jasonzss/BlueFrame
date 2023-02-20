package com.blue_dot.web.servlet.result_handler;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 15:55
 * @Description ：
 */
public class ExcelResultHandler extends AbstractResultHandler{
    public static final String EXCEL = "application/msexcel";

    @Override
    public boolean support(Object result) {
        return result instanceof HSSFWorkbook;
    }

    @Override
    protected String getContentType() {
        return EXCEL;
    }

    @Override
    public void outputResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        try {
            ((HSSFWorkbook)result).write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setHeader(HttpServletResponse response) {
        // TODO 暂时无法修改文件名
        response.setHeader("content-disposition","attachment;filename=excel.xls");
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
