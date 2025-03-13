package india21.logic;

import india21.logic.dto.*;
import india21.logic.dto.exception.PollException;

import java.util.List;

public interface OrganizerLogic {

    
    Long addUpdatePoll(PollDto model) throws PollException;

    Long addUpdateQuestion(QuestionDto model) throws PollException;

    Long addUpdateItem(ItemInfoDto model) throws PollException;

    void startPoll(StartPollDto model) throws PollException;
    
    Long extendPoll(PollDto model) throws PollException;

    void deletePoll(Long PollId) throws PollException;

    void deleteQuestion(Long id) throws PollException;

    void deleteItem(Long id) throws PollException;

    PollResultDto viewResult(Long pollId) throws PollException;

    String publishResult(Long pollId, boolean isPublished) throws PollException;
    
    List<PollIncreementDto> getPolls() throws PollException;

    List<PollIncreementDto> getAllPolls() throws PollException;
    
    PollIncreementDto getPollById(Long pollId) throws PollException;
    
    List<QuestionDto> getPollQuestions(Long pollId) throws PollException;
    
    void removeVoterList(String listName) throws PollException;
    
    Long addVoter(String listName, String email) throws PollException;

    void removeVoter(String listName, String email) throws PollException;
    
    List<VoterInfoDto> getVoterLists() throws PollException;
    
    PersonDto getOrganizerDetails() throws PollException;
}
