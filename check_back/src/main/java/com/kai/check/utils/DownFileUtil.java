package com.kai.check.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DownFileUtil {

    public static void downFile(String fileUrl, String fileName, HttpServletResponse response) {
        if (fileUrl == null || fileUrl.equals("")) {
            try {
                response.setHeader("content-disposition", "attachment" + ";filename=" + URLEncoder.encode("", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return;
        }
        try (
                ServletOutputStream outputStream = response.getOutputStream();
                FileInputStream inputStream = new FileInputStream(fileUrl);
        ) {
            response.setHeader("content-disposition", "attachment" + ";filename=" + URLEncoder.encode(fileName, "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
