package dev.projects.profsouz.opcuaclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class XmlFilepathDTO {
    private Long id;
    private String xmlFilename;
    private String xmlFilepath;
    private Boolean isExists;
}
