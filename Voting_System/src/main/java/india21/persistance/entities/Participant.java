package india21.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Participant extends Role {

    public Participant() {
    }

    public Participant(india21.persistence.entities.Person person) {
        this.person = person;
    }

    // <editor-fold defaultstate="collapsed" desc="columns">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="relations">
    @OneToOne(mappedBy = "participant")
    private india21.persistence.entities.Token token;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="access methods">
    public india21.persistence.entities.Token getToken() {
        return token;
    }

    public void setToken(india21.persistence.entities.Token token) {
        this.token = token;
    }
    // </editor-fold>
}
