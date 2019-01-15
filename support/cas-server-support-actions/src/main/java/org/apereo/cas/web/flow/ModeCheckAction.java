package org.apereo.cas.web.flow;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.CentralAuthenticationService;
import org.apereo.cas.DESCoder;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;
import javax.validation.constraints.NotNull;

@Component("modeCheckAction")
public final class ModeCheckAction extends AbstractAction {  

 public static final String NORMAL = "normal";
 public static final String RLOGIN = "rlogin";

 // public RLoginCheckAction() {
 // }

//  @NotNull
//  private final CentralAuthenticationService centralAuthenticationService;


//  public ModeCheckAction(@Qualifier("centralAuthenticationService")
//                         final CentralAuthenticationService centralAuthenticationService)
// {
//    this.centralAuthenticationService = centralAuthenticationService;   
//  }
  

 public Event doExecute(final RequestContext context) throws Exception {
   final HttpServletRequest request = 
       WebUtils.getHttpServletRequest(context);
   
 //  import javax.servlet.http.HttpServletResponse;
   final HttpServletResponse response = WebUtils.getHttpServletResponse(context);
   
   String mode = request.getParameter("mode");

   String getqs = request.getQueryString();
   System.out.println("--getQueryString : " + getqs);

  // String getpara = request.getParameter();          
  // System.out.println("--getQueryString : " + getpara);

   String strurl = request.getRequestURL().toString();  
   System.out.println("--GET REQUEST URL is : " + strurl);
   //int usridx = strurl.indexoOf("username");
   int ishostchanged = 0;

   String oamode = request.getParameter("oamlog");
   String contxt = request.getContextPath();
  // String getqs = request.getQueryString();
 
   String tgurl = "?" + getqs;
   
   //System.out.println("---------- START DISPATHER --------" +  tgurl);
   //request.getRequestDispatcher(tgurl).forward(request,response);

   System.out.println("--- getRequestURL is : " + strurl);
   System.out.println("--- getQueryString is : " + getqs);
   System.out.println("--- getContextpath is : " + contxt);
   System.out.println("--- OAgetparameter is : " + oamode);
  
 //  ishostchanged= strurl.indexOf("webtest1");
   

 //  if( ishostchanged == -1 )   //no such string
 //  {
 //        ;
 //  }
 //  else
 //  {
 //     strurl.replace("webtest1.ccccltd.cn:7778/test?","sdc-ssotest.cccc-sdc.com/cas/login?mode=rlogin&" );
 //  }


   System.out.println("--5.2.9--DDDD mode rlogin DDDD:--5.2.9-- " + mode);
   System.out.println("--AAAAAAAAAA OAM_REMOTE_USER--AAA ----in Mode-actions");
   String EMPLID = request.getHeader("OAM_REMOTE_USERs");
   String desName = null;
   if(EMPLID == null)
    {
      System.out.println("----EMPLID is null");
    }
    else
    {
          System.out.println("----EMPLID is :"+ EMPLID);
       try{ 
          desName = DESCoder.decrypt(EMPLID);
          System.out.println("----After Des Name is :"+ desName);
          
          }catch(Exception e){
          e.printStackTrace();
          }
    }


   System.out.println("--BBBBBBBBBB OAM_REMOTE_USER--BBB ----- in Mode Actions ---- ");
 
 //  if(ishostchanged != -1)
 //  {    
 //       strurl = "https://sdc-ssotest.cccc-sdc.com/cas/login?";
 //       String getqs = request.getQueryString();
 //       strurl += getqs; 
 //       System.out.println("--sendRedirect RESPONSE URL is : " + strurl);
 //       response.sendRedirect(strurl);
 //  }
 
        if(mode!=null&&mode.equals("rlogin"))
         {
             context.getFlowScope().put("mode", mode);
             return new Event(this, RLOGIN);
          }
             return new Event(this, NORMAL);
      }
    
}
  
