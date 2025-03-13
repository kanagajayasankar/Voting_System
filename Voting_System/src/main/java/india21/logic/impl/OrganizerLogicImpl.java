package india21.logic.impl;

import india21.logic.OrganizerLogic;
import india21.logic.CommonLogic;
import india21.logic.dto.*;
import india21.logic.dto.exception.InvalidOperation;
import india21.logic.dto.exception.NotFoundException;
import india21.logic.dto.exception.PollException;
import india21.logic.util.StringUtils;
import india21.persistence.dao.*;
import india21.persistence.entities.*;
import india21.persistence.entities.enums.PollStates;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class OrganizerLogicImpl implements OrganizerLogic {

    // <editor-fold defaultstate="collapsed" desc="private members">
    @Inject
    private OrganizerAccess organizerAccess;

    @Inject
    private PollAccess pollAccess;

    @Inject
    private QuestionAccess questionAccess;

    @Inject
    private ItemAccess itemAccess;

    @Inject
    private ParticipantAccess participantAccess;

    @Inject
    private ParticipantListAccess participantListAccess;

    @Inject
    private PersonAccess personAccess;

    @Inject
    private TokenAccess tokenAccess;

    @Inject
    private Principal principal;

    @Inject
    private CommonLogic commonLogic;

    @Inject
    private ValidationLogicImp validationLogic;

    private Organizer getOrganizer() throws PollException {
        Optional<Organizer> organizer = organizerAccess.getByUsername(principal.getName());
        if (!organizer.isPresent()) {
            throw new NotFoundException(StringUtils.getLabel("organizer"));
        }
        return organizer.get();
    }
    // </editor-fold>

    @Override
    public Long addUpdatePoll(PollDto model) throws PollException {
        validationLogic.validatePollTitle(model.getPollId(), model.getTitle());
        validationLogic.validateDescription(model.getDescription());
        validationLogic.validatePollDates(model.getStartDate(), model.getEndDate());

        Poll poll = new Poll();
        if (model.getPollId() != null) {
            validationLogic.validateEditablePoll(model.getPollId(), getOrganizer());
            poll = pollAccess.getByID(model.getPollId());
        }
        poll.setOrganizer(getOrganizer());
        poll.setTitle(model.getTitle());
        poll.setDescription(model.getDescription());
        poll.setStartDate(model.getStartDate());
        poll.setEndDate(model.getEndDate());
        poll.setState(PollStates.PREPARED);
        pollAccess.persist(poll);
        return poll.getId();
    }

    @Override
    public void deletePoll(Long id) throws PollException {
        validationLogic.validateDeletablePoll(id, getOrganizer());
        pollAccess.deleteByID(id);
    }

    @Override
    public Long addUpdateQuestion(QuestionDto model) throws PollException {
        validationLogic.validateQuestionTitle(model.getPollId(), model.getId(), model.getTitle());
        validationLogic.validateEditablePoll(model.getPollId(), getOrganizer());
        validationLogic.validateDecisionMode(model.getDecisionMode());
        validationLogic.validateMaxAnswers(model.getMaxAnswers());
        Poll poll = pollAccess.getByID(model.getPollId());
        Question question = new Question();
        if (model.getId() != null) {
            question = poll.getQuestions().stream()
                    .filter(qs -> qs.getId().equals(model.getId()))
                    .findFirst()
                    .orElse(null);
            if (question == null) {
                throw new NotFoundException(StringUtils.getLabel("question"));
            }
        }
        question.setMaxAnswers(model.getMaxAnswers());
        question.setMode(model.getDecisionMode());
        question.setTitle(model.getTitle());
        question.setPoll(poll);
        questionAccess.persist(question);
        return question.getId();
    }

    @Override
    public void deleteQuestion(Long id) throws PollException {
        Question question = questionAccess.getByID(id);
        if (question == null) {
            throw new NotFoundException(StringUtils.getLabel("question"));
        }
        validationLogic.validateEditablePoll(question.getPoll().getId(), getOrganizer());
        questionAccess.deleteByID(id);
    }

    @Override
    public Long addUpdateItem(ItemInfoDto model) throws PollException {
        validationLogic.validateItemName(model.getQuestionId(), model.getId(), model.getName());
        validationLogic.validateDescription(model.getDescription());
        Question question = questionAccess.getByID(model.getQuestionId());
        if (question == null) {
            throw new NotFoundException(StringUtils.getLabel("question"));
        }
        validationLogic.validateEditablePoll(question.getPoll().getId(), getOrganizer());
        Item item = new Item();
        if (model.getId() != null) {
            item = question.getItems().stream()
                    .filter(it -> it.getId().equals(model.getId()))
                    .findFirst()
                    .orElse(null);
            if (item == null) {
                throw new NotFoundException(StringUtils.getLabel("item"));
            }
        }
        item.setName(model.getName());
        item.setQuestion(question);
        item.setDescription(model.getDescription());
        itemAccess.persist(item);
        return item.getId();
    }

    @Override
    public void deleteItem(Long id) throws PollException {
        Item item = itemAccess.getByID(id);
        if (item == null) {
            throw new NotFoundException(StringUtils.getLabel("item"));
        }
        Question question = item.getQuestion();
        validationLogic.validateEditablePoll(question.getPoll().getId(), getOrganizer());
        itemAccess.deleteByID(id);
    }

    @Override
    public PollResultDto viewResult(Long pollId) throws PollException {
        validationLogic.validateViewablePoll(pollId, getOrganizer());
        Poll poll = pollAccess.getByID(pollId);
        return PollResultDto.fromEo(poll);
    }

    @Override
    public Long extendPoll(PollDto model) throws PollException {
        Poll poll = validationLogic.validateExtendablePoll(model.getPollId(), getOrganizer());
        validationLogic.validatePollExtendedDates(poll.getEndDate(), model.getExtendedDate());
        poll.setEndDate(model.getExtendedDate());
        pollAccess.persist(poll);
        return poll.getId();
    }

    @Override
    public void startPoll(StartPollDto model) throws PollException {
        validationLogic.validateStartablePoll(model.getPollId(), getOrganizer());
        List<ParticipantList> selectedParticipants = participantListAccess
                .getAllByName(model.getVoterListName(), getOrganizer().getPerson().getId());
        if (selectedParticipants.size() < 3) {
            throw new InvalidOperation(StringUtils.getLabel("startPoll"), StringUtils.getLabel("leastParticipant"));
        }
        if (model.getIsAnonymous() && model.getAutoNotify()) {
            throw new InvalidOperation(StringUtils.getLabel("startPoll"), StringUtils.getLabel("anonymousParticipant"));
        }
        Poll poll = pollAccess.getByID(model.getPollId());
        poll.setAutoNotify(model.getAutoNotify());
        poll.setIsAnonymous(model.getIsAnonymous());
        for (ParticipantList selectedParticipant : selectedParticipants) {
            Participant newParticipant = new Participant(selectedParticipant.getMember());
            Token newToken = new Token();
            newToken.setPoll(poll);
            newToken.setIsValid(true);
            if (!poll.getIsAnonymous()) {
                newToken.setParticipant(newParticipant);
            }
            commonLogic.sendEmail(newParticipant.getPerson().getUserName(), poll.getStartDate(), poll.getEndDate(), newToken.getToken(), poll.getTitle(), selectedParticipants.size());
            tokenAccess.persist(newToken);
            participantAccess.persist(newParticipant);
        }
        poll.setState(PollStates.STARTED);
        pollAccess.persist(poll);
    }

    @Override
    public String publishResult(Long pollId, boolean isPublished) throws PollException {
        validationLogic.validateViewablePoll(pollId, getOrganizer());
        Poll poll = pollAccess.getByID(pollId);
        List<ParticipantList> selectedParticipants = participantListAccess
                .getAllByOwner(poll.getOrganizer().getPerson().getId());
        if (isPublished) {
            poll.setPublicResultKey(UUID.randomUUID().toString());
            for (ParticipantList selectedParticipant : selectedParticipants) {
                Participant newParticipant = new Participant(selectedParticipant.getMember());
                commonLogic.sendResultEmail(newParticipant.getPerson().getUserName(), poll.getPublicResultKey(), poll.getTitle());
            }
        } else {
            poll.setPublicResultKey(null);
        }
        pollAccess.persist(poll);
        return poll.getPublicResultKey();
    }

    @Override
    public List<PollIncreementDto> getPolls() throws PollException {
        return pollAccess.getAllByOrganizer(getOrganizer().getId())
                .stream()
                .map(poll -> PollIncreementDto.fromEo(poll))
                .collect(Collectors.toList());
    }

    @Override
    public List<PollIncreementDto> getAllPolls() throws PollException {
        return pollAccess.getAll()
                .stream()
                .map(poll -> PollIncreementDto.fromEo(poll))
                .collect(Collectors.toList());
    }

    @Override
    public void removeVoterList(String listName) throws PollException {
        List<Long> listItemIds = participantListAccess
                .getAllByName(listName, getOrganizer().getPerson().getId())
                .stream()
                .map(pList -> pList.getId())
                .collect(Collectors.toList());
        listItemIds.forEach(itemId -> participantListAccess.deleteByID(itemId));
    }

    @Override
    public Long addVoter(String listName, String email) throws PollException {
        validationLogic.validateParticipantListName(listName);
        validationLogic.validateEmail(email);
        Optional<ParticipantList> listItem = participantListAccess
                .getByNameAndPerson(listName, getOrganizer().getPerson().getId(), email);
        if (listItem.isPresent()) {
            return listItem.get().getId();
        }
        Person newPerson = new Person();
        Optional<Person> person = personAccess.getByUsername(email);
        if (person.isPresent()) {
            newPerson = person.get();
        }
        newPerson.setUserName(email);
        ParticipantList newItem = new ParticipantList();
        newItem.setListName(listName);
        newItem.setOwner(getOrganizer().getPerson());
        newItem.setMember(newPerson);
        personAccess.persist(newPerson);
        participantListAccess.persist(newItem);
        return newItem.getId();
    }

    @Override
    public void removeVoter(String listName, String email) throws PollException {
        Optional<ParticipantList> listItem = participantListAccess
                .getByNameAndPerson(listName, getOrganizer().getPerson().getId(), email);
        if (listItem.isPresent()) {
            participantListAccess.deleteByID(listItem.get().getId());
        }
    }

    @Override
    public List<VoterInfoDto> getVoterLists() throws PollException {
        List<ParticipantList> participantLists = participantListAccess
                .getAllByOwner(getOrganizer().getPerson().getId());
        List<VoterInfoDto> result = participantLists.stream()
                .map(list -> list.getListName())
                .distinct()
                .map(listName -> new VoterInfoDto(listName))
                .collect(Collectors.toList());
        result.forEach(distinctList -> distinctList.setEmailsList(participantLists.stream()
                .filter(pList -> pList.getListName().equals(distinctList.getName()))
                .map(pList -> pList.getMember().getUserName())
                .collect(Collectors.joining(";"))));
        return result;
    }

    @Override
    public List<QuestionDto> getPollQuestions(Long pollId) throws PollException {
        Poll poll = validationLogic.validateOwningPoll(pollId, getOrganizer());
        return poll.getQuestions().stream()
                .map(question -> QuestionDto.fromEo(question))
                .collect(Collectors.toList());
    }

    @Override
    public PollIncreementDto getPollById(Long pollId) throws PollException {
        Optional<Poll> poll = pollAccess.getByOrganizerAndId(getOrganizer().getId(), pollId);
        if (!poll.isPresent()) {
            throw new NotFoundException(StringUtils.getLabel("poll"));
        }
        PollIncreementDto pollInfoDto = PollIncreementDto.fromEo(poll.get());
        pollInfoDto.setQuestions(poll.get().getQuestions()
                .stream()
                .map(question -> {
                    return QuestionDto.fromEo(question);
                })
                .collect(Collectors.toList()));
        return pollInfoDto;
    }

    @Override
    public PersonDto getOrganizerDetails() throws PollException {
        return PersonDto.fromEo(getOrganizer().getPerson());
    }
}
