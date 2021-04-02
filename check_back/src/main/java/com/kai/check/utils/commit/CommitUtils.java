package com.kai.check.utils.commit;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CommitUtils {

    public static boolean commitWorkToFile(MultipartFile workFile, String workPath) {
        File file = new File(workPath);
        if (file.exists()) {
            file.delete();
        }
        try (
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(workFile.getInputStream());
        ) {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return !file.exists();
    }

}
