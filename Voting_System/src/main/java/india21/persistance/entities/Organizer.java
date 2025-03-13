package india21.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Table;

@Entity
public class Organizer extends Role {

    // <editor-fold defaultstate="collapsed" desc="columns">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "organizer",
            fetch = FetchType.LAZY
    )
    private final List<Poll> polls = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="access methods">
    public void addPoll(Poll poll) {
        polls.add(poll);
    }

    public void removePoll(int index) {
        polls.remove(index);
    }

    public Poll getPoll(Integer index) {
        return polls.get(index);
    }
    // </editor-fold>
}
