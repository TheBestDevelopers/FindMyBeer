package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Dominik Florencki
 */
@Entity
@Table(name = "persistent_logins", schema = "public", catalog = "d86n3p8h6i057d")
public class PersistentLoginsEntity {
    private String username;
    private String series;
    private String token;
    private Timestamp lastUsed;

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @Column(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "last_used")
    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentLoginsEntity that = (PersistentLoginsEntity) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(series, that.series) &&
                Objects.equals(token, that.token) &&
                Objects.equals(lastUsed, that.lastUsed);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, series, token, lastUsed);
    }
}
