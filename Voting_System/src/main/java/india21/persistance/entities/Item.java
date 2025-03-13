package india21.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "item")
@Cacheable(false)
public class Item implements Serializable {
    
    public Item(){
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
    
    
    @Column
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer votes = 0;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

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
    
    
    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void increaseVotes() {
        this.votes = getVotes() + 1;
    }

    public int getVotes() {
        return votes == null ? 0 : votes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
