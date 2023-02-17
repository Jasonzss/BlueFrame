package com.blue_core.web.multipart;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jason
 * @CreationDate 2023/02/15 - 17:47
 * @Description ：multipart文件上传解析的策略接口
 * Spring中主要有两个实现类，一个是基于 Servlet 3.0+ API 的 StandardServletMultipartResolver
 * 一个是基于 Apache Commons FileUpload 的 CommonsMultipartResolver
 * 这里就只简单实现一个 StandardServletMultipartResolver
 *
 * 注意，Spring中的 MultipartResolver 默认是不启用的，需要在配置文件中配置相应的MultipartResolver实现类才会使用
 * 不过这个自实现的我为了方便默认设置为启用MultipartResolver
 */
public interface MultipartResolver {
    /**
     * 确定给定的请求是否包含 multipart。
     * 通常会检查内容类型“multipart/form-data”，但实际接受的请求可能取决于解析器实现的能力。
     * @param request 给定的请求
     * @return 请求是否包含 multipart
     */
    boolean isMultipart(HttpServletRequest request);

    /**
     *
     * @param request 解析请求包装成 multipart 文件上传请求
     */
    MultipartHttpServletRequest resolveMultipart(HttpServletRequest request);

    /**
     * 释放上传文件过程中使用到的资源
     * @param request 带有Multipart文件的请求
     */
    void cleanupMultipart(MultipartHttpServletRequest request);
}
