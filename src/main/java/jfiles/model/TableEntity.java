package jfiles.model;

import javax.persistence.*;

//todo to remove later
@Entity
@Table(name = "testuserauth", schema = "xodatabase", catalog = "")
public class TableEntity {

    private String login;
    private String password;

    @Id
    @Column(name="login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name="password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
