package dev.projects.sspSoft.javaCore.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {
    @Value(value = "app.fileUploadPath")
    private String path;

    public boolean uploadFile(MultipartFile file) {
        boolean result = true;

        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }

}
