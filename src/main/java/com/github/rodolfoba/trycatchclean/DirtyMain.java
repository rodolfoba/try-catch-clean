package com.github.rodolfoba.trycatchclean;

import java.net.MalformedURLException;
import java.net.URL;

public class DirtyMain {

    public static void main(String[] args) throws MalformedURLException {
        
        final URL dashboard = new URL("http://dashboard");
        final URL loginPage = new URL("http://login");
        
        final String userId = "userid";
        final String pwd = "password";
        final long twoFactorPwd = 12;
        User user;
        
        Authenticator authenticator = new Authenticator();
        try {
            user = authenticator.login(userId, pwd);
        } catch (Exception e) {
            try {
                user = authenticator.gmailLogin(userId, pwd);
            } catch (Exception gmailException) {
                Dispatcher.redirect(loginPage);
                return;
            }
        }
        
        URL target;
        try {
            authenticator.twoFactor(user, twoFactorPwd);
            target = dashboard;
        } catch (Exception e) {
            target = loginPage;
        }
        
        Dispatcher.redirect(target);
    }
    
}
