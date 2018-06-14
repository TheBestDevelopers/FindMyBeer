package com.thebestdevelopers.find_my_beer.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;


/**
 * @author Grzegorz Nowak
 *
 */
@Entity
@GenericGenerator(name = "seq1", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = @org.hibernate.annotations.Parameter(name = "convenience_id_sequence", value = "convenience_id_sequence"))
@Table(name = "conveniences", schema = "public", catalog = "d86n3p8h6i057d")
public class ConveniencesEntity {
    private int pubId;
    private int convenienceTypesId;
    private long convenienceId;
    private PubEntity pubByPubId;
    private ConvenienceTypesEntity convenienceTypesByConvenienceTypesId;

    @Basic
    @Column(name = "pub_id", nullable = false)
    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    @Basic
    @Column(name = "convenience_types_id", nullable = false)
    public int getConvenienceTypesId() {
        return convenienceTypesId;
    }

    public void setConvenienceTypesId(int convenienceTypesId) {
        this.convenienceTypesId = convenienceTypesId;
    }

    @Id
    @GeneratedValue(generator = "seq1")
    @Column(name = "convenience_id", nullable = false)
    public long getConvenienceId() {
        return convenienceId;
    }

    public void setConvenienceId(long convenienceId) {
        this.convenienceId = convenienceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConveniencesEntity that = (ConveniencesEntity) o;
        return pubId == that.pubId &&
                convenienceTypesId == that.convenienceTypesId &&
                convenienceId == that.convenienceId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pubId, convenienceTypesId, convenienceId);
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
    @JoinColumn(name = "convenience_types_id", referencedColumnName = "convenience_types_id", nullable = false, insertable = false, updatable = false)
    public ConvenienceTypesEntity getConvenienceTypesByConvenienceTypesId() {
        return convenienceTypesByConvenienceTypesId;
    }

    public void setConvenienceTypesByConvenienceTypesId(ConvenienceTypesEntity convenienceTypesByConvenienceTypesId) {
        this.convenienceTypesByConvenienceTypesId = convenienceTypesByConvenienceTypesId;
    }
}
