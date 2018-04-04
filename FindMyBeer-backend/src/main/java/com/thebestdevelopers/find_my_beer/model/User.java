package com.thebestdevelopers.find_my_beer.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author Wiktor Florencki
 */
@Entity
@Table(name = "user", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @Column(name = "user_id")
//    @org.hibernate.annotations.GenericGenerator(name="hilo-strategy", strategy = "hilo")
//    @GeneratedValue(generator = "hilo-strategy")
    @SequenceGenerator(name="identifier", sequenceName="user_id", allocationSize=8)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    //@GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    @Column(name = "login")
    private String username;
    @NotBlank
    @Column(name = "password")
    private String password;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
