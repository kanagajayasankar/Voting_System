package india21.logic.dto;

import india21.persistence.entities.Poll;
import india21.persistence.entities.enums.PollStates;

public class PollIncreementDto extends PollDto {

    private final StartPollDto startPollDto = new StartPollDto();
    private Integer numParticipats;
    private Integer numInvites;
    private PollStates state;
    
    public StartPollDto getStartPollDto() {
        return startPollDto;
    }
        
    public Integer getProgress() {
        return numInvites == 0 ? 0 : (int) ((double) numParticipats * 100 / numInvites);
    }

    public int getNumParticipats() {
        return numParticipats;
    }

    public PollStates getState() {
        return state;
    }

    public int getNumInvites() {
        return numInvites;
    }

    public static PollIncreementDto fromEo(Poll poll) {
        PollIncreementDto dto = new PollIncreementDto();
        dto.setPollId(poll.getId());
        dto.setTitle(poll.getTitle());
        dto.setDescription(poll.getDescription());
        dto.setStartDate(poll.getStartDate());
        dto.setEndDate(poll.getEndDate());
        dto.numInvites = poll.getTokenCount();
        dto.numParticipats = poll.getSubmittedVotes();
        dto.state = poll.getState();
        dto.startPollDto.setPollId(poll.getId());
        return dto;
    }
}
