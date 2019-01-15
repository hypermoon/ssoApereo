// anson
package org.apereo.cas.web.flow;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;
import javax.validation.constraints.NotNull;

@Component("generateLoginTicketRAction")
public final class GenerateLoginTicketRAction extends AbstractAction {  

 public static final String GENERATER = "generated";
  // public static final String RLOGIN = "rlogin";
 //  @NotNull
 //  private final CentralAuthenticationService centralAuthenticationService;


  
 public Event doExecute(final RequestContext context) {
   final HttpServletRequest request = 
       WebUtils.getHttpServletRequest(context);

   System.out.println("--5.2.8-- generateLoginTicketRAction :--5.2.8- ");
   
                 
      return new Event(this, GENERATER);
   
     }

}
