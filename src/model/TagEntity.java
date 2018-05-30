package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tag", schema = "backup", catalog = "")
public class TagEntity {
    private int id;
    private String name;
    private Integer idStrategy;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "id_strategy", nullable = true)
    public Integer getIdStrategy() {
        return idStrategy;
    }

    public void setIdStrategy(Integer idStrategy) {
        this.idStrategy = idStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return id == tagEntity.id &&
                Objects.equals(name, tagEntity.name) &&
                Objects.equals(idStrategy, tagEntity.idStrategy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, idStrategy);
    }
}
