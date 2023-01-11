import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ServerTCPThread extends Thread{
    Socket mySocket;
    static int connectedClients = 0;
    int questionCount = 0;

    public ServerTCPThread(Socket socket)
    {
        super();
        mySocket = socket;
    }

    public void run() {
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

            //odczytanie poprawnej odpowiedzi
            FileReader frCA = new FileReader("poprawneOdpowiedzi.txt");
            BufferedReader brCA = new BufferedReader(frCA);
            String correctAnswer;

            //zapis wyników
            FileWriter fwR = new FileWriter("wyniki.txt", true);
            BufferedWriter bwR = new BufferedWriter(fwR);
            int punkty = 0;
            String name;

            pw.println("Podaj imie i nazwisko: ");
            pw.flush();

            while((answer = bf.readLine()) == null){}
            System.out.println(answer);

            bwA.append(answer + System.lineSeparator());
            name = answer;

            while((question = brQ.readLine()) != null){
                questionCount++;
                pw.println(question);
                pw.flush();

                Instant now = Instant.now();
                Instant now2 = now.plus(5, ChronoUnit.SECONDS);

                while((answer = bf.readLine()) == null){}

                Instant later = Instant.now();

                var duration = Duration.between(now, later);
                var MAX_RESPONSE_TIME = Duration.between(now, now2);
                correctAnswer = brCA.readLine();
                if(duration.compareTo(MAX_RESPONSE_TIME)>0){
                    pw.println("timeError");
                    pw.flush();
                    bwA.append("brak odpowiedzi" + System.lineSeparator());
                }else {
                    bwA.append(answer + System.lineSeparator());
                    if(answer.equals(correctAnswer)){
                        punkty++;
                    }
                }
                    System.out.println(answer);
            }
            pw.println("Ilosc zdobytych punktow " + punkty +"/"+questionCount + ", klikni enter aby zakonczyc");
            pw.flush();
            while((answer = bf.readLine()) == null){}

            bwR.append(name + " " + punkty + System.lineSeparator());
            brQ.close();
            bwA.close();
            brCA.close();
            bwR.close();

            mySocket.close();
            connectedClients--;

        }catch (Exception e){
            System.err.println(e);
        }
    }
}
