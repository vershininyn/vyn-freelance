package dev.projects.sspsoft.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiShipUserEntity {
    private String id;
    private String companyId;
    private String login;
    private String email;
    private List<String> roles;
    private String notificationUrl;
    private boolean useDraft;
    private String offerAccepted;
    private LocalDateTime created;
    private LocalDateTime updated;
}
