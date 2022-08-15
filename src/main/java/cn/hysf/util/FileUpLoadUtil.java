package cn.hysf.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileUpLoadUtil {
    public static void uploadFileTest(MultipartFile zipFile,String fileName) {
        String targetFilePath = "H:\\360MoveData\\Users\\LENOVO\\Desktop\\fashionShop\\src\\main\\resources\\static\\img\\goodsImg";
        File targetFile = new File(targetFilePath + File.separator + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(zipFile.getInputStream(), fileOutputStream);
        } catch (IOException e) {
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {

            }
        }

    }
}
