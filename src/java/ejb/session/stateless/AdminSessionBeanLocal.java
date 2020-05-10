/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.Offences;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.OffenceExistException;
import util.exception.OffenceNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author decimatum
 */
@Local
public interface AdminSessionBeanLocal {

    public Long createNewAdmin(Admin newAdminEntity) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<Admin> retrieveAllAdmins();

    public Admin retrieveAdminByUserId(Long userId) throws UserNotFoundException;

    public Admin retrieveAdminByUsername(String username) throws UserNotFoundException;

    public void updateAdmin(Admin admin) throws InputDataValidationException, UserNotFoundException;

    public void deleteAdmin(Long userId) throws UserNotFoundException;

    public Long createNewOffence(Offences newOffenceEntity) throws UnknownPersistenceException, InputDataValidationException, OffenceExistException;
    
    public void approveSeller(Long sellerId) throws UserNotFoundException;

    public List<Offences> retrieveAllOffences();

    public Offences retrieveOffenceById(Long offenceId) throws OffenceNotFoundException;
}
