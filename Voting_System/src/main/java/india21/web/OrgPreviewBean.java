package india21.web;

import india21.logic.OrganizerLogic;
import india21.logic.dto.PollIncreementDto;
import india21.logic.dto.exception.PollException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class OrgPreviewBean extends BaseBean {

    @Inject
    private OrganizerLogic organizerLogic;

    private Long selectedPollId;

    public void setSelectedPollId(Long selectedPollId) {
        this.selectedPollId = selectedPollId;
    }

    public Long getSelectedPollId() {
        return selectedPollId;
    }
    
     public PollIncreementDto getPollInfo() {
        try {
            return organizerLogic.getPollById(selectedPollId);
        } catch (PollException exception) {
            parseVotingException(exception);
            return new PollIncreementDto();
        }
    }
}
