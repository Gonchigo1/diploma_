package mn.astvision.starter.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.socket.dto.SocketDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsoSocketClient {

    @Value("${socket.url}")
    private String url;

    @Getter
    @Value("${socket.authKey}")
    private String authKey;

    private Socket socket;

    private final ObjectMapper om =
            new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @PostConstruct
    public void init() {
        log.info("Connecting to sso socket: {}", url);

        try {
            socket = IO
                    .socket(url + "/sso")
                    .connect();
            socket.on(Socket.EVENT_CONNECT, args -> log.info("SSO socket connected"));
            socket.on(Socket.EVENT_DISCONNECT, args -> log.info("SSO socket disconnected"));
        } catch (URISyntaxException e) {
            log.error("SSO socket connection error: {}", e.getMessage());
        }
    }

    @PreDestroy
    public void cleanup() {
        if (socket.connected()) {
            log.info("Disconnecting SSO socket");
            socket.disconnect();
        }
    }

    public void emit(String event, SocketDataDto payload) throws JsonProcessingException {
        if (!socket.connected())
            socket = socket.connect();

        payload.setAuthKey(authKey);
        String payloadStr = om.writeValueAsString(payload);

        log.info("{} emitting SSO event: {} with: {}", socket.connected(), event, payload.getClass());
        socket.emit(event, payloadStr, (Ack) args -> {
//            JSONObject response = (JSONObject) args[0];
            log.info("SSO emit response -> {}", args);
//            JSONObject response = (JSONObject) args[0];
//            log.info("Payment response -> " + response.toString());
        });
    }
}
