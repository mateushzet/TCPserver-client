import java.io.*;
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
            FileReader frQ = new FileReader("bazaPytan.txt");
            BufferedReader brQ = new BufferedReader(frQ);
            String question;

            //odbieranie od klienta
            InputStreamReader in = new InputStreamReader(mySocket.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            //zapisywanie odpowiedzi do pliku od klienta
            FileWriter fwA = new FileWriter("bazaOdpowiedzi.txt", true);
            BufferedWriter bwA = new BufferedWriter(fwA);

            String answer;

            pw.println("Podaj imie i nazwisko: ");
            pw.flush();

            while((answer = bf.readLine()) == null){}
            System.out.println(answer);

            bwA.append(answer + System.lineSeparator());

            while((question = brQ.readLine()) != null){
                pw.println(question);
                pw.flush();

                    Thread.sleep(5000);
                    if((answer = bf.readLine()) == null) {
                        pw.println("Czas na odpowiedz sie skonczyl");
                        pw.flush();
                    }
            //    while((answer = bf.readLine()) == null){}
                System.out.println(answer);

                bwA.append(answer + System.lineSeparator());

            }
            frQ.close();
            bwA.close();




        }catch (Exception e){
            System.err.println(e);
        }
    }
}
