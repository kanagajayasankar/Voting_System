package india21.logic;

import india21.logic.dto.exception.PollException;
import india21.persistence.entities.Administrator;
import india21.persistence.entities.Organizer;

import java.util.Date;

public interface CommonLogic {

    Organizer addUpdateOrganizer(String email, String firstName, String lastName) throws PollException;

    Administrator addUpdateAdministrator(String email, String firstName, String lastName) throws PollException;

    void sendEmail(String toEmail, Date startDate, Date endDate, String token, String pollTitle, Integer numOfParticipants) throws PollException;

    void sendResultEmail(String toEmail, String publishKey, String pollTitle) throws PollException;
    
    void notificationMessages();
    
    void operateStates();
}
