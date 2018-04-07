package com.thebestdevelopers.find_my_beer.DTO;

/**
 * @author Dominik Florencki
 */
public class UserDTO {
    private long user_id;
    private String username;
    private String role;

    public UserDTO() {
    }

    public UserDTO(long user_id, String username, String role) {
        this.user_id = user_id;
        this.username = username;
        this.role = role;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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
