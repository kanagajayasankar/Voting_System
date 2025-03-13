package india21.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEntity implements Serializable {

    public BaseEntity() {
        this.dateCreated = new Date();
    }

    private static final long serialVersionUID = 1894892695959439681L;

    @Version
    private long jpaVersion;

    // <editor-fold defaultstate="collapsed" desc="columns">
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column
    private final Date dateCreated;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="access methods">
    @Override
    public int hashCode() {
        return "IndiaEvs2021".hashCode() + Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (other.id == null) {
            throw new IllegalStateException("other.id not set");
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id + "/v" + jpaVersion;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    // </editor-fold>
}
