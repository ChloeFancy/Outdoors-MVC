package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "backup", catalog = "")
public class UserEntity {
    private int id;
    private String name;
    private String mail;
    private String tel;
    private String password;
    private String photoPath;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "mail", nullable = true, length = 20)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "tel", nullable = true, length = 11)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "photoPath", nullable = true, length = 50)
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(tel, that.tel) &&
                Objects.equals(password, that.password) &&
                Objects.equals(photoPath, that.photoPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, mail, tel, password, photoPath);
    }
}
