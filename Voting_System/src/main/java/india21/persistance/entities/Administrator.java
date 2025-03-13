package india21.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Administrator extends Role{

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "administrator",
            fetch = FetchType.LAZY
    )
    private final List<Poll> polls = new ArrayList<>();

    public void addPoll(Poll poll) {
        polls.add(poll);
    }
    public void removePoll(int index) {
        polls.remove(index);
    }
    public Poll getPoll(Integer index) {
        return polls.get(index);
    }
}

