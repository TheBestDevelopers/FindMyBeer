package com.thebestdevelopers.find_my_beer.DTO;

/**
 * @author Dominik Florencki
 */
public class UserDTO {
    private long id;
    private String username;
    private String role;

    public UserDTO() {
    }

    public UserDTO(long user_id, String username, String role) {
        this.id = user_id;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
