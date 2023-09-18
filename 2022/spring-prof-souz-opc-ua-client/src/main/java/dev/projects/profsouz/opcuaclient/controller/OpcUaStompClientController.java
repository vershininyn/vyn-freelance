package dev.projects.profsouz.opcuaclient.controller;

import dev.projects.profsouz.opcuaclient.domain.request.WsMetaInfoRequestDTO;
import dev.projects.profsouz.opcuaclient.domain.response.WsMetaInfoResponseDTO;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.UUID;

import static dev.projects.profsouz.opcuaclient.utils.OpcUaWsMetaInfoDTOMapper.mapWsMetaInfoToResponseDTO;
import static dev.projects.profsouz.opcuaclient.utils.OpcUaWsStompCheckUtil.checkWsHostAndPort;

@Slf4j
@Controller
@RequestMapping(
        produces = "application/json",
        consumes = "application/json",
        value = "/opc-ua-ws-api")
public class OpcUaStompClientController {
    /*@Autowired
    SocketProperties socketProperties; // TODO: Either implement this properties Bean or use hard-coded values
     */

    private StompSessionHandler stompSessionHandler = null;

    private StompSession stompSession = null;

    /**
     * Map of subscriptions.
     *
     * @Getter Map<String, StompSession.Subscription> subscriptions = new HashMap<>();
     */

    @PutMapping(value = "/connectToASNeGServer")
    public ResponseEntity<WsMetaInfoResponseDTO> connectToASNeGServer(@RequestBody WsMetaInfoRequestDTO requestDTO) throws Exception {
        checkWsHostAndPort(requestDTO.getWsHost(), requestDTO.getWsPort());

        //TODO: jetty web soekct client

        /*SockJsClient sockJsClient = new SockJsClient(List.of(new WebSocketTransport(
                new StandardWebSocketClient()),
                new RestTemplateXhrTransport()));

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = createWsURLFromHostAndPort(requestDTO.getWsHost(), requestDTO.getWsPort());

        stompSession = stompClient.connectAsync(url, getStompSessionHandler()).get(180, TimeUnit.SECONDS);*/

        //TODO: ws://localhost:57382/info

        WsMetaInfoResponseDTO responseDTO = mapWsMetaInfoToResponseDTO(UUID.randomUUID(),
                stompSession.isConnected(),
                requestDTO.getWsHost(),
                requestDTO.getWsPort());

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping(value = "/disconnectFromASNeGServer")
    public ResponseEntity<WsMetaInfoResponseDTO> disconnectFromASNeGServer(@RequestBody WsMetaInfoRequestDTO requestDTO) throws Exception {
        Objects.requireNonNull(stompSession, "Unacceptable stompSession. It's the null.");

        WsMetaInfoResponseDTO responseDTO = mapWsMetaInfoToResponseDTO(UUID.randomUUID(),
                stompSession.isConnected(),
                requestDTO.getWsHost(),
                requestDTO.getWsPort());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "/checkConnectionToASNeGServer")
    public ResponseEntity<Boolean> isConnected() throws Exception {
        if (stompSession == null) {
            throw new NullPointerException("Unacceptable stompSession. It's the null.");
        }

        return ResponseEntity.ok(stompSession.isConnected());
    }

    /**
     * Subscribes to the feed of the room.
     *
     * @param roomId The id of the room to subscribe for.
     */
    public void subscribe(String roomId) {
        // TODO: Check subscriptions if already exist and is subscribed.
        log.info("Subscribing to room: {}", roomId);
        //StompSession.Subscription subscription = stompSession.subscribe("/channel/" + roomId, this);
        //subscriptions.put(roomId, subscription);
    }

    public boolean isSubscribed(String roomId) {
       /* return subscriptions
                .containsKey(roomId);*/

        return false;
    }

    public void unsubscribe(String roomId) {
        /*subscriptions
                .get(roomId))
                .unsubscribe();
        subscriptions.remove(roomId);*/
    }

    /**
     * Unsubscribe and close connection before destroying this instance (e.g. on application shutdown).
     */
    @PreDestroy
    void onShutDown() {
        /*for (String key : subscriptions.keySet()) {
            subscriptions.get(key).unsubscribe();
        }*/

        if (stompSession != null) {
            stompSession.disconnect();
        }
    }

    private StompSessionHandler getStompSessionHandler() {
        if (stompSessionHandler == null) {
            stompSessionHandler = new StompSessionHandler() {
                @Override
                public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
                    log.info("Connected to the server. [session id : {}]", stompSession.getSessionId());
                }

                @Override
                public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
                    log.error("Exception in session handler. [session id : {}, exception : {}]", stompSession.getSessionId(), throwable);
                }

                @Override
                public void handleTransportError(StompSession stompSession, Throwable throwable) {
                    log.error("Transport error in th session handler. [session id : {}, exception : {}]", stompSession.getSessionId(), throwable);
                }

                @Override
                public Type getPayloadType(StompHeaders stompHeaders) {
                    return Object.class;
                }

                @Override
                public void handleFrame(StompHeaders stompHeaders, Object o) {
                    //OutputMessage outputMessage = (OutputMessage) o;
                    log.info("handle Frame: "+ o.toString());
                }

            };
        }
        return stompSessionHandler;
    }
}
