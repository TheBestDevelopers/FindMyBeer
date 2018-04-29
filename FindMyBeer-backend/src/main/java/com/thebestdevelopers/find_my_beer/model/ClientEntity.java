package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "client", schema = "public", catalog = "d86n3p8h6i057d")
public class ClientEntity {
    private int clientId;
    private Integer photoId;
    private Collection<FavouritiesEntity> favouritiesByClientId;
    private Collection<RatingsEntity> ratingsByClientId;

    @Id
    @Column(name = "client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "photo_id", nullable = true)
    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return clientId == that.clientId &&
                Objects.equals(photoId, that.photoId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientId, photoId);
    }

    @OneToMany(mappedBy = "clientByClientId")
    public Collection<FavouritiesEntity> getFavouritiesByClientId() {
        return favouritiesByClientId;
    }

    public void setFavouritiesByClientId(Collection<FavouritiesEntity> favouritiesByClientId) {
        this.favouritiesByClientId = favouritiesByClientId;
    }

    @OneToMany(mappedBy = "clientByClientId")
    public Collection<RatingsEntity> getRatingsByClientId() {
        return ratingsByClientId;
    }

    public void setRatingsByClientId(Collection<RatingsEntity> ratingsByClientId) {
        this.ratingsByClientId = ratingsByClientId;
    }
}
