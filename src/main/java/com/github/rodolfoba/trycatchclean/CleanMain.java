package com.github.rodolfoba.trycatchclean;

import java.net.MalformedURLException;
import java.net.URL;

public class CleanMain {

    public static void main(String[] args) throws MalformedURLException {
        
        final URL dashboard = new URL("http://dashboard");
        final URL loginPage = new URL("http://login");
        
        final String userId = "userid";
        final String pwd = "password";
        final long twoFactorPwd = 12;
        
        AuthenticatorWrapper wrapper = new AuthenticatorWrapper(userId, pwd, twoFactorPwd);
        URL target;
        try {
            wrapper.login();
            target = dashboard;
        } catch (Exception e) {
           target = loginPage;
        }
        
        Dispatcher.redirect(target); 
    }
    
    static class AuthenticatorWrapper {
        
        private final String userId;
        private final String pwd;
        private final long twoFactorPwd;
        private final Authenticator authenticator;
        private User user;
        
        AuthenticatorWrapper(String userId, String pwd, long twoFactorPwd) {
            super();
            this.userId = userId;
            this.pwd = pwd;
            this.twoFactorPwd = twoFactorPwd;
            this.authenticator = new Authenticator();
        }
        
        public void login() throws Exception {
            user = tryLogin();
            if (null == user) {
                user = authenticator.gmailLogin(userId, pwd);
            }
            
            authenticator.twoFactor(user, twoFactorPwd);
        }
        
        private User tryLogin() {
            try {
                user = authenticator.login(userId, pwd);
            } catch (Exception e) {
                user = null;
            }
            
            return user;
        }
    }
}
