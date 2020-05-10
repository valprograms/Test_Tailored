/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Artwork;
import entity.ArtworkOrder;
import entity.Customer;
import entity.OrderHistory;
import entity.SelfCareBox;
import entity.SelfCareBoxOrder;
import entity.Seller;
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
import util.exception.OrderHistoryNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;
import util.exception.existingAssociationException;
import util.security.CryptographicHelper;

/**
 *
 * @author decimatum
 */
@Stateless
public class SellerSessionBean implements SellerSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;
    
    private ValidatorFactory validatorFactory;
    private Validator validator;
    
    public SellerSessionBean(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewSeller(Seller newSellerEntity) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<Seller>>constraintViolations = validator.validate(newSellerEntity);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newSellerEntity);
                em.flush();

                return newSellerEntity.getUserId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new UserUsernameExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Seller>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    @Override
    public Seller viewMySellerDetails(String username, String password) throws InvalidLoginCredentialException{
        try{
            Seller seller = retrieveSellerByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + seller.getSalt()));
           
            if(seller.getPassword().equals(passwordHash)){
                return seller;
            } else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }catch(UserNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public void updateMySellerDetails(Seller updatedSeller) throws InputDataValidationException, UserNotFoundException {
        
        Seller currentSeller = retrieveSellerById(updatedSeller.getUserId());
        updatedSeller.setUsername(currentSeller.getUsername());
        updatedSeller.setPassword(currentSeller.getPassword());
        
        Set<ConstraintViolation<Seller>>constraintViolations = validator.validate(updatedSeller);
        
        if(constraintViolations.isEmpty()){
            if (updatedSeller.getUserId() != null ) {
                Seller sellerToUpdate = retrieveSellerById(updatedSeller.getUserId());
                sellerToUpdate.setEmail(updatedSeller.getEmail());
                sellerToUpdate.setFirstName(updatedSeller.getFirstName());
                sellerToUpdate.setLastName(updatedSeller.getLastName());
                sellerToUpdate.setIsVerified(updatedSeller.isIsVerified());
                // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
            } else {
                throw new UserNotFoundException("User ID not provided for seller to be updated");
            }
        } else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        } 
    }
    
    @Override
    public void deleteSeller(Long sellerId) throws UserNotFoundException {
        Seller sellerToRemove;
        try {
            sellerToRemove = retrieveSellerById(sellerId);
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("SellerId " + sellerId + " does not exist!");
        }
        List<Artwork> artworksAssociated = sellerToRemove.getArtworks();
        List<SelfCareBox> selfCareAssociated = sellerToRemove.getSelfCareBoxes();
        List<Customer> customerAssociated = sellerToRemove.getCustomers();
        if(artworksAssociated.isEmpty() && selfCareAssociated.isEmpty() && customerAssociated.isEmpty()){
            em.remove(sellerToRemove);
        } else {
            sellerToRemove.setIsDeleted(true);
        }
        em.flush();
    }
    
    @Override
    //this returns the seller even if it is deleted
    public Seller retrieveSellerById(Long userId) throws UserNotFoundException {
        
        Seller seller = em.find(Seller.class, userId);
        
        if(seller != null)
        {
            return seller;
        }
        else
        {
            throw new UserNotFoundException("SellerId " + userId + " does not exist!");
        }
    }
    
    @Override
    public Seller retrieveSellerByUsername(String username) throws UserNotFoundException{
        Query query = em.createQuery("SELECT s FROM Seller s WHERE s.username =:inUsername AND s.isDeleted = FALSE");
        query.setParameter("inUsername", username);
        
        try{
            return (Seller)query.getSingleResult();
        }catch(NoResultException | NonUniqueResultException ex){
            throw new UserNotFoundException("User with username " + username + " does not exist!");
        }
    }
    
    @Override
    public List<Seller> retrieveAllSellers(){
        Query query = em.createQuery("SELECT s FROM Seller s WHERE s.isDeleted = FALSE");
        return query.getResultList();
    }
    
    @Override
    public List<Artwork> retrieveArtworkBySeller(Long sellerId){
        Query query = em.createQuery("SELECT a FROM Artwork a WHERE a.seller.userId =:userId");
        query.setParameter("userId", sellerId);
        
        List<Artwork> artworks = query.getResultList();
        
        for(Artwork artwork: artworks) {
            artwork.getTags().size();
            artwork.getArtworkPrices().size();
        }
        
        return artworks;
    }
    
    @Override
    public List<SelfCareBox> retrieveSelfCareBoxBySeller(Long sellerId){
        Query query = em.createQuery("SELECT s FROM SelfCareBox s WHERE s.seller.userId =:userId");
        query.setParameter("userId", sellerId);
        
        List<SelfCareBox> selfcareboxes = query.getResultList();
        
        for(SelfCareBox box: selfcareboxes) {
            box.getTags().size();
            box.getSelfCareSubscriptionDiscounts().size();
        }
        
        return selfcareboxes;
    }
    
    @Override
    public List<ArtworkOrder> retrieveArtworkOrder(Long sellerId) {
        //retrieve all artwork orders where artwork is by seller;
        Query query = em.createQuery("SELECT DISTINCT ao FROM ArtworkOrder ao JOIN Artwork a WHERE a.seller.userId =:userId");
        query.setParameter("userId", sellerId);
        
        return query.getResultList();
    }
    
    @Override
    public List<SelfCareBoxOrder> retrieveSelfCareBoxOrder(Long sellerId) {
        //retrieve all selfcare box orders where selfcarebox is by seller;
        Query query = em.createQuery("SELECT DISTINCT so FROM SelfCareBoxOrder so JOIN SelfCareBox s WHERE s.seller.userId =:userId");
        query.setParameter("userId", sellerId);
        
        return query.getResultList();
    }

    @Override
    public void updateArtworkOrderStatus(ArtworkOrder artworkOrderToUpdate) {
        ArtworkOrder updatedOrder = em.find(ArtworkOrder.class, artworkOrderToUpdate.getOrderId());
        updatedOrder.setOrderStatusEnum(artworkOrderToUpdate.getOrderStatusEnum());
    }
    
    @Override
    public void updateSelfCareBoxOrderStatus(SelfCareBoxOrder selfCareBoxOrderToUpdate) {
        SelfCareBoxOrder updatedOrder = em.find(SelfCareBoxOrder.class, selfCareBoxOrderToUpdate.getOrderId());
        updatedOrder.setOrderStatusEnum(selfCareBoxOrderToUpdate.getOrderStatusEnum());
    }
}
