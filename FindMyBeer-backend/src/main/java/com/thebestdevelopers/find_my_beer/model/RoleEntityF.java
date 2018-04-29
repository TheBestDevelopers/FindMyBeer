package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Dominik Florencki
 */
@Entity
@Table(name = "role", schema = "main", catalog = "d86n3p8h6i057d")
public class RoleEntityF {
    private long userId;
    private String role;
    private UserEntityF userByUserId;

    @Id
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntityF that = (RoleEntityF) o;
        return userId == that.userId &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, role);
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public UserEntityF getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntityF userByUserId) {
        this.userByUserId = userByUserId;
    }
}
