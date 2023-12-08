
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class CardInputStream extends InputStream{
protected InputStreamReader is;
private int index;
private BufferedReader reader;
private List<byte[]> inputBuffer = new ArrayList<>();

    public CardInputStream(InputStream input) throws IOException {
         this.is = new InputStreamReader(input);
        this.reader = new BufferedReader(is);
        //this.reader.mark(1);
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
    
    @Override
    public void close() throws IOException {
        this.is.close();
    }
    
    public Card readCard() throws IOException {
        String temp;
        String name = null;
        long id = 0;
        Rank rank = null;
        int price = 0;
        int counter = 0;
        while ((temp = readResponse()) != null) {
            if (((String) temp).contentEquals("CARD")) {
                temp = readResponse();
                while (counter < 5) {
                    id = Long.valueOf(temp);
                    counter++;
                    temp = reader.readLine();
                    counter++;
                    name = temp;
                    temp = reader.readLine();
                    counter++;
                    rank = Rank.valueOf(temp);
                    counter++;
                    temp = reader.readLine();
                    price = Integer.parseInt(temp);
                    counter++;
                }
                Card card = new Card(id, name, rank);
                card.setPrice(price);
                return card;
            }
            return null;
        }
        return null;
        
    }
    
    
    /* @Override public int read() throws IOException { if (inputBuffer.isEmpty()) {
     * return -1; } // Get first element of the List byte[] bytes =
     * inputBuffer.get(0); // Get the byte corresponding to the index and post
     * increment the current index byte result = bytes[index++]; if (index >=
     * bytes.length) { // It was the last index of the byte array so we remove it
     * from the list // and reset the current index inputBuffer.remove(0); index =
     * 0; } return result; }
     */
public String readResponse() throws IOException {
    String temp;
    //reader.reset();
    while ((temp = reader.readLine())!=null) {
        return temp;
    }
    
    return null;

}
}
