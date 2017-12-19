package com.github.rodolfoba.trycatchclean;

class Authenticator {

    public User login(String id, String pwd) throws Exception {
        throw new Exception("Login exception");
//        return new User(id, "User Name");
    }
    
    public User gmailLogin(String id, String pwd) throws Exception {
//        throw new Exception("GmailLogin exception");
        return new User(id, "User Name");
    }
    
    public void twoFactor(User user, long pwd) {
        
    }
    
}
