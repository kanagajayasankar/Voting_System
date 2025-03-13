package india21.persistence.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "role")
public class Role {

    // <editor-fold defaultstate="collapsed" desc="columns">
    public Role(){
        this.dateCreated = new Date();
    }
    
    @Version
    private long jpaVersion;
    
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column
    private final Date dateCreated;
    
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @ManyToOne(fetch = FetchType.LAZY)
    protected Person person;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="access methods">
     public Date getDateCreated() {
        return dateCreated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
    
    
    
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
    // </editor-fold>
}
