package com.blue_core.web.multipart.support;

import com.blue_core.beans.BeansException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/15 - 20:29
 * @Description ：由于这几个类太乱了所以干脆删了重新用hutool写
 */
@Deprecated
public class StandardMultipartHttpServletRequest extends AbstractMultipartHttpServletRequest{

    private Set<String> multipartParameterNames;

    protected StandardMultipartHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    /**
     * 获取 MultipartHeaders
     */
    @Override
    public Map<String, List<String>> getMultipartHeaders(String paramOrFileName) {
        try {
            Part part = getPart(paramOrFileName);
            if (part != null) {
                Map<String, List<String>> headers = new HashMap<>();
                for (String headerName : part.getHeaderNames()) {
                    headers.put(headerName, new ArrayList<>(part.getHeaders(headerName)));
                }
                return headers;
            }else {
                return null;
            }
        }catch (Throwable ex) {
            throw new BeansException("Could not access multipart servlet request", ex);
        }
    }

    /**
     * 获取 MultipartContentType
     */
    @Override
    public String getMultipartContentType(String paramOrFileName) {
        try {
            Part part = getPart(paramOrFileName);
            return (part != null ? part.getContentType() : null);
        }catch (Throwable ex) {
            throw new BeansException("Could not access multipart servlet request", ex);
        }
    }
}
