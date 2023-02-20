package com.blue_dot.web.servlet.result_handler;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 16:03
 * @Description ：
 */
public class RenderedImageResultHandler extends AbstractResultHandler {
    public static final String BUFFERED_IMAGE = "image/png";

    @Override
    public boolean support(Object result) {
        return result instanceof RenderedImage;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void outputResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        try {
            ImageIO.write(((RenderedImage) result),"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getContentType() {
        return BUFFERED_IMAGE;
    }
}
