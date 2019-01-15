package org.apereo.cas.configuration.model.support.jdbc;


import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
//import org.apereo.cas.web.support.WebUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * This is {@link JdbcAuthenticationProperties}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public class JdbcAuthenticationProperties {
    private List<Search> search = new ArrayList();
    private List<Encode> encode = new ArrayList();
    private List<Query> query = new ArrayList();
    private List<Bind> bind = new ArrayList();
    private List<Store> store = new ArrayList();

    public List<Search> getSearch() {
        return search;
    }

    public void setSearch(final List<Search> search) {
        this.search = search;
    }

    public List<Encode> getEncode() {
        return encode;
    }

    public void setEncode(final List<Encode> encode) {
        this.encode = encode;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(final List<Query> query) {
        this.query = query;
    }

    public List<Store> getStore(){
        return store;
    }
  
    public void setStore(final List<Store> store) {
        this.store = store;
    }

    public List<Bind> getBind() {
        return bind;
    }

    public void setBind(final List<Bind> bind) {
        this.bind = bind;
    }


    public static class Query extends AbstractJpaProperties {
        private String sql;
        private String credentialCriteria;
        private String fieldPassword;
        private String fieldExpired;
        private String fieldDisabled;
        private List principalAttributeList = new ArrayList();

        @NestedConfigurationProperty
        private PrincipalTransformationProperties principalTransformation =
                new PrincipalTransformationProperties();

        @NestedConfigurationProperty
        private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

        private String name;

        private int order = Integer.MAX_VALUE;

        public List getPrincipalAttributeList() {
            return principalAttributeList;
        }

        public void setPrincipalAttributeList(final List principalAttributeList) {
            this.principalAttributeList = principalAttributeList;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(final int order) {
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public PasswordEncoderProperties getPasswordEncoder() {
            return passwordEncoder;
        }

        public void setPasswordEncoder(final PasswordEncoderProperties passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(final String sql) {
            this.sql = StringUtils.replace(sql, "{user}", "?");
        }

        public PrincipalTransformationProperties getPrincipalTransformation() {
            return principalTransformation;
        }

        public void setPrincipalTransformation(final PrincipalTransformationProperties principalTransformation) {
            this.principalTransformation = principalTransformation;
        }

        public String getCredentialCriteria() {
            return credentialCriteria;
        }

        public void setCredentialCriteria(final String credentialCriteria) {
            this.credentialCriteria = credentialCriteria;
        }

        public String getFieldPassword() {
            return fieldPassword;
        }

        public void setFieldPassword(final String fieldPassword) {
            this.fieldPassword = fieldPassword;
        }

        public String getFieldExpired() {
            return fieldExpired;
        }

        public void setFieldExpired(final String fieldExpired) {
            this.fieldExpired = fieldExpired;
        }

        public String getFieldDisabled() {
            return fieldDisabled;
        }

        public void setFieldDisabled(final String fieldDisabled) {
            this.fieldDisabled = fieldDisabled;
        }
    }

    public static class Store extends AbstractJpaProperties {
        private String sql;
        private String procName;
        private String clientip;
        private String clientos;
        private String clientbrowser;
        private String useragent;
        private String clientcountry;
        private String clientarea;
        private String retStr;
        private String credentialCriteria;
        private String fieldPassword;
        private String fieldExpired;
        private String fieldDisabled;
        private List principalAttributeList = new ArrayList();

        @NestedConfigurationProperty
        private PrincipalTransformationProperties principalTransformation =
                new PrincipalTransformationProperties();

        @NestedConfigurationProperty
        private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

        private String name;

        private int order = Integer.MAX_VALUE;


        public List getPrincipalAttributeList() {
            return principalAttributeList;
        }

        public void setPrincipalAttributeList(final List principalAttributeList) {
            this.principalAttributeList = principalAttributeList;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(final int order) {
            this.order = order;
        }
        //---------------------------------------------

        public String getClientip() {
            return clientip;
        }

        public void setClientip(final String clientip) {
            this.clientip = clientip;
        }
       
        public String getClientos() {
            return clientos;
        }

        public void setClientos(final String clientos) {
            this.clientos = clientos;
        }

        public String getClientbrowser() {
            return clientbrowser;
        }
        public void setClientbrowser(final String clientbrowser) {
            this.clientbrowser = clientbrowser;
        }

        public String getUseragent() {
        
            // final String myagent = WebUtils.getHttpServletRequestUserAgent();
            // System.out.println("--NNNNNNNNNNN-----: " + myagent);
           
             return useragent;
        }
        public void setUseragent(final String useragent) {
            this.useragent = useragent;
        }

        public String getClientcountry() {
            return clientcountry;
        }
        public void setClientcountry(final String clientcountry) {
            this.clientcountry = clientcountry;
        }

        public String getClientarea() {
            return clientarea;
        }
        public void setClientarea(final String clientarea) {
            this.clientarea = clientarea;
        }
        //--------------------------------------------- 
        public String getProcName(){
            return procName;
        }         
       
        public void setProcName(final String procName){
           this.procName = procName;
        }

        public String getRetStr(){
            return retStr;
        }
 
        public void setRetStr(final String retStr){
            this.retStr = retStr;
        }
        
        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public PasswordEncoderProperties getPasswordEncoder() {
            return passwordEncoder;
        }

        public void setPasswordEncoder(final PasswordEncoderProperties passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(final String sql) {
            this.sql = StringUtils.replace(sql, "{user}", "?");
        }

        public PrincipalTransformationProperties getPrincipalTransformation() {
            return principalTransformation;
        }

        public void setPrincipalTransformation(final PrincipalTransformationProperties principalTransformation) {
            this.principalTransformation = principalTransformation;
        }

        public String getCredentialCriteria() {
            return credentialCriteria;
        }

        public void setCredentialCriteria(final String credentialCriteria) {
            this.credentialCriteria = credentialCriteria;
        }

        public String getFieldPassword() {
            return fieldPassword;
        }

        public void setFieldPassword(final String fieldPassword) {
            this.fieldPassword = fieldPassword;
        }

        public String getFieldExpired() {
            return fieldExpired;
        }

        public void setFieldExpired(final String fieldExpired) {
            this.fieldExpired = fieldExpired;
        }

        public String getFieldDisabled() {
            return fieldDisabled;
        }

        public void setFieldDisabled(final String fieldDisabled) {
            this.fieldDisabled = fieldDisabled;
        }
    }


    public static class Bind extends AbstractJpaProperties {
        private String credentialCriteria;

        @NestedConfigurationProperty
        private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

        @NestedConfigurationProperty
        private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();

        private String name;
        private Integer order;

        public Integer getOrder() {
            return order;
        }

        public void setOrder(final Integer order) {
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public PasswordEncoderProperties getPasswordEncoder() {
            return passwordEncoder;
        }

        public void setPasswordEncoder(final PasswordEncoderProperties passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public PrincipalTransformationProperties getPrincipalTransformation() {
            return principalTransformation;
        }

        public void setPrincipalTransformation(final PrincipalTransformationProperties principalTransformation) {
            this.principalTransformation = principalTransformation;
        }

        public String getCredentialCriteria() {
            return credentialCriteria;
        }

        public void setCredentialCriteria(final String credentialCriteria) {
            this.credentialCriteria = credentialCriteria;
        }
    }

    public static class Search extends AbstractJpaProperties {
        private String fieldUser;
        private String fieldPassword;
        private String tableUsers;
        private String credentialCriteria;

        @NestedConfigurationProperty
        private PrincipalTransformationProperties principalTransformation =
                new PrincipalTransformationProperties();

        @NestedConfigurationProperty
        private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

        private String name;

        private int order = Integer.MAX_VALUE;

        public int getOrder() {
            return order;
        }

        public void setOrder(final int order) {
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public PasswordEncoderProperties getPasswordEncoder() {
            return passwordEncoder;
        }

        public void setPasswordEncoder(final PasswordEncoderProperties passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public String getFieldUser() {
            return fieldUser;
        }

        public void setFieldUser(final String fieldUser) {
            this.fieldUser = fieldUser;
        }

        public String getFieldPassword() {
            return fieldPassword;
        }

        public void setFieldPassword(final String fieldPassword) {
            this.fieldPassword = fieldPassword;
        }

        public String getTableUsers() {
            return tableUsers;
        }

        public void setTableUsers(final String tableUsers) {
            this.tableUsers = tableUsers;
        }

        public PrincipalTransformationProperties getPrincipalTransformation() {
            return principalTransformation;
        }

        public void setPrincipalTransformation(final PrincipalTransformationProperties principalTransformation) {
            this.principalTransformation = principalTransformation;
        }

        public String getCredentialCriteria() {
            return credentialCriteria;
        }

        public void setCredentialCriteria(final String credentialCriteria) {
            this.credentialCriteria = credentialCriteria;
        }
    }

    public static class Encode extends AbstractJpaProperties {
        private String credentialCriteria;
        private String algorithmName;
        private String sql;
        private String passwordFieldName = "password";
        private String saltFieldName = "salt";
        private String expiredFieldName;
        private String disabledFieldName;
        private String numberOfIterationsFieldName = "numIterations";
        private long numberOfIterations;
        private String staticSalt;
        private String name;

        @NestedConfigurationProperty
        private PrincipalTransformationProperties principalTransformation =
                new PrincipalTransformationProperties();

        @NestedConfigurationProperty
        private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

        private int order = Integer.MAX_VALUE;

        public int getOrder() {
            return order;
        }

        public void setOrder(final int order) {
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public PasswordEncoderProperties getPasswordEncoder() {
            return passwordEncoder;
        }

        public void setPasswordEncoder(final PasswordEncoderProperties passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }

        public void setAlgorithmName(final String algorithmName) {
            this.algorithmName = algorithmName;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(final String sql) {
            this.sql = sql;
        }

        public String getPasswordFieldName() {
            return passwordFieldName;
        }

        public void setPasswordFieldName(final String passwordFieldName) {
            this.passwordFieldName = passwordFieldName;
        }

        public String getSaltFieldName() {
            return saltFieldName;
        }

        public void setSaltFieldName(final String saltFieldName) {
            this.saltFieldName = saltFieldName;
        }

        public String getExpiredFieldName() {
            return expiredFieldName;
        }

        public void setExpiredFieldName(final String expiredFieldName) {
            this.expiredFieldName = expiredFieldName;
        }

        public String getDisabledFieldName() {
            return disabledFieldName;
        }

        public void setDisabledFieldName(final String disabledFieldName) {
            this.disabledFieldName = disabledFieldName;
        }

        public String getNumberOfIterationsFieldName() {
            return numberOfIterationsFieldName;
        }

        public void setNumberOfIterationsFieldName(final String numberOfIterationsFieldName) {
            this.numberOfIterationsFieldName = numberOfIterationsFieldName;
        }

        public long getNumberOfIterations() {
            return numberOfIterations;
        }

        public void setNumberOfIterations(final long numberOfIterations) {
            this.numberOfIterations = numberOfIterations;
        }

        public String getStaticSalt() {
            return staticSalt;
        }

        public void setStaticSalt(final String staticSalt) {
            this.staticSalt = staticSalt;
        }

        public PrincipalTransformationProperties getPrincipalTransformation() {
            return principalTransformation;
        }

        public void setPrincipalTransformation(final PrincipalTransformationProperties principalTransformation) {
            this.principalTransformation = principalTransformation;
        }

        public String getCredentialCriteria() {
            return credentialCriteria;
        }

        public void setCredentialCriteria(final String credentialCriteria) {
            this.credentialCriteria = credentialCriteria;
        }
    }
}
