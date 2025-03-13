/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package india21.logic.impl;

import india21.logic.VoterLogic;
import india21.logic.dto.PollIncreementDto;
import india21.logic.dto.PollResultDto;
import india21.logic.dto.QuestionDto;
import india21.logic.dto.exception.*;
import india21.persistence.dao.PollAccess;
import india21.persistence.dao.TokenAccess;
import india21.persistence.entities.Item;
import india21.persistence.entities.Poll;
import india21.persistence.entities.Question;
import india21.persistence.entities.Token;
import india21.persistence.entities.enums.PollStates;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class VoterLogicImpl implements VoterLogic {

    @Inject
    private TokenAccess tokenAccess;

    @Inject
    private PollAccess pollAccess;

    private Token getTokenByValue(String tokenValue) throws PollException {
        Optional<Token> token = tokenAccess.getByValue(tokenValue);
        if (!token.isPresent()) {
            throw new NotFoundException("Token");
        }
        if (!token.get().getIsValid()) {
            throw new InvalidOperation("Vote", "Token is already used for voting");
        }
        if (token.get().getPoll() == null) {
            throw new InternalException("Token is not assigned to any poll");
        }
        if (token.get().getPoll().getState() == PollStates.FINISHED) {
            throw new InvalidOperation("Vote", "Poll is already finished");
        }
        if (token.get().getPoll().getState() != PollStates.VOTING) {
            throw new InvalidOperation("Vote", "Poll is not started for voting yet");
        }
        return token.get();
    }

    @Override
    public PollIncreementDto getPollByToken(String tokenValue) throws PollException {
        Poll poll = getTokenByValue(tokenValue).getPoll();
        PollIncreementDto pollInfoDto = PollIncreementDto.fromEo(poll);
        pollInfoDto.setQuestions(poll.getQuestions()
                .stream()
                .map(question -> {
                    return QuestionDto.fromEo(question);
                })
                .collect(Collectors.toList()));
        return pollInfoDto;
    }

    @Override
    public Long addVote(String tokenValue, PollIncreementDto pollInfo) throws PollException {
        Token token = getTokenByValue(tokenValue);
        Poll poll = token.getPoll();
        for (Question question : poll.getQuestions()) {
            Optional<QuestionDto> foundQuestion = pollInfo.getQuestions()
                    .stream()
                    .filter(inQuestion -> inQuestion.getId().equals(question.getId()))
                    .findFirst();
            if (!foundQuestion.isPresent()) {
                throw new VotingDataDoesNotMatchException();
            }
            Integer numOfAnswers = foundQuestion.get().getSelectedAnswer().size();
            if (numOfAnswers > question.getMaxAnswers()) {
                throw new InvalidParameterException(question.getTitle(), "Exceeds the number of possible answers");
            }
            if (numOfAnswers > 0) {
                for (Long answerId : foundQuestion.get().getSelectedAnswer()) {
                    Optional<Item> foundItem = question.getItems()
                            .stream()
                            .filter(item -> item.getId().equals(answerId))
                            .findFirst();
                    if (!foundItem.isPresent()) {
                        throw new VotingDataDoesNotMatchException();
                    }
                    foundItem.get().increaseVotes();
                }
                question.increaseSubmittedVotes();
            }
        }
        poll.increaseSubmittedVotes();
        if (poll.getTokenCount() == poll.getSubmittedVotes()) {
            poll.setState(PollStates.FINISHED);
        }
        token.setIsValid(false);
        tokenAccess.persist(token);
        pollAccess.persist(poll);
        return poll.getId();
    }

    @Override
    public PollResultDto viewResult(String publishKey) throws PollException {
        Optional<Poll> poll = pollAccess.getByPublishKey(publishKey);
        if (!poll.isPresent()) {
            return null;
        }
        return PollResultDto.fromEo(poll.get());
    }
}
