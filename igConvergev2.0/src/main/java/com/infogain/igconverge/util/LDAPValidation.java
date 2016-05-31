/**
 * 
 */
package com.infogain.igconverge.util;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @author kamal
 *
 */

@Service
public class LDAPValidation 
{
	String errorMessage = "";
	public HashMap<String, String> loginCredentialValidation(String userName, String password)
	{
		errorMessage = "";
		boolean status = verifyDomainUser("igglobal", userName, password);
		HashMap<String, String> ldapResponse = new HashMap<String, String>();
		if(status)
			ldapResponse.put("status", "success");
		else
			ldapResponse.put("status", "failure");
		ldapResponse.put("message", errorMessage);
		return ldapResponse;
	}
	
	private boolean verifyDomainUser(String domain, String userName, String password)
    {
      boolean status = false;
      System.out.println("User Name to Login: " + userName);
      System.out.println("domain::" + domain);
      if ((userName != null) && (password != null))
      {
        Hashtable<String, String> env = new Hashtable<String, String>();

        if (password.equals("")) 
        {
          password = "blank";
        }
        if (userName.equals("")) 
        {
          userName = "blank";
        }
        String keystore = "/usr/java/jdk1.5.0_01/jre/lib/security/cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);

        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        //env.put("java.naming.factory.initial", ldapInitial);

        env.put("java.naming.security.authentication", "simple");
        //env.put("java.naming.security.authentication", ldapAuthentication);

        env.put("java.naming.security.credentials", password);

//        env.put("java.naming.provider.url", "ldap://172.18.65.35:3268");
//        env.put("java.naming.provider.url", "ldap://172.18.65.221:3268");
        env.put("java.naming.provider.url", "ldap://172.18.65.35:389");
        //env.put("java.naming.provider.url", ldapUrl);

        LdapContext ctx = null;
        try
        {
          env.put("java.naming.security.principal", domain + "\\" + userName);

          ctx = new InitialLdapContext(env, null);

          System.out.println("URL:::" + ctx.getEnvironment().get("java.naming.provider.url"));

          if (ctx != null) {
            status = true;
          }

        }
        catch (AuthenticationException e)
        {
          errorMessage = "LDAP Authentication Error, Report to dev team";
          System.out.println(e.getLocalizedMessage());
          try
          {
            if (ctx != null)
              ctx.close();
          }
          catch (Exception en) 
          {
            System.err.println("Final Error");
            System.err.println(en.getMessage());
          }
        }
        catch (NamingException e)
        {
          errorMessage = e.getMessage();
          if(errorMessage.contains("52e"))
          {
        	  	errorMessage = "Credentials are not valid";
          }
          else if (errorMessage.contains("525")) 
          {
        	  	errorMessage = "Invalid user, user not exist";
          }
          else if (errorMessage.contains("530")) 
          {
        	  	errorMessage = "Not permitted to logon at this time";
          }
          else if (errorMessage.contains("531")) 
          {
        	  	errorMessage = "Not permitted to logon at this time";
          }
          else if (errorMessage.contains("532")) 
          {
        	  	errorMessage = "Password expired, Reset your account password";
          }
          else if (errorMessage.contains("533")) 
          {
        	  	errorMessage = "Your account has been disabled";
          }
          else if (errorMessage.contains("701")) 
          {
        	  	errorMessage = "Your account has expired";
          }
          else if (errorMessage.contains("773")) 
          {
        	  	errorMessage = "Reset your account password";
          }
          else if (errorMessage.contains("775")) 
          {
        	  	errorMessage = "Your account is locked";
          }
          System.err.println(errorMessage);
          try
          {
            if (ctx != null)
              ctx.close();
          }
          catch (Exception ex) 
          {
            System.err.println("Final Error");
            System.err.println(ex.getMessage());
          }
        }
        catch (Exception e)
        {
          System.out.println(e.getLocalizedMessage());
          errorMessage = "Server Error, Report to dev team";
          try
          {
            if (ctx != null)
              ctx.close();
          }
          catch (Exception et) 
          {
            System.err.println("Final Error");
            System.err.println(et.getMessage());
          }
        }
        finally
        {
          try
          {
            if (ctx != null)
              ctx.close();
          }
          catch (Exception e) {
            System.err.println("Final Error");
            System.err.println(e.getMessage());
          }
        }
      }

      System.out.println("statuss:::::::::::;" + status);
      return status;
    }
}
