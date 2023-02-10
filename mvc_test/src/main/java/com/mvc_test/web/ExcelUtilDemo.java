package com.mvc_test.web;

import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 17:02
 * @Description ：
 */
public class ExcelUtilDemo {
    /**
     * 标题信息 String(标题名) Pair(该标题名对应的子标题)
     * Pair(Integer,String) (该单元格的高度,子标题信息)
     */
    private static final List<Pair<String, List<Object>>> TITLE = new ArrayList<>();

    /**
     * 数据信息
     */
    private static final List<Pair<String,List<Object>>> VALUE_LIST = new ArrayList<>();


    private static final String BASE_INFO = "基本信息";
    private static final String ELECT_INFO = "电化学信息";
    private static final String POINT_INFO = "点位数据";

    /**
     * 初始化excel模板数据
     */
    private static void initTemplate(){
        // 基本信息
        List<Object> firstColTitle = new ArrayList<>();

        firstColTitle.add("实验数据id");
        firstColTitle.add("物质类型名");
        firstColTitle.add("物质名称");
        firstColTitle.add("创建时间");
        firstColTitle.add("最后修改时间");
        firstColTitle.add("用户邮箱");
        firstColTitle.add("实验数据说明");

        TITLE.add(new Pair<>(BASE_INFO,firstColTitle));

        // 电化学信息
        List<Object> secondColTitle = new ArrayList<>();

        secondColTitle.add("缓冲溶液名称");
        secondColTitle.add("原始电流");
        secondColTitle.add("原始电位");
        secondColTitle.add("最新电流");
        secondColTitle.add("最新电位");
        secondColTitle.add("PH");

        TITLE.add(new Pair<>(ELECT_INFO,secondColTitle));

        // 点位数据
        List<Object> thirdColTitle = new ArrayList<>();

        thirdColTitle.add("电位");
        thirdColTitle.add("原始电流");
//        thirdColTitle.add("最新电位");
        thirdColTitle.add("最新电流");

        TITLE.add(new Pair<>(POINT_INFO,thirdColTitle));

    }

    public static HSSFWorkbook getHSSFWorkbook(String sheetName, HSSFWorkbook workbook) {

        if (workbook == null) {
            workbook = new HSSFWorkbook();
            //初始化模板
            initTemplate();
        }

        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultRowHeightInPoints((short) (256 * 20));

        // 主标题行生成
        HSSFRow firstRow = sheet.createRow(0);
        HSSFCellStyle firstRowStyle = parentTitleStyle(workbook);

        // 子标题行生成
        HSSFRow secondRow = sheet.createRow(1);
        HSSFCellStyle secondRowStyle = childTitleStyle(workbook);

        //内容行生成
        HSSFRow thirdRow = sheet.createRow(2);
        HSSFCellStyle contentStyle = contentStyle(workbook);

        // 计数:列
        int num = 0;
        // 标题生成
        for (Pair<String, List<Object>> currentTitle : TITLE) {
            // 创建主标题单元格
            HSSFCell firstRowCell = firstRow.createCell(num);
            // 设置主标题内容
            firstRowCell.setCellValue(currentTitle.getKey());
            // 设置主标题样式
            firstRowCell.setCellStyle(firstRowStyle);

            // 子标题
            List<Object> currentChildTitles = currentTitle.getValue();
            int size = currentChildTitles.size();
            for (int j = 0; j < size; j++) {
                // 创建子标题单元格
                HSSFCell secondRowCell = secondRow.createCell(num + j);
                // 设置子标题内容
                secondRowCell.setCellValue((String) currentChildTitles.get(j));
                // 设置子标题样式
                secondRowCell.setCellStyle(secondRowStyle);
                // 设置子标题宽度
                sheet.setColumnWidth(secondRowCell.getColumnIndex(), 256 * 23);
            }

            // 合并主标题单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, num, num + size - 1));
            num += size;
        }

        return workbook;
    }

    /**
     * 主标题样式
     * @return 样式
     */
    private static HSSFCellStyle parentTitleStyle(HSSFWorkbook workbook){
        // 创建样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 横向居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 纵向居中
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 字体设置
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.PINK.index);
        font.setFontHeightInPoints((short) 30);
        font.setFontName("宋体");

        style.setFont(font);

        return style;
    }


    /**
     * 子标题样式
     * @return 样式
     */
    private static HSSFCellStyle childTitleStyle(HSSFWorkbook workbook){
        // 创建样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 横向居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 纵向居中
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 字体设置
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.GREEN.index);
        font.setFontHeightInPoints((short) 15);
        font.setFontName("黑体");

        style.setFont(font);

        return style;
    }

    /**
     * 内容样式
     * @return 样式
     */
    private static HSSFCellStyle contentStyle(HSSFWorkbook workbook){
        // 创建样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 横向居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 纵向居中
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 字体设置
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        font.setColor(HSSFColor.GREY_40_PERCENT.index);
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");

        style.setFont(font);

        return style;
    }
}

