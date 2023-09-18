package dev.projects.profsouz.opcuaclient.domain.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class WsMetaInfoRequestDTO {
    private String wsHost, wsPort;
}
