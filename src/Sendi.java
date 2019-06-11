import java.io.Serializable;
import java.util.List;

public class Sendi implements Serializable {

    private static final long serialVersionUID = 1;

    String fromServer;
    String fromClient;
    String code;
    List<Human> humanList;
    int iteration;
}
