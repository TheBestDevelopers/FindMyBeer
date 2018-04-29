package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ratings", schema = "public", catalog = "d86n3p8h6i057d")
public class RatingsEntity {
    private int pubIdPk;
    private int clientId;
    private String rate;
    private ClientEntity clientByClientId;

    @Id
    @Column(name = "pub_id_pk", nullable = false)
    public int getPubIdPk() {
        return pubIdPk;
    }

    public void setPubIdPk(int pubIdPk) {
        this.pubIdPk = pubIdPk;
    }

    @Basic
    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "rate", nullable = true, length = 100)
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingsEntity that = (RatingsEntity) o;
        return pubIdPk == that.pubIdPk &&
                clientId == that.clientId &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pubIdPk, clientId, rate);
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false, insertable = false, updatable = false)
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }
}
