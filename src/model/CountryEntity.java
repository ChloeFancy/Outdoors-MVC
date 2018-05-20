package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "country", schema = "outdoors", catalog = "")
public class CountryEntity {
    private int id;
    private String name;
    private Integer idContinent;
    private String discription;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
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
    @Column(name = "discription", nullable = true, length = -1)
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(idContinent, that.idContinent) &&
                Objects.equals(discription, that.discription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, idContinent, discription);
    }
}
