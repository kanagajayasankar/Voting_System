package india21.persistence.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "token")
@Cacheable(false)
public class Token {

    public Token() {
        this.tokenValue = UUID.randomUUID().toString();
        this.dateCreated = new Date();
    }
    
    @Version
    private long jpaVersion;
    
    // <editor-fold defaultstate="collapsed" desc="columns">
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column
    private final Date dateCreated;
    
    @Column(unique = true)
    private final String tokenValue;

    @Column
    private Boolean isValid = true;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @ManyToOne(fetch = FetchType.LAZY)
    private Poll poll;

    @OneToOne(fetch = FetchType.LAZY)
    private Participant participant;

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
    
    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Poll getPoll() {
        return poll;
    }

    public String getToken() {
        return tokenValue;
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
