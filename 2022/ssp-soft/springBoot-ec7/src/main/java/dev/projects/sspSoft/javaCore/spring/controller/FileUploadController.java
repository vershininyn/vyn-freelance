package dev.projects.sspSoft.javaCore.spring.controller;

import dev.projects.sspSoft.javaCore.spring.service.FileUploadService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(
        produces = "application/json")
public class FileUploadController {
    @Autowired
    private FileUploadService uploadService;

    @Tag(name = "Контроллер \"Работа с файлом\"; URL - /file", description = "Выгружает на сервер файл переданный через тело запроса")
    @PostMapping(value = "/upload")
    @RequestBody
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {
        boolean result = uploadService.uploadFile(file);

        return (result)
                ? (new ResponseEntity<>(HttpStatus.OK))
                : (new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
