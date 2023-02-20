package com.blue_dot.web.multipart.hutool;

import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.net.multipart.MultipartFormData;
import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Method;
import com.blue_dot.web.multipart.MultipartHttpServletRequest;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/16 - 22:51
 * @Description ：使用hutool实现的 MultipartHttpServletRequest
 */
public class HutoolMultipartHttpServletRequest extends HttpServletRequestWrapper implements MultipartHttpServletRequest {
    private final MultipartFormData formData;

    public HutoolMultipartHttpServletRequest(HttpServletRequest request) {
        super(request);

        //将parameter中的文件取出，包装成 UploadFile 后放入到Attribute中
        formData = ServletUtil.getMultipart(request);
        Set<String> fileParamNames = formData.getFileParamNames();
        fileParamNames.forEach((n) -> {
            request.setAttribute(n,formData.getFile(n));
        });

        //设置包装成功（请求已被解析为 MultipartHttpServletRequest）参数
        request.setAttribute(HAS_RESOLVED,true);
    }

    @Override
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    @Override
    public Method getRequestMethod() {
        return Method.valueOf(getRequest().getMethod());
    }

    @Override
    public Map<String, List<String>> getRequestHeaders() {
        Map<String, List<String>>  headers = new HashMap<>();
        Enumeration<String> headerNames = (getRequest()).getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            headers.put(name, Collections.list((getRequest()).getHeaders(name)));
        }
        return headers;
    }

    @Override
    public Map<String, List<String>> getMultipartHeaders(String paramOrFileName) {
        UploadFile[] files = formData.getFiles(paramOrFileName);
        Map<String, List<String>> headers = new HashMap<>();

        for (UploadFile file : files){
            headers.put(file.getFileName(),
                    Lists.newArrayList(
                            file.getHeader().getFileName(),
                            file.getHeader().getMimeType(),
                            file.getHeader().getContentType(),
                            file.getHeader().getFormFileName(),
                            file.getHeader().getContentDisposition(),
                            file.getHeader().getFormFieldName(),
                            file.getHeader().getMimeSubtype()
                    ));
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
        return Arrays.asList(formData.getFiles(name));
    }

    @Override
    public Map<String, UploadFile[]> getFileMap() {
        return formData.getFileMap();
    }

    @Override
    public ListValueMap<String, UploadFile> getMultiFileMap() {
        return formData.getFileListValueMap();
    }

    @Override
    public String getMultipartContentType(String paramOrFileName) {
        return formData.getFile(paramOrFileName).getHeader().getContentType();
    }
}
