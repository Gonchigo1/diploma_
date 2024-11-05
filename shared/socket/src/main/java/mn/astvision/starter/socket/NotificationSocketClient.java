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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

/**
 * Notification - Client
 *
 * @author Maroon
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSocketClient {

    @Value("${socket.url}")
    private String url;

    @Value("${socket.authKey}")
    private String authKey;

    private Socket socket;

    private final ObjectMapper om =
            new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @PostConstruct
    public void init() {
        try {
            log.info("Connecting to socket: " + url + "/notification");
            socket = IO
                    .socket(url + "/notification")
                    .connect();
            socket.on(Socket.EVENT_CONNECT, args -> log.info("Notification socket connected"));
            socket.on(Socket.EVENT_DISCONNECT, args -> log.info("Notification socket disconnected"));
        } catch (URISyntaxException e) {
            log.error("Notification socket connection error: " + e.getMessage());
        }
    }

    @PreDestroy
    public void cleanup() {
        if (socket.connected()) {
            log.info("Disconnecting notification socket");
            socket.disconnect();
        }
    }

    public void emit(String event, Object payload) throws JsonProcessingException {
        if (!socket.connected())
            socket = socket.connect();

        String payloadStr = om.writeValueAsString(payload);
        log.info(socket.connected() + " -> notification emitting event: " + event + " with: " + payloadStr);
        socket.emit(event, payloadStr, (Ack) args -> {
            JSONObject response = (JSONObject) args[0];
            log.info("notification response -> " + response.toString());
        });
    }

    public String getAuthKey() {
        return authKey;
    }
}
