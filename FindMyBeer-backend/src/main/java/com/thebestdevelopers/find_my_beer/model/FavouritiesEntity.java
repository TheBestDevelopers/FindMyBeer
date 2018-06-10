package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;


/**
 * @author Grzegorz Nowak
 *
 */
@Entity
@GenericGenerator(name = "seq3", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "favourities_id_sequence", value = "favourities_id_sequence"))

@Table(name = "favourities", schema = "public", catalog = "d86n3p8h6i057d")
public class FavouritiesEntity {
    private int clientId;
    private int pubId;
    private int favouritiesId;
    private ClientEntity clientByClientId;
    private PubEntity pubByPubId;

    @Basic
    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "pub_id", nullable = false)
    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    @Id
    @GeneratedValue(generator = "seq3")
    @Column(name = "favourities_id", nullable = false)
    public int getFavouritiesId() {
        return favouritiesId;
    }

    public void setFavouritiesId(int favouritiesId) {
        this.favouritiesId = favouritiesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouritiesEntity that = (FavouritiesEntity) o;
        return clientId == that.clientId &&
                pubId == that.pubId &&
                favouritiesId == that.favouritiesId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientId, pubId, favouritiesId);
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false, insertable = false, updatable = false)
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }

    @ManyToOne
    @JoinColumn(name = "pub_id", referencedColumnName = "pub_id", nullable = false, insertable = false, updatable = false)
    public PubEntity getPubByPubId() {
        return pubByPubId;
    }

    public void setPubByPubId(PubEntity pubByPubId) {
        this.pubByPubId = pubByPubId;
    }
}
