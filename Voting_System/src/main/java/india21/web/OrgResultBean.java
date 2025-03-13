package india21.web;

import india21.logic.OrganizerLogic;
import india21.logic.dto.PollResultDto;
import india21.logic.dto.exception.PollException;
import india21.logic.util.Pair;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class OrgResultBean extends BaseBean {

    @Inject
    private OrganizerLogic organizerLogic;

    protected void redirectToThisWithParams() {
        redirectTo(getRequestCompletePath(), new Pair<>("pollId", this.pollId.toString()));
    }

    private Long pollId;

    private PollResultDto selectedPoll;

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        if (pollId != null && !pollId.equals(this.pollId)) {            
            try {
                this.selectedPoll = organizerLogic.viewResult(pollId);
            } catch (PollException exception) {
                parseVotingException(exception);
                this.selectedPoll = null;
            }
        }
        this.pollId = pollId;
    }

    public PollResultDto getSelectedPoll() {
        return selectedPoll;
    }

    public void publishResult() {
        try {
            organizerLogic.publishResult(pollId, true);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void unPublishResult() {
        try {
            organizerLogic.publishResult(pollId, false);
            redirectToThisWithParams();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }
}
