import com.google.gson.Gson;
import jakarta.websocket.Decoder;

public class MessageDecoder implements Decoder.Text<Message> {

    private final static Gson gson = new Gson();

    @Override
    public Message decode(String s) {
        return gson.fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void destroy() {
        // Close resources
    }
}