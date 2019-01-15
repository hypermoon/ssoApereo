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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
//import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
//import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
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
 
   private  final String clientip;
   private  final String clientos;
   private  final String clientbrowser;
   private  final String useragent;
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

        final String username = credentials.getUsername();
        final String password = credentials.getPassword();
        final String encryptedPassword = credentials.getPassword();  

        System.out.println("----xxxxx---usernameParameterName: " + usernameParameterName);
        System.out.println("----zzzzz---username: " + username);
        System.out.println("----aaaa---originpassword: " + originPassword);
        System.out.println("----bbbb---password: " + password);
        
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(this.usernameParameterName, username, Types.VARCHAR)
                .addValue(this.passwordParameterName, encryptedPassword, Types.VARCHAR)
                .addValue("pClientIp", this.clientip, Types.NVARCHAR)
                .addValue("pClientOs", this.clientos, Types.NVARCHAR)
                .addValue("pClientBrowser", this.clientbrowser, Types.NVARCHAR)
                .addValue("pHttpUserAgent", this.useragent, Types.NVARCHAR)
                .addValue("pClientCountry", this.clientcountry, Types.NVARCHAR)
                .addValue("pClientArea", this.clientarea, Types.NVARCHAR);

   /*     SimpleJdbcCall sp = new SimpleJdbcCall(this.getDataSource())
                .withoutProcedureColumnMetaDataAccess()
                .withProcedureName(procedureName)
                .declareParameters(new SqlParameter(this.usernameParameterName, Types.VARCHAR))
                .declareParameters(new SqlParameter(this.passwordParameterName, Types.VARCHAR))
                .declareParameters(new SqlParameter("pClientIp", Types.NVARCHAR))
                .declareParameters(new SqlParameter("pClientOs", Types.NVARCHAR))
                .declareParameters(new SqlParameter("pClientBrowser", Types.NVARCHAR))
                .declareParameters(new SqlParameter("pHttpUserAgent", Types.NVARCHAR))
                .declareParameters(new SqlParameter("pClientCountry", Types.NVARCHAR))
                .declareParameters(new SqlParameter("pClientArea", Types.NVARCHAR))
                 //.declareParameters(new SqlOutParameter("RETURN_VALUE", Types.INTEGER))
                .returningResultSet("result1", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });*/

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
        /*        .returningResultSet("result1", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        });*/
  
        System.out.println("----cccccc---procedurename: " + procedureName);
        System.out.println("----dddddd---procip: " + this.clientip);
 
       try {
             System.out.println("----Step111111---: ");
             System.out.println("----Step1 ip: " + this.clientip);
             System.out.println("----Step1 os: " + this.clientos);
             System.out.println("----Step1 browser: " + this.clientbrowser);
             System.out.println("----Step1 useragent: " + this.useragent);
             System.out.println("----Step1 clientcountry: " + this.clientcountry);
             System.out.println("----Step1 cleintarea: " + this.clientarea);

             Map<String, Object> results = sp.execute(params);

             System.out.println("----Step1.51.51.51.51.5---: ");

             ArrayList result1 = (ArrayList) results.get("result1");
            // String dbResult = (String) result1.get(0);

             System.out.println("----Step222222---: ");
           //  System.out.println("----eeeeee---dbResult: " + dbResult);

           //  String strpara = (String) results.get("pClientIp");
           //  String strret = (String) results.get("pReturn");
             String strret =  results.get("RETURN_VALUE").toString();
             

             System.out.println("----Step333333---: ");
           //  String str1111 = (String) result1.get("pReturn");
          //   String str2222 = (String) result1.get("pClientIp");
        
             System.out.println("----ggggggg---strret: " + strret);
           //  System.out.println("----ggggggg---str2222: " + str2222);
            

            // System.out.println("----fffffff---ParaMeterIp: " + strpara);
             String dbResult = strret;
           //  System.out.println("----fffffff---ParaReturn: " + strret);

             if(dbResult.equalsIgnoreCase(this.successReturnValue)) //true is equals
             {
                System.out.println("-----EEEEEEEEquals -------");
                return createHandlerResult(credentials, this.principalFactory.createPrincipal(username), null);
             }
             else                                                   //false is unequals
             {
                System.out.println("------------UUUUUUUUUUUUUUnEqualssssss---------");
                return null;
             }




        }
        catch (DataAccessException ex) {
          
             System.out.println("----Error: Exception! " );
             return null;
        }
    }

}
//CHECKSTYLE:ON
