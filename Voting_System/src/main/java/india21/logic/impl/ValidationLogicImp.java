package india21.logic.impl;

import india21.logic.dto.exception.*;
import india21.logic.util.StringUtils;
import india21.persistence.dao.ItemAccess;
import india21.persistence.dao.PollAccess;
import india21.persistence.dao.QuestionAccess;
import india21.persistence.entities.*;
import india21.persistence.entities.enums.DecisionMode;
import india21.persistence.entities.enums.PollStates;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
public class ValidationLogicImp {

    @Inject
    private PollAccess pollAccess;

    @Inject
    private QuestionAccess questionAccess;
    
    @Inject
    private ItemAccess itemAccess;

    public void validatePollTitle(Long pollId, String title) throws PollException {
        if (StringUtils.isEmpty(title)) {
            throw new MissingException(StringUtils.getLabel("title"));
        }
        if (title.length() > 100 || title.length() < 5) {
            throw new InvalidParameterException(StringUtils.getLabel("title"), StringUtils.getLabel("pollTitleLength"));
        }
        Optional<Poll> poll = pollAccess.getByTitle(title);
        if (poll.isPresent() && !poll.get().getId().equals(pollId)) {
            throw new InvalidParameterException(StringUtils.getLabel("title"), StringUtils.getLabel("pollDuplicate"));
        }
    }

    public void validateDescription(String description) throws PollException {
        if (StringUtils.isEmpty(description)) {
            throw new MissingException(StringUtils.getLabel("pollDescription"));
        }
    }

    public void validatePollDates(Date startDate, Date endDate) throws PollException {
        if (startDate == null) {
            throw new MissingException(StringUtils.getLabel("pollStartDate"));
        }
        if (endDate == null) {
            throw new MissingException(StringUtils.getLabel("pollEndDate"));
        }
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        calender.add(Calendar.HOUR, 3);
        if (startDate.compareTo(calender.getTime()) < 0) {
            throw new InvalidParameterException(StringUtils.getLabel("pollStartDate"), StringUtils.getLabel("pollHourFuture"));
        }
        calender.setTime(startDate);
        calender.add(Calendar.HOUR, 24);
        if (endDate.compareTo(calender.getTime()) < 0) {
            throw new InvalidParameterException(StringUtils.getLabel("pollEndDate"), StringUtils.getLabel("pollAfterStartDate"));
        }
    }
    
    public void validatePollExtendedDates(Date currentEndDate, Date newEndDate) throws PollException{
        if (currentEndDate == null) {
            throw new MissingException(StringUtils.getLabel("pollEndDate"));
        }
        if (newEndDate == null) {
            throw new MissingException(StringUtils.getLabel("pollNewEndDate"));
        }
        if (newEndDate.compareTo(currentEndDate) <= 0) {
            throw new InvalidParameterException(StringUtils.getLabel("pollNewEndDate"), StringUtils.getLabel("pollNewEndDateBeforeCurrent"));
        }
    }

    public void validateQuestionTitle(Long pollId, Long questionId, String title) throws PollException {
        if (StringUtils.isEmpty(title)) {
            throw new MissingException(StringUtils.getLabel("title"));
        }
        if (title.length() > 100 || title.length() < 4) {
            throw new InvalidParameterException(StringUtils.getLabel("title"), StringUtils.getLabel("QuestionTitleLength"));
        }
        Optional<Question> question = questionAccess.getByTitleAndPoll(pollId, title);
        if (question.isPresent() && !question.get().getId().equals(questionId)) {
            throw new InvalidParameterException(StringUtils.getLabel("title"), StringUtils.getLabel("pollDuplicate"));
        }
    }

    public Poll validateOwningPoll(Long pollId, Organizer thisOrganizer) throws PollException {
        Poll poll = pollAccess.getByID(pollId);
        if (poll == null) {
            throw new NotFoundException(StringUtils.getLabel("poll"));
        }
        if (poll.getOrganizer() != thisOrganizer) {
            throw new InvalidOperation(StringUtils.getLabel("viewingPoll"), StringUtils.getLabel("notOrganizersPoll"));
        }
        return poll;
    }

    public Poll validateExtendablePoll(Long pollId, Organizer thisOrganizer) throws PollException {
        Poll poll = validateOwningPoll(pollId, thisOrganizer);
        if (poll.getState() != PollStates.STARTED && poll.getState() != PollStates.VOTING) {
            throw new InvalidOperation(StringUtils.getLabel("extendingPoll"), StringUtils.getLabel("notStartedOrVoting"));
        }
        return poll;
    }
    
    public Poll validateDeletablePoll(Long pollId, Organizer thisOrganizer) throws PollException {
        Poll poll = validateOwningPoll(pollId, thisOrganizer);
        if (poll.getState() == PollStates.STARTED || poll.getState() == PollStates.VOTING) {
            throw new InvalidParameterException(StringUtils.getLabel("poll"), StringUtils.getLabel("startedAndNotFinishedPoll"));
        }
        return poll;
    }

    public Poll validateEditablePoll(Long pollId, Organizer thisOrganizer) throws PollException {
        Poll poll = validateDeletablePoll(pollId, thisOrganizer);
        if (poll.getState() != PollStates.PREPARED) {
            throw new InvalidOperation(StringUtils.getLabel("editingPoll"), StringUtils.getLabel("finishedPoll"));
        }
        return poll;
    }

    public void validateStartableQuestion(Question question) throws PollException {
        if (question.getItems().size() < 2) {
            throw new QuestionNotCompleteException(question.getTitle(), StringUtils.getLabel("leastOption"));
        }
        if (question.getMaxAnswers() > question.getItems().size()) {
            throw new InvalidOperation(question.getTitle(), StringUtils.getLabel("maxOption"));
        }
    }

    public Poll validateStartablePoll(Long pollId, Organizer thisOrganizer) throws PollException {
        Poll poll = validateEditablePoll(pollId, thisOrganizer);
        if (poll.getQuestions().isEmpty()) {
            throw new InvalidOperation(StringUtils.getLabel("startPoll"), StringUtils.getLabel("leastQuestion"));
        }
        for (Question question : poll.getQuestions()) {
            validateStartableQuestion(question);
        }
        validatePollDates(poll.getStartDate(), poll.getEndDate());
        return poll;
    }

    public void validateMaxAnswers(int maxAnswers) throws PollException {
        if (maxAnswers < 1) {
            throw new InvalidParameterException(StringUtils.getLabel("maxAnswers"), StringUtils.getLabel("maxQuestion"));
        }
    }

    public void validateDecisionMode(DecisionMode mode) throws PollException {
        if (mode == null) {
            throw new MissingException(StringUtils.getLabel("decisionMode"));
        }
    }

    public void validateItemName(Long questionId, Long itemId, String name) throws PollException {
        if (StringUtils.isEmpty(name)) {
            throw new MissingException(StringUtils.getLabel("name"));
        }
        if (name.length() > 40 || name.length() < 1) {
            throw new InvalidParameterException(StringUtils.getLabel("name"), StringUtils.getLabel("shortQuestion"));
        }
        Optional<Item> item = itemAccess.getByNameAndQuestion(questionId, name);
        if (item.isPresent() && !item.get().getId().equals(itemId)) {
            throw new InvalidParameterException(StringUtils.getLabel("name"), StringUtils.getLabel("duplicateQuestion"));
        }
    }

    public void validateParticipantListName(String name) throws PollException {
        if (StringUtils.isEmpty(name)) {
            throw new MissingException(StringUtils.getLabel("name"));
        }
    }

    public void validateEmail(String email) throws PollException {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException exception) {
            Logger.getLogger("ValidationLogicImp").log(Level.WARNING, "AddressException", exception);
            throw new InvalidParameterException(StringUtils.getLabel("email"), StringUtils.getLabel("invalidEmail"));
        }
    }

    public void validateViewablePoll(Long pollId, Organizer thisOrganizer) throws PollException {
        Poll poll = validateOwningPoll(pollId, thisOrganizer);
        if (poll.getState() != PollStates.FINISHED) {
            throw new InvalidParameterException(StringUtils.getLabel("poll"), StringUtils.getLabel("NotFinishedPoll"));
        }
        if (poll.getSubmittedVotes() < 3) {
            throw new InvalidOperation(StringUtils.getLabel("viewingPoll"), StringUtils.getLabel("insufficientPoll"));
        }
    }
}
