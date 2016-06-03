/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.dto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.Size;
import xyz.codingmentor.constraint.PasswordConstraint;
import xyz.codingmentor.entity.User;

/**
 *
 * @author keni
 */
public class UserDTO {

    private User user;

    @Size(min = 1, message = "This field has to be filled.")
    @PasswordConstraint(message = "Wrong password format.")
    private String password;
    
     public User makeUser() {
        String outcome;
        outcome = this.hashPassword(password);
        user.setPassword(outcome);
        return this.user;
    }

    public String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedPassword = bigInt.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPassword;
    }
    public UserDTO() {
        user = new User();
    }

    public UserDTO(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
