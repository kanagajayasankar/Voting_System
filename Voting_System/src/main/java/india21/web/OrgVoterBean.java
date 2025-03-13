package india21.web;

import india21.logic.OrganizerLogic;
import india21.logic.dto.VoterInfoDto;
import india21.logic.dto.exception.PollException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class OrgVoterBean extends BaseBean {

    @Inject
    private OrganizerLogic organizerLogic;

    @ManagedProperty(value = "#{param.emailToBeRemoved}")
    private String emailToBeRemoved;

    public void setEmailToBeRemoved(String email) {
        this.emailToBeRemoved = email;
    }

    private final VoterInfoDto createVoterInfoDto = new VoterInfoDto();

    public VoterInfoDto getCreateParticipantListInfoDto() {
        return createVoterInfoDto;
    }

    public List<VoterInfoDto> getMyParticipantList() {
        try {
            return organizerLogic.getVoterLists();
        } catch (PollException exception) {
            parseVotingException(exception);
            return new ArrayList<>();
        }
    }

    public void addParticipantToList(VoterInfoDto model) {
        try {
            for (String email : model.getEmails()) {
                organizerLogic.addVoter(model.getName(), email);
            }
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void deleteParticipantList(String listName) {
        try {
            organizerLogic.removeVoterList(listName);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void removeParticipant(String listName) {
        try {
            organizerLogic.removeVoter(listName, emailToBeRemoved);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }
}
