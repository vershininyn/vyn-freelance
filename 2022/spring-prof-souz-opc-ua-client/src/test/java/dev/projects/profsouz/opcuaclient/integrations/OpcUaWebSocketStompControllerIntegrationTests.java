package dev.projects.profsouz.opcuaclient.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.profsouz.opcuaclient.controller.OpcUaStompClientController;
import dev.projects.profsouz.opcuaclient.domain.request.WsMetaInfoRequestDTO;
import org.hibernate.validator.constraints.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test"})
@Testcontainers(disabledWithoutDocker = true)
public class OpcUaWebSocketStompControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OpcUaStompClientController opcUaStompController;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static DockerComposeContainer environment = new DockerComposeContainer(new File("src/test/resources/docker-compose-testcontainers.yml"))
            .withPull(true)
            .withExposedService("asneg-demo", 8889, Wait.forListeningPort())
            .withExposedService("webserver", 8081, Wait.forListeningPort())
            .withLocalCompose(true);

    @Test
    public void connectToASNeGServer_usingCorrectData_andCorrectHandleIt_whenDisconnect() throws Exception {
        String host = environment.getServiceHost("webserver", 8081),
                port = String.valueOf(environment.getServicePort("webserver", 8081));

        WsMetaInfoRequestDTO requestDTO = WsMetaInfoRequestDTO.builder()
                .wsHost(host)
                .wsPort(port)
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/opc-ua-ws-api/connectToASNeGServer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.wsHost", is(host)))
                .andExpect(jsonPath("$.wsPort", is(port)))
                .andExpect(jsonPath("$.isConnected", is(true)))
                .andExpect(jsonPath("$.connectionUUID", isA(UUID.class)));
    }
}
