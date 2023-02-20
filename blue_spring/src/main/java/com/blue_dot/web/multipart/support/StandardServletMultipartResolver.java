package com.blue_dot.web.multipart.support;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.LogFactory;
import com.blue_dot.web.multipart.MultipartHttpServletRequest;
import com.blue_dot.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Collection;

/**
 * @Author Jason
 * @CreationDate 2023/02/15 - 20:23
 * @Description ：使用 Servlet Api 实现的上传文件解析器，负责解析用户的请求并包装成 StandardMultipartHttpServletRequest 类
 * 使用了策略模式，在Spring核心容器加载解析器时，会根据上下文选择不同的策略。
 *
 * 由于这几个类太乱了所以干脆删了重新用hutool写
 */
@Deprecated
public class StandardServletMultipartResolver implements MultipartResolver {

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        return ServletUtil.isMultipart(request);
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) {
        return new StandardMultipartHttpServletRequest(request);
    }

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        if (!(request instanceof AbstractMultipartHttpServletRequest) ||
                ((AbstractMultipartHttpServletRequest) request).isResolved()) {
            // To be on the safe side: explicitly delete the parts,
            // but only actual file parts (for Resin compatibility)
            try {
                Collection<Part> parts = request.getParts();
                for (Part part : parts) {
                    if (request.getFile(part.getName()) != null) {
                        part.delete();
                    }
                }
            }catch (Throwable ex) {
                LogFactory.get(getClass()).warn("Failed to perform cleanup of multipart items", ex);
            }
        }
    }
}
