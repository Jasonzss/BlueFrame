package com.blue_dot.web.multipart.support;

import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.net.multipart.MultipartFormData;
import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Method;
import com.blue_dot.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.*;
import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/15 - 20:54
 * @Description ：由于这几个类太乱了所以干脆删了重新用hutool写
 */
@Deprecated
public abstract class AbstractMultipartHttpServletRequest extends HttpServletRequestWrapper
        implements MultipartHttpServletRequest {
    private final MultipartFormData formData;


    protected AbstractMultipartHttpServletRequest(HttpServletRequest request) {
        super(request);

        formData = ServletUtil.getMultipart(request);
        Set<String> fileParamNames = formData.getFileParamNames();
        fileParamNames.forEach((n) -> {
            request.setAttribute(n,formData.getFile(n));
        });
    }

    /*---------------------------重写MultipartHttpServletRequest相关方法-----------------------------*/

    @Override
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    @Override
    public Method getRequestMethod() {
        return Method.valueOf((getRequest()).getMethod());
    }

    @Override
    public Map<String, List<String>>   getRequestHeaders() {
        Map<String, List<String>>  headers = new HashMap<>();
        Enumeration<String> headerNames = (getRequest()).getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            headers.put(name, Collections.list((getRequest()).getHeaders(name)));
        }
        return headers;
    }

    @Override
    public Iterator<String> getFileNames() {
        return formData.getFileParamNames().iterator();
    }

    @Override
    public UploadFile getFile(String name) {
        return formData.getFile(name);
    }

    @Override
    public List<UploadFile> getFiles(String name) {
        return formData.getFileList(name);
    }

    @Override
    public Map<String, UploadFile[]> getFileMap() {
        return formData.getFileMap();
    }

    @Override
    public ListValueMap<String, UploadFile> getMultiFileMap() {
        return formData.getFileListValueMap();
    }

    public boolean isResolved() {
        return (this.formData != null);
    }
}
