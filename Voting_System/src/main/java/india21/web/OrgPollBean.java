package india21.web;

import india21.logic.AdministratorLogic;
import india21.logic.OrganizerLogic;
import india21.logic.dto.PersonDto;
import india21.logic.dto.PollIncreementDto;
import india21.logic.dto.PollDto;
import india21.logic.dto.StartPollDto;
import india21.logic.dto.exception.PollException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

//TODO: PREVIEW PREPARED
@ManagedBean
@RequestScoped
public class OrgPollBean extends BaseBean {

    @Inject
    private OrganizerLogic organizerLogic;

    @Inject
    private AdministratorLogic administratorLogic;

    private final PollDto createPollDto = new PollDto();

    public PollDto getCreatePollInfoDto() {
        return createPollDto;
    }

    public String getBadgeType(PollIncreementDto poll) {
        switch (poll.getState()) {
            case PREPARED:
                return "badge-secondary";
            case STARTED:
                return "badge-warning";
            case VOTING:
                return "badge-danger";
            default:
                return "badge-success";
        }
    }

    public PersonDto getOrganizerPerson() {
        try {
            return organizerLogic.getOrganizerDetails();
        } catch (PollException exception) {
            parseVotingException(exception);
            return null;
        }
    }

    public List<PollIncreementDto> getPolls() {
        try {
            return organizerLogic.getPolls();
        } catch (PollException exception) {
            parseVotingException(exception);
            return new ArrayList<>();
        }
    }

    public List<PollIncreementDto> getAllPolls() {
        try {
            return organizerLogic.getAllPolls();
        } catch (PollException exception) {
            parseVotingException(exception);
            return new ArrayList<>();
        }
    }

    public void createPoll() {
        try {
            organizerLogic.addUpdatePoll(createPollDto);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void removePoll(Long pollId) {
        try {
            organizerLogic.deletePoll(pollId);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void removeAdminPoll(Long pollId) {
        try {
            administratorLogic.deletePoll(pollId);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void editPoll(PollDto model) {
        try {
            organizerLogic.addUpdatePoll(model);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void startPoll(StartPollDto model) {
        try {
            organizerLogic.startPoll(model);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }

    public void extendPoll(PollDto model) {
        try {
            organizerLogic.extendPoll(model);
            redirectToThis();
        } catch (PollException exception) {
            parseVotingException(exception);
        }
    }
}
