public class Hall {

    String name;
    String film;
    String price;
    String row;
    String column;

    public Hall(String name, String film, String price, String row, String column){
        this.name = name;
        this.film = film;
        this.price = price;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "hall\t" + film + "\t" + name + "\t" + price + "\t" + row + "\t" + column + "\n";
    }
}
