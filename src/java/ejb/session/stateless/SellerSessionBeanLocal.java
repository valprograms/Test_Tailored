/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Artwork;
import entity.ArtworkOrder;
import entity.SelfCareBox;
import entity.SelfCareBoxOrder;
import entity.Seller;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.OrderStatusEnum;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.OrderHistoryNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;
import util.exception.existingAssociationException;

/**
 *
 * @author decimatum
 */
@Local
public interface SellerSessionBeanLocal {

    public Long createNewSeller(Seller newSellerEntity) throws UserUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public Seller viewMySellerDetails(String username, String password) throws InvalidLoginCredentialException;

    public List<Seller> retrieveAllSellers();

    public Seller retrieveSellerById(Long sellerId) throws UserNotFoundException;

    public Seller retrieveSellerByUsername(String username) throws UserNotFoundException;
    
    public void deleteSeller(Long sellerId) throws UserNotFoundException;

    public List<Artwork> retrieveArtworkBySeller(Long sellerId);

    public List<SelfCareBox> retrieveSelfCareBoxBySeller(Long sellerId);

    public void updateMySellerDetails(Seller updatedSeller) throws InputDataValidationException, UserNotFoundException;

    public List<ArtworkOrder> retrieveArtworkOrder(Long sellerId);

    public List<SelfCareBoxOrder> retrieveSelfCareBoxOrder(Long sellerId);

    public void updateArtworkOrderStatus(ArtworkOrder artworkOrderToUpdate);

    public void updateSelfCareBoxOrderStatus(SelfCareBoxOrder selfCareBoxOrderToUpdate);
    
}
