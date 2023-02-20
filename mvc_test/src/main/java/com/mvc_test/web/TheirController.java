package com.mvc_test.web;

import com.blue_dot.stereotype.Component;
import com.blue_dot.test.TestEntity1;
import com.blue_dot.web.bind.annotation.RequestMapping;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 15:31
 * @Description ：
 */
@Component
@RequestMapping
public class TheirController {
    @RequestMapping("/te1")
    public TestEntity1 defaultResultHandler(){
        return new TestEntity1("你好MVC",15);
    }

    @RequestMapping("/te2")
    public File file(HttpServletResponse response, HttpServletRequest request){
        return new File("D:\\Git\\gitCodes\\BlueFrame\\mvc_test\\src\\main\\resources\\ddd.txt");
    }

    @RequestMapping("/te3")
    public HSSFWorkbook excel(HttpServletResponse response, HttpServletRequest request){
        return ExcelUtilDemo.getHSSFWorkbook("demo",new HSSFWorkbook());
    }

    @RequestMapping("/te4")
    public BufferedImage image(HttpServletResponse response, HttpServletRequest request){
        return ImageUtilDemo.createAuthImage("hello world");
    }

    @RequestMapping("/te5")
    public BufferedImage uploadFile(){
        return ImageUtilDemo.createAuthImage("hello world");
    }
}

