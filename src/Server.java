import ReceivingConnections.Receiver;
import RequestReader.FileReceiver;
import java.io.IOException;
public class Server {
    public static void main(String args[]) throws ClassNotFoundException {
        FileReceiver  fileReceiver = new FileReceiver();
        fileReceiver.XMLConvertation();
        Receiver rc = new Receiver();
        try {
            rc.receiveObject();
        }catch (IOException e) {
        }
    }
}
