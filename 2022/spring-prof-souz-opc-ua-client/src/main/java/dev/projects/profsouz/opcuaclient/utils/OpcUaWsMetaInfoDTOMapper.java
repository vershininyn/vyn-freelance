package dev.projects.profsouz.opcuaclient.utils;

import dev.projects.profsouz.opcuaclient.domain.response.WsMetaInfoResponseDTO;

import java.util.UUID;

public class OpcUaWsMetaInfoDTOMapper {

    public static WsMetaInfoResponseDTO mapWsMetaInfoToResponseDTO(UUID clientUUID, Boolean isConnected, String wsHost, String wsPort) {
        return WsMetaInfoResponseDTO.builder()
                .wsPort(wsPort)
                .wsHost(wsHost)
                .isConnected(isConnected)
                .connectionUUID(clientUUID)
                .build();
    }

}
