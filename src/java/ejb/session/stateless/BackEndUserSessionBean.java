/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.Customer;
import entity.Seller;
import entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.UserNotFoundException;
import util.security.CryptographicHelper;

/**
 *
 * @author decimatum
 */
@Stateless
public class BackEndUserSessionBean implements BackEndUserSessionBeanLocal {

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;

    @Override
    public User login(String username, String password) throws InvalidLoginCredentialException {
        try {
            User user = retrieveUserByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + user.getSalt()));
            System.out.println("reached here");
            if (user instanceof Seller) {
                Seller seller = (Seller) user;
                if (seller.isIsBanned()) {
                    throw new InvalidLoginCredentialException("User is banned due to offences committed!");
                }
                if (seller.getPassword().equals(passwordHash) && !seller.isIsDeleted() && !seller.isIsBanned()) {
                    return user;
                } else {
                    throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
                }
            } else if (user instanceof Admin){
                Admin admin = (Admin) user;
                if (admin.getPassword().equals(passwordHash)) {
                    return user;
                } else {
                    throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
                }
            } else {
                Customer customer = (Customer) user;
                if (customer.getPassword().equals(passwordHash)) {
                    return user;
                } else {
                    throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
                }
            }
        } catch (UserNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    public User retrieveUserByUsername(String username) throws UserNotFoundException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username =:inUsername AND u.isDeleted = FALSE");
        query.setParameter("inUsername", username);

        try {
            return (User) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new UserNotFoundException("User with username " + username + " does not exist!");
        }
    }

    public User retrieveUserByUserId(Long userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);

        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("UserId " + userId + " does not exist!");
        }
    }
}
