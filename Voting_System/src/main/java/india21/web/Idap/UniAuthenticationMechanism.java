package india21.web.ldap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

@AutoApplySession
@ApplicationScoped
public class UniAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req,
            HttpServletResponse res,
            HttpMessageContext msg) {
        Credential credential = msg.getAuthParameters().getCredential();
        if (!(credential instanceof UsernamePasswordCredential)) {
            return msg.doNothing();
        }
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        return result.getStatus() == VALID ? msg.notifyContainerAboutLogin(result) : msg.doNothing();
    }
}
