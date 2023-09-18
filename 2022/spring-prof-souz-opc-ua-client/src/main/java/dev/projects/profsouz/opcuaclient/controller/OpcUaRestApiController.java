package dev.projects.profsouz.opcuaclient.controller;

import dev.projects.profsouz.opcuaclient.service.OpcUaFileSystemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        produces = "application/json",
        value = "/opc-ua-ws-api")
public class OpcUaRestApiController {
    @Autowired
    private OpcUaFileSystemService fsService;

    @Tag(name = "", description = "")
    @GetMapping(value = "/getValuesInfo")
    public ResponseEntity<HttpStatus> getValuesInfo() {
        return null;
    }
}
