package india21.web;

import india21.logic.VoterLogic;
import india21.logic.dto.PollIncreementDto;
import india21.logic.dto.exception.PollException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class VoterBean extends BaseBean {

    private String token;

    private PollIncreementDto pollExtendedInfoDto;

    public PollIncreementDto getPollInfo() {
        return pollExtendedInfoDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token == null) {
            token = "";
        }
        if (this.token == null || !this.token.equals(token)) {
            this.token = token;
            loadPoll();
        }
    }

    @Inject
    private VoterLogic voterLogic;

    private static final String PARTICIPANT_THANK_YOU_PATH = "close.xhtml";

    private void loadPoll() {
        try {
            pollExtendedInfoDto = token.isEmpty() ? null : voterLogic.getPollByToken(token);
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void submitVote() {
        try {
            voterLogic.addVote(token, pollExtendedInfoDto);
            redirectTo(PARTICIPANT_THANK_YOU_PATH);
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }
}
