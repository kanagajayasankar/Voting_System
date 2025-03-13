package india21.logic.impl;

import india21.logic.CommonLogic;
import india21.logic.dto.exception.InternalException;
import india21.logic.dto.exception.PollException;
import india21.logic.util.StringUtils;
import india21.persistence.dao.AdministratorAccess;
import india21.persistence.dao.OrganizerAccess;
import india21.persistence.dao.PersonAccess;
import india21.persistence.dao.PollAccess;
import india21.persistence.entities.Administrator;
import india21.persistence.entities.Organizer;
import india21.persistence.entities.Person;
import india21.persistence.entities.Poll;
import india21.persistence.entities.enums.PollStates;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;

@Stateless
public class CommonLogicImpl implements CommonLogic {

    @Inject
    private PollAccess pollAccess;

    @Inject
    private PersonAccess personAccess;

    @Inject
    private OrganizerAccess organizerAccess;

    @Inject
    private AdministratorAccess administratorAccess;
    
    @Resource(lookup="mail/evsindia")
    private Session mailsession;

    @Override
    public Organizer addUpdateOrganizer(String email, String firstName, String lastName) throws PollException {
        Optional<Organizer> organizer = organizerAccess.getByUsername(email);
        Organizer newOrganizer = new Organizer();
        Person newPerson = new Person();
        if (organizer.isPresent()) {
            newOrganizer = organizer.get();
            newPerson = newOrganizer.getPerson();
        } else {
            Optional<Person> person = personAccess.getByUsername(email);
            if (person.isPresent()) {
                newPerson = person.get();
            }
        }
        newOrganizer.setPerson(newPerson);
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        newPerson.setUserName(email);
        organizerAccess.persist(newOrganizer);
        personAccess.persist(newPerson);
        return newOrganizer;
    }

    @Override
    public Administrator addUpdateAdministrator(String email, String firstName, String lastName) throws PollException {
        Optional<Administrator> administrator = administratorAccess.getByUsername(email);
        Administrator newAdministrator = new Administrator();
        Person newPerson = new Person();
        if (administrator.isPresent()) {
            newAdministrator = administrator.get();
            newPerson = newAdministrator.getPerson();
        } else {
            Optional<Person> person = personAccess.getByUsername(email);
            if (person.isPresent()) {
                newPerson = person.get();
            }
        }
        newAdministrator.setPerson(newPerson);
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        newPerson.setUserName(email);
        administratorAccess.persist(newAdministrator);
        personAccess.persist(newPerson);
        return newAdministrator;
    }

    @Override
    public void sendEmail(String toEmail, Date startDate, Date endDate, String token, String pollTitle, Integer numOfParticipants) throws PollException {
        Properties props = System.getProperties();
        //props.put("mail.smtp.auth", "false");
        //props.put("mail.smtp.host", StringUtils.getResource("india21.logic.util.smtp", "smtpHost"));
        //props.put("mail.smtp.port", StringUtils.getResource("india21.logic.util.smtp", "smtpPort"));
        //Session session = Session.getInstance(props);
        Message simpleMail = new MimeMessage(mailsession);
        try {
            simpleMail.setFrom(new InternetAddress("noreply@uni-koblenz.de"));
            simpleMail.setSubject(StringUtils.getLabel("reminderEmailSubject"));
            simpleMail.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            MimeMultipart mailContent = new MimeMultipart();
            MimeBodyPart mailMessage = new MimeBodyPart();
            mailMessage.setContent(StringUtils.getLabel("reminderEmailBody", pollTitle, StringUtils.dateToString(startDate), StringUtils.dateToString(endDate), token, numOfParticipants, token), "text/html; charset=utf-8");
            mailContent.addBodyPart(mailMessage);
            simpleMail.setContent(mailContent);
            Transport.send(simpleMail);
        } catch (MessagingException exception) {
            Logger.getLogger("CommonLogicImpl").log(Level.SEVERE, "MessagingException", exception);
            throw new InternalException(StringUtils.getLabel("sendingEmailUnknownException"));
        }
    }

    @Override
    public void sendResultEmail(String toEmail, String publishKey, String pollTitle) throws PollException {
        Properties props = System.getProperties();
        Message simpleMail = new MimeMessage(mailsession);
        try {
            simpleMail.setFrom(new InternetAddress("noreply@uni-koblenz.de"));
            simpleMail.setSubject(StringUtils.getLabel("resultEmailSubject"));
            simpleMail.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            MimeMultipart mailContent = new MimeMultipart();
            MimeBodyPart mailMessage = new MimeBodyPart();
            mailMessage.setContent(StringUtils.getLabel("resultEmailBody", pollTitle, publishKey, publishKey), "text/html; charset=utf-8");
            mailContent.addBodyPart(mailMessage);
            simpleMail.setContent(mailContent);
            Transport.send(simpleMail);
        } catch (MessagingException exception) {
            Logger.getLogger("CommonLogicImpl").log(Level.SEVERE, "MessagingException", exception);
            throw new InternalException(StringUtils.getLabel("sendingEmailUnknownException"));
        }
    }

    @Override
    public void notificationMessages() {
        List<Poll> startedPolls = pollAccess.getAllByState(PollStates.STARTED);
        startedPolls.forEach(startedPoll -> {
            if (!startedPoll.getIsAnonymous() && startedPoll.getAutoNotify()) {
                startedPoll.getTokens().forEach(token -> {
                    if (token.getParticipant() != null) {
                        try {
                            sendEmail(token.getParticipant().getPerson().getUserName(),
                                    startedPoll.getStartDate(),
                                    startedPoll.getEndDate(),
                                    token.getToken(),
                                    startedPoll.getTitle(),
                                    startedPoll.getTokenCount());
                        } catch (PollException exception) {
                            Logger.getLogger("CommonLogicImpl").log(Level.WARNING, "VotingException", exception);
                        } 
                    }
                });
            }
        });
    }

    @Override
    public void operateStates() {
        List<Poll> startedPolls = pollAccess.getAllByState(PollStates.STARTED);
        List<Poll> votingPolls = pollAccess.getAllByState(PollStates.VOTING);
        startedPolls.forEach(startedPoll -> {
            if (startedPoll.getStartDate().compareTo(new Date()) < 0) {
                startedPoll.setState(PollStates.VOTING);
                pollAccess.persist(startedPoll);
            }
        });
        votingPolls.forEach(votingPoll -> {
            if (votingPoll.getEndDate().compareTo(new Date()) < 0) {
                votingPoll.setState(PollStates.FINISHED);
                pollAccess.persist(votingPoll);
            }
        });
    }
}
