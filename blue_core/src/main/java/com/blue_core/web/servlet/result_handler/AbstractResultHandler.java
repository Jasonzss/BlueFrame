package com.blue_core.web.servlet.result_handler;

import com.blue_core.beans.BeansException;
import com.blue_core.web.servlet.ResultHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 15:07
 * @Description ：
 */
public abstract class AbstractResultHandler implements ResultHandler {
    public static final String DEFAULT_CHARACTER_ENCODING = "GBK";

    @Override
    public void handleResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        if (support(result)){
            response.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
            setHeader(response);
            response.setContentType(getContentType());

            outputResult(result,request,response);
        }else{
            throw new BeansException("不支持的处理的结果类型："+result.getClass());
        }

        try {
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setHeader(HttpServletResponse response){

    }

    protected abstract String getContentType();

    public abstract void outputResult(Object result, HttpServletRequest request, HttpServletResponse response);

    /**
     * 优先级数字越小代表优先级越小
     */
    @Override
    public int compareTo(ResultHandler o) {
        return this.getPriority() - o.getPriority();
    }
}
