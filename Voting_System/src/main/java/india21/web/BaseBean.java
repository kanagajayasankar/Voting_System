package india21.web;

import india21.logic.dto.exception.InternalException;
import india21.logic.dto.exception.PollException;
import india21.logic.util.Pair;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseBean implements Serializable {

    private String pageException = "";

    public String getPageException() {
        return pageException;
    }

    public void setPageException(String pageException) {
        this.pageException = pageException;
    }

    protected void parseVotingException(PollException exception) {
        Logger.getLogger("BaseBean").log(Level.WARNING, "VotingException", exception);
        setPageException(exception.getMessage());
    }

    protected void redirectTo(String url, Pair<String, String>... queryString) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (queryString.length > 0) {
                StringBuilder urlBuilder = new StringBuilder(url);
                urlBuilder.append("?");
                for (Pair<String, String> param : queryString) {
                    urlBuilder.append(param.getKey());
                    urlBuilder.append("=");
                    urlBuilder.append(param.getValue());
                    urlBuilder.append("&");
                }
                url = urlBuilder.toString();
            }
            externalContext.redirect(url);
        } catch (IOException exception) {
            Logger.getLogger("BaseBean").log(Level.WARNING, "IOException", exception);
            parseVotingException(new InternalException("Was not able to redirect"));
        }
    }

    protected String getRequestCompletePath() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return externalContext.getRequestContextPath()
                + externalContext.getRequestServletPath();
    }

    protected void redirectToThis() {
        redirectTo(getRequestCompletePath());
    }
}
