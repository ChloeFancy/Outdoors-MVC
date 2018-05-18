package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "strategy", schema = "outdoors", catalog = "")
public class StrategyEntity {
    private int id;
    private Integer idWriter;
    private Integer idSpot;
    private String content;
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
    @Column(name = "id_writer", nullable = true)
    public Integer getIdWriter() {
        return idWriter;
    }

    public void setIdWriter(Integer idWriter) {
        this.idWriter = idWriter;
    }

    @Basic
    @Column(name = "id_spot", nullable = true)
    public Integer getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(Integer idSpot) {
        this.idSpot = idSpot;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        StrategyEntity that = (StrategyEntity) o;
        return id == that.id &&
                Objects.equals(idWriter, that.idWriter) &&
                Objects.equals(idSpot, that.idSpot) &&
                Objects.equals(content, that.content) &&
                Objects.equals(photoPath, that.photoPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idWriter, idSpot, content, photoPath);
    }
}
