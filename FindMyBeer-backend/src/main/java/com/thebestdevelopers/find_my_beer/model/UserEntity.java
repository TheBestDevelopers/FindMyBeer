package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "public", catalog = "d86n3p8h6i057d")
public class UserEntity {
    private int userId;
    private String login;
    private String password;
    private RoleEntity roleByUserId;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 100)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, login, password);
    }

    @OneToOne(mappedBy = "userByUserId")
    public RoleEntity getRoleByUserId() {
        return roleByUserId;
    }

    public void setRoleByUserId(RoleEntity roleByUserId) {
        this.roleByUserId = roleByUserId;
    }
}
