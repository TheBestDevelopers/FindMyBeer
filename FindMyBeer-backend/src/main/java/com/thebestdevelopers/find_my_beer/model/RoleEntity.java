package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "public", catalog = "d86n3p8h6i057d")
public class RoleEntity {
    private String role;
    private int userId;
    private UserEntity userByUserId;

    @Basic
    @Column(name = "role", nullable = false, length = 100)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return userId == that.userId &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(role, userId);
    }
}
