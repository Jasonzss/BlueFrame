package com.blue_core.web.servlet.result_handler;

import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 14:27
 * @Description ：
 */
public class DefaultResultHandler extends AbstractResultHandler{
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    @Override
    public boolean support(Object result) {
        return true;
    }

    @Override
    protected String getContentType() {
        return DEFAULT_CONTENT_TYPE;
    }

    @Override
    public void outputResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPriority() {
        return 999;
    }
}
