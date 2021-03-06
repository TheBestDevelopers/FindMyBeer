package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Dominik Florencki
 */
@Entity
@Table(name = "user", schema = "main", catalog = "d86n3p8h6i057d")
@GenericGenerator(name = "seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "user_id_seq", value = "user_id_seq"))
public class UserEntityF {
    private long userId;
    private String password;
    private String username;
    private RoleEntityF roleByUserId;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "seq")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntityF that = (UserEntityF) o;
        return userId == that.userId &&
                Objects.equals(password, that.password) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, password, username);
    }

    @OneToOne(mappedBy = "userByUserId")
    public RoleEntityF getRoleByUserId() {
        return roleByUserId;
    }

    public void setRoleByUserId(RoleEntityF roleByUserId) {
        this.roleByUserId = roleByUserId;
    }
}
