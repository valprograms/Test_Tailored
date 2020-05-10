/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Artwork;
import entity.SelfCareBox;
import entity.ArtworkPrice;
import entity.SelfCareSubscriptionDiscount;
import entity.Seller;
import java.util.List;
import javax.ejb.Local;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import util.exception.ArtworkNotCreatedException;
import util.exception.ArtworkNotFoundException;
import util.exception.ArtworkPriceNotFoundException;
import util.exception.DeleteArtworkException;
import util.exception.DeleteSelfCareBoxException;
import util.exception.DiscountNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.SelfCareBoxExistException;
import util.exception.SelfCareBoxNotFoundException;
import util.exception.SelfCareSubscriptionDiscountExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author decimatum
 */
@Local
public interface ProductCatalogueSessionBeanLocal {


    public List<Artwork> retrieveAllArtwork();

    public List<SelfCareBox> retrieveAllSelfCareBox();
    
    public List<Artwork> filterArtworksByTags(Long tagId) throws TagNotFoundException;

    public List<SelfCareBox> filterSelfCareBoxesByTags(Long tagId) throws TagNotFoundException;

    public ArtworkPrice createArtworkPrice(ArtworkPrice artworkPrice, Artwork artwork) throws ArtworkNotCreatedException;


    public Artwork createArtwork(Artwork artwork, List<Long> tagIdList, Seller seller) throws ArtworkNotCreatedException, TagNotFoundException;

    public Artwork retrieveArtworkById(Long artworkId) throws ArtworkNotFoundException;

    public SelfCareBox createSelfCareBox(SelfCareBox newSelfCareBox, List<Long> tagIds, Seller seller) throws SelfCareBoxExistException, TagNotFoundException, UnknownPersistenceException, InputDataValidationException;

    public SelfCareBox retrieveSelfCareBoxById(Long selfCareBoxId) throws SelfCareBoxNotFoundException;

    public void deleteSelfCareBox(Long selfCareBoxId) throws SelfCareBoxNotFoundException, DeleteSelfCareBoxException;

    public SelfCareSubscriptionDiscount createSelfCareSubscriptionDiscount(SelfCareSubscriptionDiscount discount, SelfCareBox selfCareBox) throws SelfCareSubscriptionDiscountExistException, UnknownPersistenceException, InputDataValidationException;

    public void deleteArtwork(Long artworkId) throws ArtworkNotFoundException, DeleteArtworkException;

    public ArtworkPrice retrieveArtworkPriceById(Long artworkPriceId) throws ArtworkPriceNotFoundException;
   
    public void updateArtworkPrice(ArtworkPrice artworkPrice) throws ArtworkPriceNotFoundException, InputDataValidationException;

    public void updateArtwork(Artwork artwork, List<Long> tagIds, List<ArtworkPrice> prices) throws ArtworkNotFoundException, TagNotFoundException, InputDataValidationException, ArtworkPriceNotFoundException;

    public void updateSelfCareBox(SelfCareBox selfCareBox, List<Long> tagIds, List<SelfCareSubscriptionDiscount> discounts) throws SelfCareBoxNotFoundException, TagNotFoundException, InputDataValidationException, DiscountNotFoundException;

    public void updateSubscriptionDiscount(SelfCareSubscriptionDiscount discountPercentage) throws InputDataValidationException, DiscountNotFoundException;

    public SelfCareSubscriptionDiscount retrieveSubscriptionDiscountById(Long discountId) throws DiscountNotFoundException;
}
