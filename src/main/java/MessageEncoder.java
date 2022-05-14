import com.google.gson.Gson;
import jakarta.websocket.Encoder;


public class MessageEncoder implements Encoder.Text<Message> {

    private static final Gson gson = new Gson();

    @Override
    public String encode(Message message) {
        return gson.toJson(message);
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
