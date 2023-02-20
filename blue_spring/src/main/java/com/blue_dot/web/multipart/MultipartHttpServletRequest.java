package com.blue_dot.web.multipart;

import cn.hutool.http.Method;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Provides additional methods for dealing with multipart content within a
 * servlet request, allowing to access uploaded files.
 * Implementations also need to override the standard
 * {@link javax.servlet.ServletRequest} methods for parameter access, making
 * multipart parameters available.
 * 提供额外的方法来处理 servlet 请求中的 Multipart Content，从而允许操作上传的文件。
 * 实现类还需要重写用于参数访问的标准 ServletRequest 方法，使 multipart 的参数可用。
 *
 * <p>A concrete implementation is DefaultMultipartHttpServletRequest
 * As an intermediate step,AbstractMultipartHttpServletRequest can be subclassed.
 *
 *
 * 用于处理表单中携带文件的请求的特殊包装类
 */
public interface MultipartHttpServletRequest extends HttpServletRequest,MultipartRequest {
    /**
     * Return this request's method as a convenient HttpMethod instance.
     * 将此请求的方法作为方便的 HttpMethod 实例返回。
     */
    Method getRequestMethod();

    /**
     * Return this request's headers as a convenient HttpHeaders instance.
     * 将此请求的标头作为方便的 HttpHeaders 实例返回。
     */
    Map<String, List<String>> getRequestHeaders();

    /**
     * Return the headers associated with the specified part of the multipart request.
     * <p>If the underlying implementation supports access to headers, then all headers are returned.
     * Otherwise, the returned headers will include a 'Content-Type' header at the very least.
     *
     * 返回与multipart请求的指定部分关联的标头。
     * <p>如果底层实现支持访问标头，则返回所有标头。
     * 否则，返回的标头将至少包含一个“Content-Type”标头。
     */
    Map<String, List<String>>  getMultipartHeaders(String paramOrFileName);
}
