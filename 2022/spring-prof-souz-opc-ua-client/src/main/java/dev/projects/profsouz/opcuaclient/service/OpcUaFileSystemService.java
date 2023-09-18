package dev.projects.profsouz.opcuaclient.service;

import dev.projects.profsouz.opcuaclient.domain.XmlFilepathDTO;
import dev.projects.profsouz.opcuaclient.entity.OpcUaXmlFilepathEntity;
import dev.projects.profsouz.opcuaclient.repository.OpcUaFileSystemJpaRepository;
import dev.projects.profsouz.opcuaclient.utils.OpcUaFileSystemObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class OpcUaFileSystemService {
    private OpcUaFileSystemJpaRepository xmlFilepathsRepository;

    @Autowired
    public OpcUaFileSystemService(OpcUaFileSystemJpaRepository repository) {
        xmlFilepathsRepository = repository;
    }

    public XmlFilepathDTO createXmlFile(String xmlPath) throws IOException {
        Path xmlFilepath = Path.of(xmlPath);

        Long id = -1L;

        if (!Files.exists(xmlFilepath)) {
            Files.createFile(xmlFilepath);

            OpcUaXmlFilepathEntity entity = OpcUaXmlFilepathEntity.builder()
                    .xmlFilepath(xmlFilepath.getFileName().toString())
                    .xmlFilename(xmlFilepath.toAbsolutePath().toString())
                    .build();

            id = xmlFilepathsRepository.save(entity).getId();
        }

        return OpcUaFileSystemObjectMapper.mapFromStringToXmlFilepathDTO(xmlPath, id);
    }

    public XmlFilepathDTO deleteXmlFile(Long xmlFileId) throws IOException, EntityNotFoundException {
        Optional<OpcUaXmlFilepathEntity> optionalEntity = xmlFilepathsRepository.findById(xmlFileId);

        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Unacceptable id=" + xmlFileId + ".Not found.");
        }

        OpcUaXmlFilepathEntity entity = optionalEntity.get();

        XmlFilepathDTO result = XmlFilepathDTO.builder()
                .xmlFilepath(entity.getXmlFilepath())
                .xmlFilename(entity.getXmlFilename())
                .id(xmlFileId)
                .isExists(true)
                .build();

        Path xmlPath = Path.of(result.getXmlFilepath());

        if (Files.exists(xmlPath)) {
            Files.delete(xmlPath);

            xmlFilepathsRepository.deleteById(xmlFileId);

            result.setIsExists(false);
        }

        return result;
    }
}
