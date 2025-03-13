package india21.persistence.entities;

import india21.persistence.entities.enums.DecisionMode;
import java.io.Serializable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "question")
@Cacheable(false)
public class Question implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="columns">
    public Question(){
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
    private DecisionMode mode = DecisionMode.ABSOLUTE_MAJORITY;

    @Column
    private Integer maxAnswers;

    @Column
    private String title;

    @Column(nullable = false)
    private Integer submittedVotes = 0;

    @Column
    private Boolean canAddAnswers = false;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "question",
            fetch = FetchType.LAZY
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Poll poll;

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
    
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public void setMode(DecisionMode mode) {
        this.mode = mode;
    }

    public DecisionMode getMode() {
        return mode;
    }

    public void setMaxAnswers(Integer maxAnswers) {
        this.maxAnswers = maxAnswers;
    }

    public Integer getMaxAnswers() {
        return maxAnswers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSubmittedVotes() {
        return submittedVotes == null ? 0 : submittedVotes;
    }

    public void increaseSubmittedVotes() {
        this.submittedVotes = getSubmittedVotes() + 1;
    }

    public Integer getAbstentions() {
        return poll.getSubmittedVotes() - getSubmittedVotes();
    }

    public Boolean getCanAddAnswers() {
        return canAddAnswers;
    }

    public void setCanAddAnswers(Boolean canAddAnswers) {
        this.canAddAnswers = canAddAnswers;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(int index) {
        items.remove(index);
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
