package com.github.rodolfoba.trycatchclean;

class Authenticator {

    public User login(String id, String pwd) throws Exception {
        return new User(id, "User Name");
    }
    
    public User gmailLogin(String id, String pwd) throws Exception {
        return new User(id, "User Name");
    }
    
    public void twoFactor(User user, long pwd) {
        
    }
    
}
