package india21.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    // <editor-fold defaultstate="collapsed" desc="columns">
    
    public Person(){
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
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String userName;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "person",
            fetch = FetchType.LAZY
    )
    private final List<Role> roles = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "member",
            fetch = FetchType.LAZY
    )
    private final List<ParticipantList> participationMembers = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "owner",
            fetch = FetchType.LAZY
    )
    private final List<ParticipantList> participationOwners = new ArrayList<>();

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
    
    public List<ParticipantList> getParticipationMembers() {
        return participationMembers;
    }

    public List<ParticipantList> getParticipationOwners() {
        return participationOwners;
    }

    public void addParticipationMember(ParticipantList participantList) {
        this.participationMembers.add(participantList);
    }

    public void removeParticipationMember(int index) {
        this.participationMembers.remove(index);
    }

    public void addParticipationOwner(ParticipantList participantList) {
        this.participationOwners.add(participantList);
    }

    public void removeParticipationOwner(int index) {
        this.participationOwners.remove(index);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
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
