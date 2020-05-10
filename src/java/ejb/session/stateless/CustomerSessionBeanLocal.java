/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import entity.Artwork;
import entity.OrderHistory;
import entity.Transaction;
import util.exception.TransactionNotFoundException;
import entity.Customer;
import entity.Offences;
import entity.SelfCareBox;
import entity.Seller;
import entity.Tag;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.OffenceNotFoundException;
import util.exception.OrderHistoryNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UnableToReportUserException;
import util.exception.UnknownPersistenceException;
import util.exception.UserAlreadyBannedException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;


/**
 *
 * @author decimatum
 */
@Local
public interface CustomerSessionBeanLocal {


    public List<Transaction> retrieveAllMyTransactions(Long customerId);

    public Transaction retrieveTransactionById(Long transactionId) throws TransactionNotFoundException;

//    public Long checkoutShoppinngCart(Transaction transaction, List<OrderHistory> orders);

    public Customer retrieveCustomerById(Long customerId) throws UserNotFoundException;

    public Customer retrieveCustomerByUsername(String username) throws UserNotFoundException;

    public List<Customer> retrieveAllCustomers();

    public void updateMyCustomerProfile(Customer updatedCustomer) throws UserNotFoundException;

    public Long createNewCustomer(Customer newCustomerEntity, List<Tag> tagsList) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException, TagNotFoundException;

    public void deleteCustomer(Long customerId) throws UserNotFoundException;
    
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;

    public List<Offences> retrieveAllOffences();

    public Offences retrieveOffenceById(Long offenceId) throws OffenceNotFoundException;

    public void reportAUser(Long customerId, Long offenceId) throws UnableToReportUserException, UserAlreadyBannedException;

    public void followASeller (Long customerId, Long sellerId) throws UserNotFoundException;

    public List<Seller> viewAllFollowedSellers (Long customerId) throws UserNotFoundException;

    public void unfollowASeller (Long customerId, Long sellerId) throws UserNotFoundException;

    public List<Artwork> viewAllArtworksByFollowedSellers (Long customerId) throws UserNotFoundException;

    public List<SelfCareBox> viewAllSelfCareBoxByFollowedSellers (Long customerId) throws UserNotFoundException;
    
    public OrderHistory retrieveOrderHistoryById(Long orderHistoryId) throws OrderHistoryNotFoundException;

    public void updateMyCustomerPreferences(Customer customer, List<Tag> updatedTags) throws UserNotFoundException, TagNotFoundException;
    
}