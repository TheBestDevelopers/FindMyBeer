package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;


/**
 * @author Grzegorz Nowak
 *
 */
@Entity
@GenericGenerator(name = "seq6", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prod_id_sequence", value = "prod_id_sequence"))
@Table(name = "products", schema = "public", catalog = "d86n3p8h6i057d")
public class ProductsEntity {
    private int prodId;
    private String description;
    private Collection<MenuEntity> menusByProdId;

    @Id
    @GeneratedValue(generator = "seq6")
    @Column(name = "prod_id", nullable = false)
    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return prodId == that.prodId &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(prodId, description);
    }

    @OneToMany(mappedBy = "productsByProdId")
    public Collection<MenuEntity> getMenusByProdId() {
        return menusByProdId;
    }

    public void setMenusByProdId(Collection<MenuEntity> menusByProdId) {
        this.menusByProdId = menusByProdId;
    }
}
