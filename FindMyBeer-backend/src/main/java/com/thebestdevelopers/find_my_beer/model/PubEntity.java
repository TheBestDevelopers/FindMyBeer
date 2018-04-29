package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pub", schema = "public", catalog = "d86n3p8h6i057d")
public class PubEntity {
    private int pubId;
    private Integer photoId;
    private String pubName;
    private AddressesEntity addressesByPubId;
    private Collection<ConveniencesEntity> conveniencesByPubId;
    private Collection<FavouritiesEntity> favouritiesByPubId;
    private Collection<MenuEntity> menusByPubId;
    private PhotosEntity photosByPhotoId;
    private Collection<TablesEntity> tablesByPubId;

    @Id
    @Column(name = "pub_id", nullable = false)
    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    @Basic
    @Column(name = "photo_id", nullable = true)
    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    @Basic
    @Column(name = "pub_name", nullable = true, length = 100)
    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PubEntity pubEntity = (PubEntity) o;
        return pubId == pubEntity.pubId &&
                Objects.equals(photoId, pubEntity.photoId) &&
                Objects.equals(pubName, pubEntity.pubName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pubId, photoId, pubName);
    }

    @OneToOne(mappedBy = "pubByPubId")
    public AddressesEntity getAddressesByPubId() {
        return addressesByPubId;
    }

    public void setAddressesByPubId(AddressesEntity addressesByPubId) {
        this.addressesByPubId = addressesByPubId;
    }

    @OneToMany(mappedBy = "pubByPubId")
    public Collection<ConveniencesEntity> getConveniencesByPubId() {
        return conveniencesByPubId;
    }

    public void setConveniencesByPubId(Collection<ConveniencesEntity> conveniencesByPubId) {
        this.conveniencesByPubId = conveniencesByPubId;
    }

    @OneToMany(mappedBy = "pubByPubId")
    public Collection<FavouritiesEntity> getFavouritiesByPubId() {
        return favouritiesByPubId;
    }

    public void setFavouritiesByPubId(Collection<FavouritiesEntity> favouritiesByPubId) {
        this.favouritiesByPubId = favouritiesByPubId;
    }

    @OneToMany(mappedBy = "pubByPubId")
    public Collection<MenuEntity> getMenusByPubId() {
        return menusByPubId;
    }

    public void setMenusByPubId(Collection<MenuEntity> menusByPubId) {
        this.menusByPubId = menusByPubId;
    }

    @ManyToOne
    @JoinColumn(name = "photo_id", referencedColumnName = "photo_id", insertable = false, updatable = false)
    public PhotosEntity getPhotosByPhotoId() {
        return photosByPhotoId;
    }

    public void setPhotosByPhotoId(PhotosEntity photosByPhotoId) {
        this.photosByPhotoId = photosByPhotoId;
    }

    @OneToMany(mappedBy = "pubByPubId")
    public Collection<TablesEntity> getTablesByPubId() {
        return tablesByPubId;
    }

    public void setTablesByPubId(Collection<TablesEntity> tablesByPubId) {
        this.tablesByPubId = tablesByPubId;
    }
}
