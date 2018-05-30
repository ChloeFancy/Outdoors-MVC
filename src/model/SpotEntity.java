package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spot", schema = "backup", catalog = "")
public class SpotEntity {
    private int id;
    private String name;
    private Integer idContinent;
    private Integer idCountry;
    private String shortIntro;
    private String introduction;
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
    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "id_continent", nullable = true)
    public Integer getIdContinent() {
        return idContinent;
    }

    public void setIdContinent(Integer idContinent) {
        this.idContinent = idContinent;
    }

    @Basic
    @Column(name = "id_country", nullable = true)
    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    @Basic
    @Column(name = "shortIntro", nullable = true, length = -1)
    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    @Basic
    @Column(name = "introduction", nullable = true, length = -1)
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
        SpotEntity that = (SpotEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(idContinent, that.idContinent) &&
                Objects.equals(idCountry, that.idCountry) &&
                Objects.equals(shortIntro, that.shortIntro) &&
                Objects.equals(introduction, that.introduction) &&
                Objects.equals(photoPath, that.photoPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, idContinent, idCountry, shortIntro, introduction, photoPath);
    }
}
