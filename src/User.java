public class User {

    public String name;
    public String password;
    public String clubMember;
    public String admin;

    public User(String name, String password, String clubMember, String  admin){
        this.name = name;
        this.password = password;
        this.clubMember = clubMember;
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClubMember() {
        return clubMember;
    }

    public void setClubMember(String clubMember) {
        this.clubMember = clubMember;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "user\t" + name + "\t" + password + "\t" + clubMember + "\t" + admin + "\n" ;
    }
}
