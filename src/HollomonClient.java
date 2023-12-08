

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HollomonClient {
private String server;
private InputStream is;
private OutputStream os;
//private List<Card> card;
BufferedWriter writer;
private int port;
private CardInputStream cis;
private List<Card> array =  new ArrayList<Card>();
private Socket socket;
    public HollomonClient(String serverarg, int portarg) throws UnknownHostException, IOException {
        this.server = serverarg;
        this.port = portarg;
        Socket socket = new Socket(this.server, this.port);
        this.socket = socket;
         this.is = socket.getInputStream();
         this.os = socket.getOutputStream();
         
         cis = new CardInputStream(is);
        
    }
    

    
    public List<Card> login(String username, String password) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(this.os));

        String correct = new String( "User " + username + " logged in successfully.");
        try {
            //in here write to server to verify login
        writer.write(username.toLowerCase() + "\n");
        writer.write(password + "\n");
        //writer.write("\r\n");
        writer.flush();
        String temp = new String();
        Card tempcard;
        while ((temp = cis.readResponse())!=null) {
            
            if (temp.contentEquals(correct)) {
                //temp = cis.readResponse();
                while ((tempcard = cis.readCard()) != null) {
                
                    array.add(tempcard);
                    continue;
                }
                Collections.sort(array);
                return array;
                
            
            } else {
                continue;
            }
            
        }
        return null;
        } catch (UnknownHostException e) {
            System.out.println("DIDNT CONNECT TO SERVER");
            return null;
          } 
        }
    
    public long getCredits() throws IOException {
        String temp;
        long val;
    //maybe make a WriteResponse function in CIS that created a writer  
        writer = new BufferedWriter(new OutputStreamWriter(this.os));
        writer.write("CREDITS\n");
        writer.flush();
        
        if ((temp = cis.readResponse()) != "OK") {
        val = Long.parseLong(temp);
        return val;
        }
        return 0;
    }
    
    public List<Card> getCards() throws IOException {
        List<Card> cardArray = new ArrayList<Card>();
        writer = new BufferedWriter(new OutputStreamWriter(this.os));
        writer.write("CARDS\n");
        writer.flush();
        Card card;
        while ((card = cis.readCard()) != null) {
            
            cardArray.add(card);
            continue;
        }
        Collections.sort(cardArray);
        return cardArray;
        
    }
    
    public List<Card> getOffers() throws IOException {
        List<Card> offercards = new ArrayList<Card>();
        writer = new BufferedWriter(new OutputStreamWriter(this.os));
        writer.write("OFFERS\n");
        writer.flush();

        Card card;
        while ((card = cis.readCard()) != null) {
            System.out.println(card);
            offercards.add(card);
            continue;
        }
        
        Collections.sort(offercards);
        return offercards;
        
    }
    
    public boolean buyCard(Card cardarg) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(this.os));
        String temp;
        String OK = new String("OK");
        System.out.println("cardarg: " + cardarg.toString());

    List<Card> cardarray = new ArrayList<Card>();
    
        writer.write("BUY " + cardarg.getID()+ "\n");
        writer.flush();
        String readingStream;
        while ((temp = cis.readResponse()) !=null) {
            if (temp.contentEquals(OK)) {
                return true;
            }
            return false;
        }
    return false;
    }

    public boolean sellCard(Card card, long price) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(this.os));
        String temp;
        String OK = new String("OK");
        
        List<Card> cardarray = new ArrayList<Card>();
        writer.write("SELL " + card.getID() + "\n") ;
        writer.flush();
        while ((temp = cis.readResponse()) != null) {
            if (temp.contentEquals(OK)) {
                return true;
            }
            return false;
        }
        return false;
    }
    
        public void close() throws IOException {
            this.is.close();
            this.os.close();
            this.socket.close();
    }
    
      public static void main(String[] args) throws UnknownHostException,IOException {  
          Card c0 = new Card(123, "_", Rank.COMMON);
          Card c1 = new Card(0, "_", Rank.COMMON);
          HollomonClient hc0 = new HollomonClient("netsrv.cim.rhul.ac.uk", 1812);
          List<Card> al0 = hc0.login("bottest", "testpass");
          //hc0.getOffers();
           boolean b0 = hc0.buyCard(c0);
          boolean b1 = hc0.buyCard(c1);

      }
     }
