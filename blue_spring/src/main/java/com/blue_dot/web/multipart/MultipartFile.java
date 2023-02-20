package com.blue_dot.web.multipart;

import cn.hutool.core.io.IoUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Author Jason
 * @CreationDate 2023/02/15 - 18:29
 * @Description ：Spring中使用此类提供给用户操作上传文件使用，不过此项目使用hutool的uploadFile替代了
 */
@Deprecated
public interface MultipartFile {

    /**
     * 返回 multipart form 中的文件参数名称，文件不存在时返回null，有文件但没指定名称则返回空字符串
     */
    String getName();

    /**
     * 返回客户端系统内原始文件的名称，有可能会包含文件路径信息，这取决于浏览器
     * 文件不存在时返回null，有文件但没指定名称则返回空字符串
     */
    String getOriginalFilename();

    /**
     * 返回文件的 ContentType，文件不存在时返回null
     */
    String getContentType();

    /**
     * 返回上传的文件是否是空的，也就是说，在 multipart form 中没有选择文件，或者选择的文件没有内容
     */
    boolean isEmpty();

    /**
     * 返回文件bytes的大小
     */
    long getSize();

    /**
     * 返回文件的字节数组
     */
    byte[] getBytes() throws IOException;

    /**
     * 返回文件的字节流
     */
    InputStream getInputStream() throws IOException;

    /**
     * 将接收到的文件传输到给定的目标文件。
     *
     * 这可能会在文件系统中移动文件，在文件系统中复制文件，或者将内存保存的内容保存到目标文件。
     * 如果目标文件已经存在，它将首先被删除。
     *
     * dest – 目标文件（通常是绝对路径的文件）
     */
    void transferTo(File dest) throws IOException, IllegalStateException;

    /**
     * 将接收到的文件传输到给定的目标文件。
     * 默认实现只是复制文件输入流。
     */
    default void transferTo(Path dest) throws IOException, IllegalStateException {
        IoUtil.copy(getInputStream(), Files.newOutputStream(dest));
    }
}
