import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTCP {
        public static void main(String args[]) {
            String[] tempArgs = new String[1];
            tempArgs[0] = "4999";
            final int MAX_CLIENT_CAPACITY = 250;
            boolean quit = false;

            if (tempArgs.length == 0)
                System.out.println("Wprowadz numer portu, na ktorym" + "serwer bpdzie oczekiwai na klientOw");
            else {
                int port = 0;
                try {
                    port = Integer.parseInt(tempArgs[0]);
                }catch (NumberFormatException e) {
                    System.err.println("Wprowadz poprawny numer portu: " + e);
                return;
                }

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            int i = 0;
            while (!quit) {
                if(ServerTCPThread.connectedClients < MAX_CLIENT_CAPACITY) {
                    ServerTCPThread thread = new ServerTCPThread(serverSocket.accept());
                    i++;
                    ServerTCPThread.connectedClients++;
                    System.out.println("polaczono klienta nr: " + i);
                    thread.start();
                }else System.out.println("Wszystkie miejsca zajete");
          }


          }catch (Exception e)
            { System.err.println(e); }
        }
            server.close();
}
}