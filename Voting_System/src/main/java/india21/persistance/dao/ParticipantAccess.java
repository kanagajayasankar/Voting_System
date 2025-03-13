
package india21.persistence.dao;

import india21.persistence.entities.Participant;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ParticipantAccess extends BaseDataAccess<Participant> {
    
    public ParticipantAccess() {
        super(Participant.class);
    }
    
}
