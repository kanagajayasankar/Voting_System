package india21.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "ParticipantList")
public class ParticipantList implements Serializable  {

    // <editor-fold defaultstate="collapsed" desc="columns">
    
    
     public ParticipantList(){
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
    
    
    @Column
    private String listName;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @ManyToOne(fetch = FetchType.LAZY)
    private Person owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person member;

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
    
    
    public Person getOwner() {
        return owner;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getMember() {
        return member;
    }

    public void setMember(Person member) {
        this.member = member;
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
