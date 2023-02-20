package com.blue_dot.web.servlet.result_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 16:36
 * @Description ：
 */
public class FileResultHandler extends AbstractResultHandler{
    public static final String FILE = "text/plain";

    @Override
    public boolean support(Object result) {
        return result instanceof File || result instanceof InputStream;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    protected String getContentType() {
        return FILE;
    }

    @Override
    protected void setHeader(HttpServletResponse response) {
        response.setHeader("content-disposition","attachment");
    }

    @Override
    public void outputResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        byte[] bytes = new byte[1024];
        InputStream is;

        try{
            if (result instanceof File){
                is = new FileInputStream((File) result);
            }else {
                is = (InputStream) result;
            }

            while (is.read(bytes) != -1){
                response.getOutputStream().write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
