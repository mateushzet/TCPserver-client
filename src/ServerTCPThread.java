import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTCPThread extends Thread{
    Socket mySocket;

    public ServerTCPThread(Socket socket)
    {
        super();
        mySocket = socket;
    }

    public void run()
    {
        try{

            //przesylanie do klienta
            PrintWriter pw = new PrintWriter(mySocket.getOutputStream());

            //czytanie pytan przez serwer
            FileReader frQ = new FileReader("questions.txt");
            BufferedReader brQ = new BufferedReader(frQ);
            String question;

            //odbieranie od klienta
            InputStreamReader in = new InputStreamReader(mySocket.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str;

            while((question = brQ.readLine()) != null){
                pw.println(question);
                pw.flush();

                while((str = bf.readLine()) == null){}
                System.out.println(str);

            }




        }catch (Exception e){
            System.err.println(e);
        }
    }
}
