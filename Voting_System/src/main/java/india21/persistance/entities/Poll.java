package india21.persistence.entities;

import india21.persistence.entities.enums.PollStates;
import java.io.Serializable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "poll")
@Cacheable(false)
public class Poll implements Serializable{

    // <editor-fold defaultstate="collapsed" desc="columns">
    public Poll(){
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
    
    
    @Column(unique = true)
    private String title;

    @Column
    private String description;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column(nullable = false)
    private Integer submittedVotes = 0;

    @Column
    private PollStates state;

    @Column(nullable = false)
    private Boolean isAnonymous = false;

    @Column(nullable = false)
    private Boolean autoNotify = true;

    @Column
    private String publicResultKey;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "poll",
            fetch = FetchType.LAZY
    )
    private List<Question> questions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Administrator administrator;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "poll",
            fetch = FetchType.LAZY
    )
    private List<Token> tokens = new ArrayList<>();

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
    
    public String getPublicResultKey() {
        return publicResultKey;
    } 

    public List<Token> getTokens() {
        return tokens;
    }

    public void setPublicResultKey(String publicResultKey) {
        this.publicResultKey = publicResultKey;
    }

    public Boolean getAutoNotify() {
        return autoNotify == null ? true : autoNotify;
    }

    public void setAutoNotify(Boolean autoNotify) {
        this.autoNotify = autoNotify;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous == null ? false : isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(int index) {
        questions.remove(index);
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public void removeToken(int index) {
        tokens.remove(index);
    }

    public Integer getTokenCount() {
        return tokens.size();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void increaseSubmittedVotes() {
        this.submittedVotes = getSubmittedVotes() + 1;
    }

    public int getSubmittedVotes() {
        return submittedVotes == null ? 0 : submittedVotes;
    }

    public PollStates getState() {
        return state;
    }

    public void setState(PollStates state) {
        this.state = state;
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
