package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "continent", schema = "outdoors", catalog = "")
public class ContinentEntity {
    private int id;
    private String name;
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
    @Column(name = "name", nullable = false, length = 6)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        ContinentEntity that = (ContinentEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(discription, that.discription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, discription);
    }
}
