import javafx.scene.control.Button;

public class Seat {

    String film;
    String hall;
    int row;
    int column;
    String owner;
    int price;

    Button button;


    public Seat(String film, String hall, int row, int column, String owner, int price){
        this.film = film;
        this.hall = hall;
        this.row = row;
        this.column = column;
        this.owner = owner;
        this.price = price;
    }

    public void setButton(Button button){
        this.button = button;
    }

    @Override
    public String toString() {
        return "seat\t" + film + "\t" + hall + "\t" + row + "\t" + column + "\t" + owner + "\t" + price + "\n";
    }
}
