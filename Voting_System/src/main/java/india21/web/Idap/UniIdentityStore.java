package india21.web.ldap;

import india21.logic.CommonLogic;
import india21.logic.dto.exception.PollException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UniIdentityStore implements IdentityStore {

    @Inject
    private CommonLogic commonLogic;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential passwordCredential = (UsernamePasswordCredential) credential;
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, "ldaps://ldap.uni-koblenz.de:636");
        properties.put(Context.SECURITY_AUTHENTICATION, "simple");
        properties.put(Context.SECURITY_PRINCIPAL, String.format("uid=%s,ou=people,ou=koblenz,dc=Uni-Koblenz-landau,dc=de", passwordCredential.getCaller().split("@")[0]));
        properties.put(Context.SECURITY_CREDENTIALS, passwordCredential.getPasswordAsString());
        try {
            LdapContext context = new InitialLdapContext(properties, null);
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> answer = context.search("dc=Uni-Koblenz-landau,dc=de", "mail=" + passwordCredential.getCaller(), constraints);
            if (answer.hasMore()) {
                Attributes attrs = answer.next().getAttributes();
                if (passwordCredential.getCaller().equals("riediger@uni-koblenz.de")){
                    commonLogic.addUpdateAdministrator(passwordCredential.getCaller(), attrs.get("givenname").get().toString(), attrs.get("sn").get().toString());
                }else {
                    commonLogic.addUpdateOrganizer(passwordCredential.getCaller(), attrs.get("givenname").get().toString(), attrs.get("sn").get().toString());
                }

                return new CredentialValidationResult(passwordCredential.getCaller(), new HashSet<>(Arrays.asList("UNI-KO-STAFF")));
            }
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        } catch (PollException | NamingException exception) {
            Logger.getLogger("UniIdentityStore").log(Level.SEVERE, "LDAPException", exception);
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }
}
