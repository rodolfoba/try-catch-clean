package com.github.rodolfoba.trycatchclean;

import static java.util.Arrays.asList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class StrategyMain {

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
        private List<AuthenticatorStrategy> strategies;

        public AuthenticatorWrapper(String userId, String pwd, long twoFactorPwd) {
            super();
            this.userId = userId;
            this.pwd = pwd;
            this.twoFactorPwd = twoFactorPwd;
            this.authenticator = new Authenticator();
        }

        public void login() throws Exception {
            strategies = asList(new LoginAuthenticatorStrategy(), new GmailLoginAuthenticatorStrategy());
            tryStrategies();
            authenticator.twoFactor(user, twoFactorPwd);
        }
        
        private void tryStrategies() throws Exception {
            for (AuthenticatorStrategy strategy : strategies) {
                user = tryLogin(strategy);
                if (null != user) {
                    return;
                }
            }
            
            throw new Exception("Login exception");
        }
        
        private User tryLogin(AuthenticatorStrategy strategy) {
            try {
                user = strategy.login(userId, pwd);
            } catch (Exception e) {
                user = null;
            }
            
            return user;
        }
        
        interface AuthenticatorStrategy {
            User login(String userId, String pwd) throws Exception;
        }
        
        class LoginAuthenticatorStrategy implements AuthenticatorStrategy {

            @Override
            public User login(String userId, String pwd) throws Exception {
                return new Authenticator().login(userId, pwd);
            }
        }
        
        class GmailLoginAuthenticatorStrategy implements AuthenticatorStrategy {

            @Override
            public User login(String userId, String pwd) throws Exception {
                return new Authenticator().gmailLogin(userId, pwd);
            }
        }
    }
}
