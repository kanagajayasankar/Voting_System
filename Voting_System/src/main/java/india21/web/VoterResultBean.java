package india21.web;

import india21.logic.VoterLogic;
import india21.logic.dto.PollResultDto;
import india21.logic.dto.exception.PollException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class VoterResultBean extends BaseBean {

    @Inject
    private VoterLogic voterLogic;

    private String publishKey;

    public String getPublishKey() {
        return publishKey;
    }

    public void setPublishKey(String publishKey) {
        this.publishKey = publishKey;
    }

    public PollResultDto getSelectedPoll() {
        try {
            return voterLogic.viewResult(publishKey);
        } catch (PollException exception) {
            parseVotingException(exception);
            return null;
        }
    }
}
