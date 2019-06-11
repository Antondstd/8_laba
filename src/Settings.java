import java.net.DatagramSocket;
import java.net.InetAddress;

public class Settings {
    public static String email = new String();
    public static String password = new String();
    public static InetAddress address;
    public static DatagramSocket socket;
    public static byte[] buffer = new byte[3000];
    public static byte[] bufferResponce = new byte[3000];
    public static int port = 6879;
    public static String language;
}
