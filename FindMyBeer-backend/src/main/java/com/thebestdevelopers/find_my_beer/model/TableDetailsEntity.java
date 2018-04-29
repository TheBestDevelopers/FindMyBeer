package com.thebestdevelopers.find_my_beer.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "table_details", schema = "public", catalog = "d86n3p8h6i057d")
public class TableDetailsEntity {
    private int tableId;
    private Integer places;
    private boolean isOccupied;
    private TablesEntity tablesByTableId;

    @Id
    @Column(name = "table_id", nullable = false)
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Basic
    @Column(name = "places", nullable = true)
    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    @Basic
    @Column(name = "is_occupied", nullable = false)
    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableDetailsEntity that = (TableDetailsEntity) o;
        return tableId == that.tableId &&
                isOccupied == that.isOccupied &&
                Objects.equals(places, that.places);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tableId, places, isOccupied);
    }

    @OneToOne
    @JoinColumn(name = "table_id", referencedColumnName = "table_id", nullable = false)
    public TablesEntity getTablesByTableId() {
        return tablesByTableId;
    }

    public void setTablesByTableId(TablesEntity tablesByTableId) {
        this.tablesByTableId = tablesByTableId;
    }
}
