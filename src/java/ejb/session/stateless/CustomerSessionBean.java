/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Artwork;
import entity.Transaction;
import entity.Customer;
import entity.Offences;
import entity.OrderHistory;
import entity.Post;
import entity.Review;
import entity.SelfCareBox;
import entity.Seller;
import entity.Tag;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.OffenceNotFoundException;
import util.exception.OrderExistException;
import util.exception.OrderHistoryNotFoundException;
import util.exception.TransactionExistException;
import util.exception.TransactionNotFoundException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import util.exception.UnableToReportUserException;
import util.exception.UnknownPersistenceException;
import util.exception.UserAlreadyBannedException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;
import util.security.CryptographicHelper;

/**
 *
 * @author decimatum
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;
    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    private ValidatorFactory validatorFactory;
    private Validator validator;

    public CustomerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<Transaction> retrieveAllMyTransactions(Long customerId) {
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.customer.userId = :inUserId");
        query.setParameter("inUserId", customerId);
        List<Transaction> transactions = query.getResultList();

        for (Transaction transaction : transactions) {
            transaction.getOrders().size();
        }

        return transactions;
    }

    @Override
    public Transaction retrieveTransactionById(Long transactionId) throws TransactionNotFoundException {
        Transaction transaction = em.find(Transaction.class, transactionId);

        if (transaction != null) {
            transaction.getOrders().size();

            return transaction;
        } else {
            throw new TransactionNotFoundException("Transaction ID " + transactionId + " does not exist!");
        }
    }

    @Override
    public OrderHistory retrieveOrderHistoryById(Long orderHistoryId) throws OrderHistoryNotFoundException {
        OrderHistory orderHistory = em.find(OrderHistory.class, orderHistoryId);

        if (orderHistory != null) {
            return orderHistory;
        } else {
            throw new OrderHistoryNotFoundException("OrderHistory ID " + orderHistoryId + " does not exist!");
        }
    }

//    public Long checkoutShoppinngCart(Transaction transaction, List<OrderHistory> orders)
//    {
//        
//    }
    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Customer customer = retrieveCustomerByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + customer.getSalt()));
            if (customer.isIsBanned()) {
                throw new InvalidLoginCredentialException("User is banned due to offences committed!");
            }
            if (customer.getPassword().equals(passwordHash) && !customer.isIsBanned() && !customer.isIsDeleted()) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (UserNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    //also sets the customer's tags (aka preferences)
    @Override
    public Long createNewCustomer(Customer newCustomerEntity, List<Tag> tagsList) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException, TagNotFoundException {
        try {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(newCustomerEntity);

            if (constraintViolations.isEmpty()) {
                newCustomerEntity.setTags(new ArrayList<Tag>());
                em.persist(newCustomerEntity);
                for(Tag tag : tagsList){
                    tag = tagSessionBeanLocal.retrieveTagByTagId(tag.getTagId());
                    newCustomerEntity.getTags().add(tag);
                    tag.getCustomers().add(newCustomerEntity);
                }
                em.flush();

                return newCustomerEntity.getUserId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new UserUsernameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Customer>> constraintViolations) {

        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    @Override
    //this returns the customer even if it is deleted
    public Customer retrieveCustomerById(Long userId) throws UserNotFoundException {
        Customer customer = em.find(Customer.class, userId);

        if (customer != null) {
            return customer;
        } else {
            throw new UserNotFoundException("CustomerId " + userId + " does not exist!");
        }
    }

    @Override
    public Customer retrieveCustomerByUsername(String username) throws UserNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.username =:inUsername AND c.isDeleted = FALSE");
        query.setParameter("inUsername", username);

        try {
            return (Customer) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new UserNotFoundException("User with username " + username + " does not exist!");
        }
    }

    @Override
    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.isDeleted = FALSE");
        return query.getResultList();
    }

    @Override
    //this method just updates the customer's profile (basic information)
    public void updateMyCustomerProfile(Customer updatedCustomer) throws UserNotFoundException {
        if (updatedCustomer != null && updatedCustomer.getUserId() != null) {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(updatedCustomer);
            if (constraintViolations.isEmpty()) {
                Customer curCustomer;
                try {
                    curCustomer = retrieveCustomerByUsername(updatedCustomer.getUsername());
                } catch (UserNotFoundException ex) {
                    throw new UserNotFoundException("Customer username " + updatedCustomer.getUsername() + " does not exist!");
                }
                curCustomer.setEmail(updatedCustomer.getEmail());
                curCustomer.setFirstName(updatedCustomer.getFirstName());
                curCustomer.setLastName(updatedCustomer.getLastName());
                curCustomer.setPassword(updatedCustomer.getPassword());
                curCustomer.setShippingAddress(updatedCustomer.getShippingAddress());
                curCustomer.setShippingPostalCode(updatedCustomer.getShippingPostalCode());
                curCustomer.setShippingUnitNum(updatedCustomer.getShippingUnitNum());
                // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
            }
        }
    }

    @Override
    //this method just updates the relationship btwn customer and tags
    public void updateMyCustomerPreferences(Customer customer, List<Tag> updatedTags) throws UserNotFoundException, TagNotFoundException {
        if(customer != null  && customer.getUserId() != null){
            try {
                Customer curCustomer = retrieveCustomerById(customer.getUserId());
                List<Tag> currTagList = curCustomer.getTags();
                //if the updatedTags list does not contain current tag, remove association with current customer
                for (Tag curTag : currTagList) {
                    if (!updatedTags.contains(curTag)) {
                        curTag = tagSessionBeanLocal.retrieveTagByTagId(curTag.getTagId());
                        List<Customer> associatedCustomers = curTag.getCustomers();
                        curTag.getCustomers().remove(curCustomer);
                    }
                }
                //if the updatedTag is not associated with current customer, add association
                for(Tag tag : updatedTags){
                    tag = tagSessionBeanLocal.retrieveTagByTagId(tag.getTagId());
                    List<Customer> associatedCustomers = tag.getCustomers();
                    if (!associatedCustomers.contains(curCustomer)) {
                        tag.getCustomers().add(curCustomer);
                    }
                }
                curCustomer.setTags(updatedTags);
            } catch (UserNotFoundException ex) {
                throw new UserNotFoundException("CustomerId " + customer.getUserId() + " does not exist!");
            } catch (TagNotFoundException ex) {
                throw new TagNotFoundException("Tag does not exist!");
            }
        }
    }

    @Override
    public void followASeller(Long customerId, Long sellerId) throws UserNotFoundException {
        if (customerId != null) {
            try {
                Customer curCustomer = retrieveCustomerById(customerId);
                Seller sellerToFollow = sellerSessionBeanLocal.retrieveSellerById(sellerId);
                //if the customer isn't already following the seller, we add the seller to the current list
                if (!curCustomer.getSellers().contains(sellerToFollow)) {
                    curCustomer.getSellers().add(sellerToFollow);
                    sellerToFollow.getCustomers().add(curCustomer);
                }
            } catch (UserNotFoundException ex) {
                throw new UserNotFoundException("CustomerId " + customerId + " or SellerId " + sellerId + " does not exist!");
            }
        }
    }

    @Override
    public void unfollowASeller(Long customerId, Long sellerId) throws UserNotFoundException {
        if (customerId != null) {
            try {
                Customer curCustomer = retrieveCustomerById(customerId);
                Seller sellerToFollow = sellerSessionBeanLocal.retrieveSellerById(sellerId);
                //if the customer is following the seller, remove association
                if (curCustomer.getSellers().contains(sellerToFollow)) {
                    curCustomer.getSellers().remove(sellerToFollow);
                    sellerToFollow.getCustomers().remove(curCustomer);
                }
            } catch (UserNotFoundException ex) {
                throw new UserNotFoundException("CustomerId " + customerId + " does not exist!");
            }
        }
    }

    @Override
    public List<Seller> viewAllFollowedSellers(Long customerId) throws UserNotFoundException {
        try {
            Customer curCustomer = retrieveCustomerById(customerId);
            return curCustomer.getSellers();
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("CustomerId " + customerId + " does not exist!");
        }
    }

    @Override
    public List<Artwork> viewAllArtworksByFollowedSellers(Long customerId) throws UserNotFoundException {
        try {
            Customer curCustomer = retrieveCustomerById(customerId);
            List<Seller> sellers = curCustomer.getSellers();
            List<Artwork> artworks = new ArrayList();
            for (Seller seller : sellers) {
                List<Artwork> artworksBySeller = seller.getArtworks();
                for (Artwork artwork : artworksBySeller) {
                    artworks.add(artwork);
                }
            }
            return artworks;
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("CustomerId " + customerId + " does not exist!");
        }
    }

    @Override
    public List<SelfCareBox> viewAllSelfCareBoxByFollowedSellers(Long customerId) throws UserNotFoundException {
        try {
            Customer curCustomer = retrieveCustomerById(customerId);
            List<Seller> sellers = curCustomer.getSellers();
            List<SelfCareBox> boxes = new ArrayList();
            for (Seller seller : sellers) {
                List<SelfCareBox> boxBySeller = seller.getSelfCareBoxes();
                for (SelfCareBox selfCareBox : boxBySeller) {
                    boxes.add(selfCareBox);
                }
            }
            return boxes;
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("CustomerId " + customerId + " does not exist!");
        }
    }

    @Override
    //view all offences before reporting
    public List<Offences> retrieveAllOffences() {
        Query query = em.createQuery("SELECT o FROM Offences o");
        return query.getResultList();
    }

    @Override
    public Offences retrieveOffenceById(Long offenceId) throws OffenceNotFoundException {
        Offences offence = em.find(Offences.class, offenceId);

        if (offence != null) {
            return offence;
        } else {
            throw new OffenceNotFoundException("OffenceId " + offenceId + " does not exist!");
        }
    }

    @Override
    //this method just updates the relationship btwn customer and offences
    public void reportAUser(Long userId, Long offenceId) throws UnableToReportUserException, UserAlreadyBannedException {
        try {
            User userToReport = retrieveUserById(userId);
            if (userToReport instanceof Customer) {
                Customer customer = (Customer) userToReport;
                if (customer.isIsBanned()) {
                    throw new UserAlreadyBannedException("User " + userId + " is already Banned");
                }
            } else if (userToReport instanceof Seller) {
                Seller seller = (Seller) userToReport;
                if (seller.isIsBanned()) {
                    throw new UserAlreadyBannedException("User " + userId + " is already Banned");
                }
            }
            //add current offence to list of users' current offences
            Offences curOffence = retrieveOffenceById(offenceId);
            userToReport.getOffences().add(curOffence);
            curOffence.getUsers().add(userToReport);
            //check if the sum of offences' points >= 15, ban user
            List<Offences> offencesList = userToReport.getOffences();
            int sum = 0;
            for (Offences offence : offencesList) {
                sum = sum + offence.getNumOfPoints();
            }
            if (sum >= 15) {
                if (userToReport instanceof Customer) {
                    Customer customer = (Customer) userToReport;
                    customer.setIsBanned(true);
                } else if (userToReport instanceof Seller) {
                    Seller seller = (Seller) userToReport;
                    seller.setIsBanned(true);
                }
            }
        } catch (UserNotFoundException | OffenceNotFoundException ex) {
            throw new UnableToReportUserException("Unable to report customer!");
        }
    }

    public User retrieveUserById(Long userId) throws UserNotFoundException {
        User user = em.find(User.class, userId);

        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("UserId " + userId + " does not exist!");
        }
    }

    @Override
    public void deleteCustomer(Long customerId) throws UserNotFoundException {
        Customer customerToRemove;
        try {
            customerToRemove = retrieveCustomerById(customerId);
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("CustomerId " + customerId + " does not exist!");
        }
        //get list of reviews and orderHistory associated with customer
        //if list is not empty, we set customer isDeleted = true
        //else delete customer and un-set the tags and seller associations
        List<Review> reviewsAssociated = customerToRemove.getReviews();
        List<OrderHistory> ordersAssociated = customerToRemove.getOrders();
        List<Post> postsAssociated = customerToRemove.getPosts();
        List<Transaction> transactionAssociated = customerToRemove.getTransactions();
        if (reviewsAssociated.isEmpty() && ordersAssociated.isEmpty() && postsAssociated.isEmpty() && transactionAssociated.isEmpty()) {
            List<Tag> tagsAssociated = customerToRemove.getTags();
            for (Tag tag : tagsAssociated) {
                //unset tag association with customer
                tag.getCustomers().remove(customerToRemove);
            }
            List<Seller> sellersAssociated = customerToRemove.getSellers();
            for (Seller seller : sellersAssociated) {
                //unset seller association with customer
                seller.getCustomers().remove(customerToRemove);
            }
            em.remove(customerToRemove);
        } else {
            customerToRemove.setIsDeleted(true);
        }
        em.flush();
    }
}
