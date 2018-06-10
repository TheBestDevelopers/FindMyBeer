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
@GenericGenerator(name = "seq2", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "convenience_types_id_sequence", value = "convenience_types_id_sequence"))
@Table(name = "convenience_types", schema = "public", catalog = "d86n3p8h6i057d")
public class ConvenienceTypesEntity {
    private int convenienceTypesId;
    private String description;
    private Collection<ConveniencesEntity> conveniencesByConvenienceTypesId;

    @Id
    @GeneratedValue(generator = "seq2")
    @Column(name = "convenience_types_id", nullable = false)
    public int getConvenienceTypesId() {
        return convenienceTypesId;
    }

    public void setConvenienceTypesId(int convenienceTypesId) {
        this.convenienceTypesId = convenienceTypesId;
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
        ConvenienceTypesEntity that = (ConvenienceTypesEntity) o;
        return convenienceTypesId == that.convenienceTypesId &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(convenienceTypesId, description);
    }

    @OneToMany(mappedBy = "convenienceTypesByConvenienceTypesId")
    public Collection<ConveniencesEntity> getConveniencesByConvenienceTypesId() {
        return conveniencesByConvenienceTypesId;
    }

    public void setConveniencesByConvenienceTypesId(Collection<ConveniencesEntity> conveniencesByConvenienceTypesId) {
        this.conveniencesByConvenienceTypesId = conveniencesByConvenienceTypesId;
    }
}
