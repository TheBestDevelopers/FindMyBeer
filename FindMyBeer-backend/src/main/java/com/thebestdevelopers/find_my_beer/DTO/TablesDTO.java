package com.thebestdevelopers.find_my_beer.DTO;


/**
 * @author Grzegorz Nowak
 *
 */
public class TablesDTO {
    private int chair1;
    private int chair2;
    private int chair4;
    private int chair6;
    private int chair8;

    public TablesDTO(int chair1, int chair2, int chair4, int chair6, int chair8) {
        this.chair1 = chair1;
        this.chair2 = chair2;
        this.chair4 = chair4;
        this.chair6 = chair6;
        this.chair8 = chair8;
    }

    public int getChair1() {
        return chair1;
    }

    public void setChair1(int chair1) {
        this.chair1 = chair1;
    }

    public int getChair2() {
        return chair2;
    }

    public void setChair2(int chair2) {
        this.chair2 = chair2;
    }

    public int getChair4() {
        return chair4;
    }

    public void setChair4(int chair4) {
        this.chair4 = chair4;
    }

    public int getChair6() {
        return chair6;
    }

    public void setChair6(int chair6) {
        this.chair6 = chair6;
    }

    public int getChair8() {
        return chair8;
    }

    public void setChair8(int chair8) {
        this.chair8 = chair8;
    }
}
