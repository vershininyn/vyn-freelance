package dev.projects.profsouz.opcuaclient.domain.response;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WsMetaInfoResponseDTO {
    private String wsHost, wsPort;
    private Boolean isConnected;
    private UUID connectionUUID;
}
