package dev.projects.profsouz.opcuaclient.repository;

import dev.projects.profsouz.opcuaclient.entity.OpcUaXmlFilepathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcUaFileSystemJpaRepository extends JpaRepository<OpcUaXmlFilepathEntity, Long> {

}
