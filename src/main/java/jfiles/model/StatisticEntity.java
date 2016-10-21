package jfiles.model;

import javax.persistence.*;

/**Class is required for database interaction via hibernate.<br>
 * One record contains statistic how many times player with id user win, loose or even to vsUser*/
@Entity
@Table(name = "statistic", schema = "xodatabase", catalog = "")
public class StatisticEntity {

    private int id;
    private String user;
    private String vsUser;
    private int win;
    private int loose;
    private int even;

    public StatisticEntity(){}

    public StatisticEntity(int id, String user, String vsUser, int win, int loose, int even) {
        this.id = id;
        this.user = user;
        this.vsUser = vsUser;
        this.win = win;
        this.loose = loose;
        this.even = even;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "User")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "vsUser")
    public String getVsUser() {
        return vsUser;
    }

    public void setVsUser(String vsUser) {
        this.vsUser = vsUser;
    }

    @Basic
    @Column(name = "Win")
    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    @Basic
    @Column(name = "Loose")
    public int getLoose() {
        return loose;
    }

    public void setLoose(int loose) {
        this.loose = loose;
    }

    @Basic
    @Column(name = "Even")
    public int getEven() {
        return even;
    }

    public void setEven(int even) {
        this.even = even;
    }

    /**Method implementation is used into preparation of .csv tables*/
    @Override
    public String toString(){

        String delim = ";";

        return id + delim + user + delim + vsUser + delim + win + delim + loose + delim + even;
    }
}
