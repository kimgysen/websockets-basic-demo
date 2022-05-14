import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Message {
    private String from;
    private String content;

    //standard constructors, getters, setters
}