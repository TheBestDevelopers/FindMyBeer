package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tables", schema = "public", catalog = "d86n3p8h6i057d")
public class TablesEntity {
    private int tableId;
    private int pubId;
    private TableDetailsEntity tableDetailsByTableId;
    private PubEntity pubByPubId;

    @Id
    @Column(name = "table_id", nullable = false)
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Basic
    @Column(name = "pub_id", nullable = false)
    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TablesEntity that = (TablesEntity) o;
        return tableId == that.tableId &&
                pubId == that.pubId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(tableId, pubId);
    }

    @OneToOne(mappedBy = "tablesByTableId")
    public TableDetailsEntity getTableDetailsByTableId() {
        return tableDetailsByTableId;
    }

    public void setTableDetailsByTableId(TableDetailsEntity tableDetailsByTableId) {
        this.tableDetailsByTableId = tableDetailsByTableId;
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
