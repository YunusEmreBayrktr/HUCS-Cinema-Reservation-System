public class Film {

    String name;
    String path;
    String duration;


    public Film(String name, String path, String duration){
        this.name = name;
        this.path = path;
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "film\t" + name + "\t" + path + "\t" + duration + "\n";
    }
}
