package india21.web;

import india21.logic.CommonLogic;
import india21.logic.dto.exception.InvalidOperation;
import india21.logic.dto.exception.InternalException;
import india21.logic.util.StringUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@SessionScoped
@Named
public class LoginBean extends BaseBean {

    @Inject
    private Principal principal;

    @Inject
    private CommonLogic commonLogic;

    @Inject
    private SecurityContext securityContext;

    private static final String ORGANIZER_POLLS_PATH = "pages/organizer/polls.xhtml";

    private static final String ADMIN_POLLS_DASHBOARD_PATH = "pages/admin/polls.xhtml";

    private String viewLocale;

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public String getLoggendInUsername() {
        if (principal == null
                || StringUtils.isEmpty(principal.getName())
                || principal.getName().equals("anonymous")) {
            return null;
        }
        return principal.getName();
    }

    public boolean isLoggendIn() {
        //return !StringUtils.isEmpty(getLoggendInUsername());
        
        if(getLoggendInUsername()=="ANONYMOUS"){
            return false;
        }
        if(getLoggendInUsername()==null){
            return false;
        }
        return !StringUtils.isEmpty(getLoggendInUsername());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            request.logout();
            securityContext.authenticate(request,
                    (HttpServletResponse) externalContext.getResponse(),
                    AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(email, password)));
            if (isLoggendIn()) {
                if (getLoggendInUsername().equals("riediger@uni-koblenz.de")) {
                    redirectTo(ADMIN_POLLS_DASHBOARD_PATH);
                }else {
                    redirectTo(ORGANIZER_POLLS_PATH);
                }
            } else {
                parseVotingException(new InvalidOperation("Login", "username or password is wrong !"));
            }
        } catch (ServletException exception) {
            parseVotingException(new InternalException("Unknown internal exception"));
        }
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            request.logout();
            externalContext.invalidateSession();
            redirectTo(externalContext.getRequestContextPath());
        } catch (ServletException ex) {
            parseVotingException(new InternalException("Unknown internal exception"));
        }
    }

    public void setLocaleEn() {
        viewLocale = "en";
    }

    public void setLocaleDe() {
        viewLocale = "de";
    }

    public String getViewLocale() {
        return viewLocale == null ? "en" : viewLocale;
    }

    public void setViewLocale(String viewLocale) {
        this.viewLocale = viewLocale;
    }
}
