package com.mvc_test.web;

import cn.hutool.core.net.multipart.UploadFile;
import com.blue_core.stereotype.Component;
import com.blue_core.web.bind.annotation.RequestMapping;
import com.blue_core.web.servlet.result_handler.ForwardHandler;
import com.blue_core.web.servlet.result_handler.RedirectHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/06 - 18:48
 * @Description ：
 */
@Component
@RequestMapping
public class YourController {
    @RequestMapping("/y4")
    public String test04(HttpServletResponse response, HttpServletRequest request){
        System.out.println(response.getCharacterEncoding());
        System.out.println(request.getRequestURI());
        return "yyyyyyyyyyyyyyyyyyyyyyy";
    }

    @RequestMapping
    public String y05(HttpServletResponse response, HttpServletRequest request){
        System.out.println(response.getCharacterEncoding());
        System.out.println(request.getRequestURI());
        return "xxxxxxxxxxxxxxxxxxxxxxx";
    }

    @RequestMapping
    public String y06(HttpServletResponse response, HttpServletRequest request){
        return RedirectHandler.REDIRECT_URI_PREFIX+"/y4";
    }

    @RequestMapping
    public String y07(){
        return ForwardHandler.FORWARD_URI_PREFIX +"/y05";
    }

    @RequestMapping
    public String y08(UploadFile uploadFile){
        System.out.println(uploadFile.getFileName());
        return ForwardHandler.FORWARD_URI_PREFIX +"/y05";
    }

    @RequestMapping
    public String y09(UploadFile uploadFile){
        try {
            uploadFile.write("D:\\Git\\gitCodes\\BlueFrame\\mvc_test\\src\\main\\resources");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return "122651w8d51aw5f61awf44";

        return ForwardHandler.FORWARD_URI_PREFIX +"/y05";
    }

    @RequestMapping
    public String y10(UploadFile uploadFile){
        System.out.println(uploadFile.getFileName());
        return ForwardHandler.FORWARD_URI_PREFIX +"/y08";
    }
}
