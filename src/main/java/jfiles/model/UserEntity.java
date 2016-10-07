package jfiles.model;

import javax.persistence.*;

/**Class is required for database interaction via hibernate.*/
@Entity
@Table(name = "user", schema = "xodatabase", catalog = "")
public class UserEntity {

    private String name;
    private String password;
    private byte[] avatarPic;
    private int role;
    private String email;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "avatarPic")
    public byte[] getAvatarPic() {
        return avatarPic;
    }

    public void setAvatarPic(byte[] avatarPic) {
        this.avatarPic = avatarPic;
    }

    @Basic
    @Column(name = "role")
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**Method implementation is used into preparation of .csv tables*/
    @Override
    public String toString(){
        return name + ";" + password + ";" + role + ";" + email;
    }
}
