package com.blue_core.web.multipart.hutool;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.LogFactory;
import com.blue_core.web.multipart.MultipartHttpServletRequest;
import com.blue_core.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Collection;

/**
 * @Author Jason
 * @CreationDate 2023/02/16 - 22:49
 * @Description ：
 */
public class HutoolMultipartResolver implements MultipartResolver {

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        return ServletUtil.isMultipart(request);
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) {
        return new HutoolMultipartHttpServletRequest(request);
    }

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
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
