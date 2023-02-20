package com.blue_dot.web.multipart;

import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.net.multipart.UploadFile;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 该接口定义了为实际 Multipart Request 公开的 Multipart Request 访问操作。
 * 它由MultipartHttpServletRequest扩展。
 */
public interface MultipartRequest {
    //当前请求是否已经被包装过（从HttpServletRequest 包装成 MultipartRequest）
    public static final String HAS_RESOLVED = "hasResolved";

    /**
     * Return an {@link java.util.Iterator} of String objects containing the
     * parameter names of the multipart files contained in this request. These
     * are the field names of the form (like with normal parameters), not the
     * original file names.
     * 返回包含此请求中包含的多部分文件的参数名称的 String 对象的 Iterator。
     * 这些是表单的字段名称（与普通参数一样），而不是原始文件名。
     * @return the names of the files
     */
    Iterator<String> getFileNames();

    /**
     * Return the contents plus description of an uploaded file in this request,
     * or {@code null} if it does not exist.
     *
     * @param name a String specifying the parameter name of the multipart file
     * @return the uploaded content in the form of a {@link UploadFile} object
     */
    UploadFile getFile(String name);

    /**
     * Return the contents plus description of uploaded files in this request,
     * or an empty list if it does not exist.
     *
     * @param name a String specifying the parameter name of the multipart file
     * @return the uploaded content in the form of a {@link UploadFile} list
     * @since 3.0
     */
    List<UploadFile> getFiles(String name);

    /**
     * Return a {@link java.util.Map} of the multipart files contained in this request.
     *
     * @return a map containing the parameter names as keys, and the
     * {@link UploadFile} objects as values
     */
    Map<String, UploadFile[]> getFileMap();

    /**
     * Return a map of the multipart files contained in this request.
     *
     * @return a map containing the parameter names as keys, and a list of
     * {@link UploadFile} objects as values
     * @since 3.0
     */
    ListValueMap<String, UploadFile> getMultiFileMap();

    /**
     * Determine the content type of the specified request part.
     *
     * @param paramOrFileName the name of the part
     * @return the associated content type, or {@code null} if not defined
     * @since 3.1
     */
    String getMultipartContentType(String paramOrFileName);
}