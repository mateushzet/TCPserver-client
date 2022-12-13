import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
        public static void main(String args[]) {
            String[] tempArgs = new String[1];
            tempArgs[0] = "4999";
            Socket[] clientSocket = new Socket[250];

            if (tempArgs.length == 0)
                System.out.println("WprowadZ numer portu, na ktorym" + "serwer bpdzie oczekiwai na klientOw");
            else {
                int port = 0;
                try {
                    port = Integer.parseInt(tempArgs[0]);
                }catch (NumberFormatException e) {
                    System.err.println("Wprowad poprawny numer portu: " + e);
                return;
                }
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            int i = 0;
            while (true) {
             clientSocket[i] = serverSocket.accept();
              System.out.println("polaczono klienta nr: " + i);
                ServerTCPThread thread = new ServerTCPThread(clientSocket[i]);
                thread.start();
              i++;
          }


          }catch (Exception e)
            { System.err.println(e); }
        }
}
}