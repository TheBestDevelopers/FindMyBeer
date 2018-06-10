package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;


/**
 * @author Grzegorz Nowak
 *
 */
@Entity
@Table(name = "addresses", schema = "public", catalog = "d86n3p8h6i057d")
public class AddressesEntity {
    private int pubId;
    private String city;
    private String street;
    private String number;
    private PubEntity pubByPubId;

    @Id
    @Column(name = "pub_id", nullable = false)
    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street", nullable = true, length = 100)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "number", nullable = true, length = 100)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressesEntity that = (AddressesEntity) o;
        return pubId == that.pubId &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pubId, city, street, number);
    }

    @OneToOne
    @JoinColumn(name = "pub_id", referencedColumnName = "pub_id", nullable = false)
    public PubEntity getPubByPubId() {
        return pubByPubId;
    }

    public void setPubByPubId(PubEntity pubByPubId) {
        this.pubByPubId = pubByPubId;
    }
}
