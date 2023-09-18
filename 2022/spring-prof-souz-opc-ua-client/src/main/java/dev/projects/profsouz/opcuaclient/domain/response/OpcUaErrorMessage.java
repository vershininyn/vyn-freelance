package dev.projects.profsouz.opcuaclient.domain.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OpcUaErrorMessage {
    private int statusCode;
    private String message;
    private long timestamp;
}
