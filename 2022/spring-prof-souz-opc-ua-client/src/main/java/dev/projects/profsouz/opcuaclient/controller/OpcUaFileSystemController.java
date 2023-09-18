package dev.projects.profsouz.opcuaclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.profsouz.opcuaclient.domain.XmlFilepathDTO;
import dev.projects.profsouz.opcuaclient.domain.request.XmlFilepathRequestDTO;
import dev.projects.profsouz.opcuaclient.service.OpcUaFileSystemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(
        produces = "application/json",
        consumes = "application/json",
        value = "/opc-ua-fs-api")
public class OpcUaFileSystemController {

    @Autowired
    private OpcUaFileSystemService fsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Tag(name = "It's the create xml file endpoint", description = "Create xml file if it is needed")
    @PutMapping(value = "/createXmlFile")
    public ResponseEntity<XmlFilepathDTO> createTemplateXmlFile(@RequestBody XmlFilepathRequestDTO xmlFilepathRequestDTO) throws IOException {
        XmlFilepathDTO xmlFilepathDTO = fsService.createXmlFile(xmlFilepathRequestDTO.getXmlFilepath());

        return ResponseEntity.created(URI.create("/"+xmlFilepathDTO.getId())).body(xmlFilepathDTO);
    }

    @Tag(name = "It's the delete xml file endpoint", description = "Delete xml file if it is exists")
    @DeleteMapping(value = "/deleteXmlFile/{xmlFileId}")
    public ResponseEntity<XmlFilepathDTO> deleteTemplateXmlFile(@PathVariable("xmlFileId") Long xmlFileId) throws IOException {
        XmlFilepathDTO xmlFilepathDTO = fsService.deleteXmlFile(xmlFileId);

        //TODO handle other branches of alg

        return ResponseEntity.ok(xmlFilepathDTO);
    }

}
