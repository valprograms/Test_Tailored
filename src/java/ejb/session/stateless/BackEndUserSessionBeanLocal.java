/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.User;
import javax.ejb.Local;
import util.exception.InvalidLoginCredentialException;
import util.exception.UserNotFoundException;

/**
 *
 * @author decimatum
 */
@Local
public interface BackEndUserSessionBeanLocal {

    public User login(String username, String password) throws InvalidLoginCredentialException;
    public User retrieveUserByUserId(Long userId) throws UserNotFoundException;
    
}
