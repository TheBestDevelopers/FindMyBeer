package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@GenericGenerator(name = "seq4", strategy = "sequence-identity", parameters = @org.hibernate.annotations.Parameter(name = "menu_id_sequence", value = "menu_id_sequence"))
@Table(name = "menu", schema = "public", catalog = "d86n3p8h6i057d")
public class MenuEntity {
    private int pubId;
    private int prodId;
    private Double price;
    private int menuId;
    private PubEntity pubByPubId;
    private ProductsEntity productsByProdId;

    @Basic
    @Column(name = "pub_id", nullable = false)
    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    @Basic
    @Column(name = "prod_id", nullable = false)
    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Id
    @GeneratedValue(generator = "seq4")
    @Column(name = "menu_id", nullable = false)
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuEntity that = (MenuEntity) o;
        return pubId == that.pubId &&
                prodId == that.prodId &&
                menuId == that.menuId &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pubId, prodId, price, menuId);
    }

    @ManyToOne
    @JoinColumn(name = "pub_id", referencedColumnName = "pub_id", nullable = false, insertable = false, updatable = false)
    public PubEntity getPubByPubId() {
        return pubByPubId;
    }

    public void setPubByPubId(PubEntity pubByPubId) {
        this.pubByPubId = pubByPubId;
    }

    @ManyToOne
    @JoinColumn(name = "prod_id", referencedColumnName = "prod_id", nullable = false, insertable = false, updatable = false)
    public ProductsEntity getProductsByProdId() {
        return productsByProdId;
    }

    public void setProductsByProdId(ProductsEntity productsByProdId) {
        this.productsByProdId = productsByProdId;
    }
}
