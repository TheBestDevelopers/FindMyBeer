package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq5", strategy = "sequence-identity", parameters = @org.hibernate.annotations.Parameter(name = "photo_id_sequence", value = "photo_id_sequence"))
@Table(name = "photos", schema = "public", catalog = "d86n3p8h6i057d")
public class PhotosEntity {
    private int photoId;
    private String path;
    private Collection<PubEntity> pubsByPhotoId;

    @Id
    @GeneratedValue(generator = "seq5")
    @Column(name = "photo_id", nullable = false)
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Basic
    @Column(name = "path", nullable = false, length = 200)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotosEntity that = (PhotosEntity) o;
        return photoId == that.photoId &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(photoId, path);
    }

    @OneToMany(mappedBy = "photosByPhotoId")
    public Collection<PubEntity> getPubsByPhotoId() {
        return pubsByPhotoId;
    }

    public void setPubsByPhotoId(Collection<PubEntity> pubsByPhotoId) {
        this.pubsByPhotoId = pubsByPhotoId;
    }
}
