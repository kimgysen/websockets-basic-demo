import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value="/chat/{username}",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class})
public class ChatServerEndpoint {

    private Session session;
    private final static Set<ChatServerEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private final static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(
            Session session) {
        this.session = session;

        String username = session.getRequestParameterMap().get("username").get(0);
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        Message msg = new Message();
        msg.setFrom(users.get(session.getId()));
        msg.setContent(message);
        broadcast(msg);
    }

    @OnClose
    public void onClose(Session session) {
        chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendObject(message.toString());
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}