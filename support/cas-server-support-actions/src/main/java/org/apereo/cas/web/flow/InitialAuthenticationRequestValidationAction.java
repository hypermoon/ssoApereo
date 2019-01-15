package org.apereo.cas.web.flow;

import org.apereo.cas.web.flow.resolver.CasWebflowEventResolver;
import org.springframework.webflow.action.AbstractAction;
import org.apereo.cas.web.support.WebUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * This is {@link InitialAuthenticationRequestValidationAction}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class InitialAuthenticationRequestValidationAction extends AbstractAction {

    private final CasWebflowEventResolver rankedAuthenticationProviderWebflowEventResolver;

    public InitialAuthenticationRequestValidationAction(final CasWebflowEventResolver eventResolver) {
        this.rankedAuthenticationProviderWebflowEventResolver = eventResolver;
    }

    @Override
    protected Event doExecute(final RequestContext requestContext) throws Exception {
   
       final HttpServletRequest request = WebUtils.getHttpServletRequest(requestContext);
         
        String strurl = request.getRequestURL().toString();
        String getqs = request.getQueryString();
        System.out.println("--GET REQUEST URL is : " + strurl);
        System.out.println("--GET REQUEST Parameter is : " + getqs);
        System.out.println("----222---initiaAuthenticatRequestValidation ----222----");
        return this.rankedAuthenticationProviderWebflowEventResolver.resolveSingle(requestContext);
    }
}
