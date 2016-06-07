package xyz.codingmentor.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import xyz.codingmentor.dto.UserDTO;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "MoviePU")
    private EntityManager em;
    
    public List<Object[]> userByMostComment() {
        TypedQuery<Object[]> q = em.createNamedQuery("userByMostComment", Object[].class);
        return q.getResultList();
    }

    public static String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedPassword = bigInt.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(UserDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPassword;
    }
}
