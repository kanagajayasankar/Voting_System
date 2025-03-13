package india21.persistence.dao;

import india21.persistence.entities.Token;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class TokenAccess extends BaseDataAccess<Token> {

    public TokenAccess() {
        super(Token.class);
    }

    public Optional<Token> getByValue(String value) {
        try {
            Token token = (Token) entityManager.createQuery("SELECT t FROM Token t WHERE t.tokenValue=:value")
                    .setParameter("value", value.toLowerCase())
                    .getSingleResult();
            return Optional.ofNullable(token);
        } catch (NoResultException exception) {
            Logger.getLogger("TokenAccess").log(Level.WARNING, "NoResultException", exception);
            return Optional.empty();
        }
    }
}
