package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq8", strategy = "sequence-identity", parameters = @org.hibernate.annotations.Parameter(name = "user_id_sequence", value = "user_id_sequence"))
@Table(name = "user", schema = "public", catalog = "d86n3p8h6i057d")
public class UserEntity {
    private long userId;
    private String username;
    private String password;
    private RoleEntity roleByUserId;

    @Id
    @GeneratedValue(generator = "seq8")
    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = false, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, username, password);
    }

    @OneToOne(mappedBy = "userByUserId")
    public RoleEntity getRoleByUserId() {
        return roleByUserId;
    }

    public void setRoleByUserId(RoleEntity roleByUserId) {
        this.roleByUserId = roleByUserId;
    }
}
