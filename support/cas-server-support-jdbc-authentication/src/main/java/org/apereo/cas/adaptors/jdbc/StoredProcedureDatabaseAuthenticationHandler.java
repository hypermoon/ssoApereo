/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apereo.cas.adaptors.jdbc;
//package org.apereo.cas.web;
//package org.apereo.cas.services.web;
//package org.apereo.cas.trusted.util;

//import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.validation.constraints.NotNull;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.DESCoder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
//import org.apereo.commons.lang3.StringUtils;
import org.apereo.cas.web.support.WebUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
//import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
//CHECKSTYLE:OFF

/**
 * Provides the ability to authenticate a user by passing the username and password
 * to a database stored procedure. Matches the first column and first row of the
 * result set against a provided ReturnValue.
 * Default password translator is plaintext translator.
 * 
 * @author John Gasper
 * @version $Revision:  $ $Date: $  
 * @since 3.4.8
 */
public class StoredProcedureDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

   private  final String procedureName;
   private  final String usernameParameterName;
   private  final String passwordParameterName;
 
   private   String clientip;
   private  final String clientos;
   private  final String clientbrowser;
   private   String useragent;
   private  final String clientcountry;
   private  final String clientarea;

   private  final String successReturnValue;
  
   public StoredProcedureDatabaseAuthenticationHandler(final String name, 
                                                       final ServicesManager servicesManager,
                                                       final PrincipalFactory principalFactory,
                                                       final Integer order,
                                                       final DataSource dataSource,
                                                       final String procedureName,
                                                       final String usernameParameterName,
                                                       final String passwordParameterName,
                                                       final String clientip,
                                                       final String clientos,
                                                       final String clientbrowser,
                                                       final String useragent,
                                                       final String clientcountry,
                                                       final String clientarea,
                                                       final String successReturnValue
                                                      ){ 
    super(name,servicesManager,principalFactory,order,dataSource);
    this.procedureName = procedureName;
    this.usernameParameterName = usernameParameterName;
    this.passwordParameterName = passwordParameterName;
    this.clientip              = clientip;
    this.clientos              = clientos;
    this.clientbrowser         = clientbrowser;
    this.useragent             = useragent;
    this.clientcountry         = clientcountry;
    this.clientarea            = clientarea;
    this.successReturnValue    = successReturnValue;
         

}
    @Override
    protected  HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credentials ,final String originPassword)
            throws GeneralSecurityException, PreventedException {

        String username = credentials.getUsername();
        final String password = credentials.getPassword();
        String encryptedPassword = credentials.getPassword();  

        //credentials.setUsername(username);

        System.out.println("----111111---usernameParameterName: " + usernameParameterName);
        System.out.println("----222222---username: " + username);
        System.out.println("----aaaa---originpassword: " + originPassword);
        System.out.println("----bbbb---password: " + password);
 
        final ClientInfo clientInfo=ClientInfoHolder.getClientInfo();
        final String remoteAddress = clientInfo.getClientIpAddress();
     
        //  this.clientip = remoteAddress;
        
        final String myuseragents = WebUtils.getHttpServletRequestUserAgent();
        this.useragent = myuseragents;
        System.out.println("----ccccccc---!useragent is: " + myuseragents);
       
        final HttpServletRequest request = WebUtils.getHttpServletRequest();

        String getqsnew = request.getQueryString();
        
        String strurl2 = request.getRequestURL().toString();
        System.out.println("----(~~~~~~~~GET REQUEST URL is ~~~~~~~): " + strurl2);
         
        String EMPLID = request.getHeader("OAM_REMOTE_USERs");
       
        final HttpServletResponse response = WebUtils.getHttpServletResponse();
        

        System.out.println("---dddddddd1111  here is string parameter----- : " + getqsnew );
        System.out.println("---dddddddd222  here is OAM_REMOTE_USER----- : " + EMPLID );
        String desName=null;    
        if( EMPLID  != null && EMPLID.length() !=0 )
        {
         
           System.out.println("----remote flow ------");
           try{
                desName = DESCoder.decrypt(EMPLID);
                 username = desName;
                 credentials.setUsername(desName);
                 encryptedPassword="1234567890123456789012345678901234567890123456789012345678901234";
                System.out.println("----After Des Name is:" + desName);
              }
           catch(Exception e)
              {
                e.printStackTrace();
              }
             
        }
        else
        {
           System.out.println("----normal flow ------");
        }        

        this.clientip =  getrealIPAddress(request); 
        System.out.println("----ffffffff---!clientip is: " + this.clientip);

          System.out.println("---- user name before store is ------" + username);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(this.usernameParameterName, username, Types.VARCHAR)
                .addValue(this.passwordParameterName, encryptedPassword, Types.VARCHAR)
                .addValue("pClientIp", this.clientip, Types.NVARCHAR)
                .addValue("pClientOs", this.clientos, Types.NVARCHAR)
                .addValue("pClientBrowser", this.clientbrowser, Types.NVARCHAR)
                .addValue("pHttpUserAgent", this.useragent, Types.NVARCHAR)
                .addValue("pClientCountry", this.clientcountry, Types.NVARCHAR)
                .addValue("pClientArea", this.clientarea, Types.NVARCHAR);


        SimpleJdbcCall sp = new SimpleJdbcCall(this.getDataSource())
                .withProcedureName(procedureName)
                .withoutProcedureColumnMetaDataAccess().withReturnValue();

                sp.declareParameters(new SqlOutParameter("RETURN_VALUE", Types.INTEGER));

                sp.addDeclaredParameter(new SqlParameter(this.usernameParameterName, Types.VARCHAR));
                sp.addDeclaredParameter(new SqlParameter(this.passwordParameterName, Types.VARCHAR));
                sp.addDeclaredParameter(new SqlParameter("pClientIp", Types.NVARCHAR));
                sp.addDeclaredParameter(new SqlParameter("pClientOs", Types.NVARCHAR));
                sp.addDeclaredParameter(new SqlParameter("pClientBrowser", Types.NVARCHAR));
                sp.addDeclaredParameter(new SqlParameter("pHttpUserAgent", Types.NVARCHAR));
                sp.addDeclaredParameter(new SqlParameter("pClientCountry", Types.NVARCHAR));
                sp.addDeclaredParameter(new SqlParameter("pClientArea", Types.NVARCHAR));
        
  
        System.out.println("----pppppp---procedurename: " + procedureName);
        System.out.println("----qqqqqq---procip: " +  remoteAddress);  // this.clientip);
 
       try {
             System.out.println("----Step1 ---: ");
             System.out.println("----Step1.1111 name is ---: " + username);
             System.out.println("----Step1 ip: " + this.clientip);
             System.out.println("----Step1 os: " + this.clientos);
             System.out.println("----Step1 browser: " + this.clientbrowser);
             System.out.println("----Step1 useragent: " + this.useragent);
             System.out.println("----Step1 clientcountry: " + this.clientcountry);
             System.out.println("----Step1 cleintarea: " + this.clientarea);

             Map<String, Object> results = sp.execute(params);

             ArrayList result1 = (ArrayList) results.get("result1");

             String strret =  results.get("RETURN_VALUE").toString();
             
             System.out.println("----ppppppp---strret by procedure: " + strret);
             System.out.println("----qqqqqqq---successRet: " + this.successReturnValue);
            
             String dbResult = strret;

             Integer retvals = Integer.parseInt(strret);

             if(dbResult.equalsIgnoreCase(this.successReturnValue)) //true is equals
             {
             //   System.out.println("-----EEEEEEEEquals -------");
             //   String  newstrurl = "https://sdc-ssotest.cccc-sdc.com/cas/login?";
             //   String  newgetqs = request.getQueryString();
            //    newstrurl += newgetqs; 
            //    System.out.println("--new --sendRedirect RESPONSE URL is : " + newstrurl);
            //    response.sendRedirect(newstrurl);
             
                return createHandlerResult(credentials, this.principalFactory.createPrincipal(username), null);
             }
             else                                                   //false is unequals
             {
                System.out.println("------------UUUUUUUUUUUUUUnEqualssssss---------");
                switch(retvals)
                {
                   case -1:
                           {
                           System.out.println("*** Locked  -1 ---Account has been locked ***");
                           throw new AccountLockedException("Account has been locked.");
                           }
                   case -2:
                           {
                           System.out.println("*** PSD err  -2 ---Psd does not matched***");
                           throw new FailedLoginException("Password does not match value on record.");
                           } 
                   case -3:
                           {
                           System.out.println("*** PSD err>5 lock  -3  ---Psd wrong 5 Locked***");
                           throw new AccountLockedException("Password wrong more than 5 times,locked.");
                            }
                   case -4:
                          {
                           System.out.println("*** No user -4 ---Account does not exist***");
                           throw new AccountNotFoundException("Account Does not exist!");
                          }
                   case -5:
                          {
                           System.out.println("*** same tel  -5 ---Mobile number is duplicated***");
                           throw new FailedLoginException("Mobilephone number is duplicated!");
                           }
                   case -6:
                           {
                           System.out.println("*** same mail  -6 ---Email is duplicated***");
                           throw new FailedLoginException("Email is duplicated!");
                          }
                           
                 }
        
                String strurl3 = request.getRequestURL().toString();
                System.out.println("----(~~~~3333~~~~GET REQUEST URL is ~~~~3333~~~): " + strurl3);
                return null;
             }


        }
        catch (DataAccessException ex) {
          
             System.out.println("----Error: Exception! " );
             return null;
        }
    }

   public static String getrealIPAddress(HttpServletRequest request) 
  {
	String ip = null;
	String ipAddresses = request.getHeader("X-Forwarded-For");
	if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))         {
		ipAddresses = request.getHeader("Proxy-Client-IP");
	}
	if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))         {
		ipAddresses = request.getHeader("WL-Proxy-Client-IP");
	}
	if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))         {
		ipAddresses = request.getHeader("HTTP_CLIENT_IP");
	}
	if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses))         {       
		ipAddresses = request.getHeader("X-Real-IP");
	}
	if (ipAddresses != null && ipAddresses.length() != 0) 
        {
		ip = ipAddresses.split(",")[0];
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) 
        {
		ip = request.getRemoteAddr();
	}
	return ip;
}
   


}
//CHECKSTYLE:ON
