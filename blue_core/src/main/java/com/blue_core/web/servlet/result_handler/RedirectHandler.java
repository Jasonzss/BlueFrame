package com.blue_core.web.servlet.result_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 17:10
 * @Description ：重定向处理器，当处理器返回的结果为 redirect: 开头的String类的话，就会重定向到其后面的uri中
 */
public class RedirectHandler extends AbstractResultHandler{
    public static final String REDIRECT_URI_PREFIX = "redirect:";

    @Override
    public boolean support(Object result) {
        return result instanceof String && ((String) result).trim().startsWith(REDIRECT_URI_PREFIX);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    protected String getContentType() {
        return null;
    }

    @Override
    public void outputResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        String uri = ((String)result).trim().substring(REDIRECT_URI_PREFIX.length());
        try {
            response.sendRedirect(request.getContextPath()+uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
