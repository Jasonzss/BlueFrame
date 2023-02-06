package com.mvc_test.web;

import com.blue_core.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/06 - 18:48
 * @Description ：
 */
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
}
