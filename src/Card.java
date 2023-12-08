

public class Card  implements Comparable<Card> {
    private long id;
    private String name;
    private Rank rank;
    private long price;
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        //this line is wrong
        this.price = 0;
    }
    
    public long getID() { 
        return this.id;
    }
    
    public long getPrice() {
        return this.price;
    }
    
    //change dis one
    public String toString() {
        String s1 = "";
        s1 = ("ID: " + this.id + ", Name: " + this.name + ", rank = " + this.rank + ", price = " + this.price);
        return s1;
    }
    
    //change dis one
      public boolean equals(Object o) {
          if (!(o instanceof Card)) {
              return false; 
              }
      Card other = (Card) o; 
      return (this.id == other.id &&  this.name.equals(other.name) && this.rank == other.rank); }
     
    
    /*
     * public boolean equals(Object o) { Card other = (Card) o; if (this.id == other
     * .id && this.name.equals(other.name) && this.rank == other.rank) { return
     * true; } else { return false; } }
     */
    
    public int hashCode() {
        return (int) ((id >> 32) ^ id);
    }
    
    /*
     * @Override public int compareTo(Card o) { Card other = o; if (o.rank.UNIQUE ==
     * other.rank.UNIQUE && o.name.equals(other.name) && o.id == other.id) { return
     * 0; } else if (o.id == other.id && o.name.equals(other.name) && o.rank ==
     * other.rank) { return 0; } else { return -1; } }
     */
    @Override
    public int compareTo(Card o) {
        int result = this.rank.compareTo(o.rank);
        if (result == 0) {
            return this.name.compareTo(o.name);
        } else {
            return result;
        }
    }
    
    
}
