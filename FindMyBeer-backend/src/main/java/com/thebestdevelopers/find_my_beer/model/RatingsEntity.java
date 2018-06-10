package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;


/**
 * @author Grzegorz Nowak
 *
 */
@Entity
@GenericGenerator(name = "seq9", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "ratings_id_sequence", value = "ratings_id_sequence"))
@Table(name = "ratings", schema = "public", catalog = "d86n3p8h6i057d")
public class RatingsEntity {
    private int ratingsId;
    private int clientId;
    private Double rate;
    private Integer pubId;
    private ClientEntity clientByClientId;

    @Id
    @GeneratedValue(generator = "seq9")
    @Column(name = "ratings_id", nullable = false)
    public int getRatingsId() {
        return ratingsId;
    }

    public void setRatingsId(int ratingsId) {
        this.ratingsId = ratingsId;
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
    @Column(name = "rate", nullable = true, precision = 0)
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "pub_id", nullable = true)
    public Integer getPubId() {
        return pubId;
    }

    public void setPubId(Integer pubId) {
        this.pubId = pubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingsEntity that = (RatingsEntity) o;
        return ratingsId == that.ratingsId &&
                clientId == that.clientId &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(pubId, that.pubId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ratingsId, clientId, rate, pubId);
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
