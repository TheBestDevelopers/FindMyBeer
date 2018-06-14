package thebestdevelopers.pl.findmybeer.loginController;


public class User {
    private String username;
    private int id;
    private Role role;

    public User(String _username, int _id, Role _role) {
        username = _username;
        id = _id;
        role = _role;
    }

    public Role getRole() {
        return role;
    }
}
