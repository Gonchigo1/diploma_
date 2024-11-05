package mn.astvision.starter.socket;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.net.URISyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentSocketClient {

    @Value("${socket.url}")
    private String url;

    @Getter
    @Value("${socket.authKey}")
    private String authKey;

    private Socket socket;

    @PostConstruct
    public void init() {
        log.info("Connecting to payment socket: " + url);

        try {
            socket = IO
                    .socket(url + "/payment")
                    .connect();
            socket.on(Socket.EVENT_CONNECT, args -> log.info("Payment socket connected"));
            socket.on(Socket.EVENT_DISCONNECT, args -> log.info("Payment socket disconnected"));
        } catch (URISyntaxException e) {
            log.error("Payment socket connection error: " + e.getMessage());
        }
    }

    @PreDestroy
    public void cleanup() {
        if (socket.connected()) {
            log.info("Disconnecting payment socket");
            socket.disconnect();
        }
    }

    public void emit(String event, Object payload) {
        if (!socket.connected())
            socket = socket.connect();

        log.info(socket.connected() +
                " emitting payment event: " + event +
                " with: " + payload.getClass() + " -> " + payload);
        socket.emit(event, new JSONObject(payload), (Ack) args -> {
            log.info("Payment emit response -> " + args);
//            JSONObject response = (JSONObject) args[0];
//            log.info("Payment response -> " + response.toString());
        });
    }
}
