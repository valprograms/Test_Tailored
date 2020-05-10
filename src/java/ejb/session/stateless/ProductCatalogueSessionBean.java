/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Artwork;
import entity.ArtworkOrder;
import entity.SelfCareBox;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import entity.ArtworkPrice;
import entity.Rating;
import entity.Review;
import entity.SelfCareBoxOrder;
import entity.SelfCareSubscriptionDiscount;
import entity.Seller;
import entity.Tag;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import javax.persistence.Query;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
@Stateless
public class ProductCatalogueSessionBean implements ProductCatalogueSessionBeanLocal {

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProductCatalogueSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<Artwork> retrieveAllArtwork() {
        Query query = em.createQuery("SELECT a FROM Artwork a");
        List<Artwork> artworks = query.getResultList();
        List<Artwork> artworksSoldByNotBannedUser = new ArrayList<>();
        for (Artwork artwork : artworks) {
            if (!artwork.getSeller().isIsBanned() && !artwork.getSeller().isIsDeleted()) {
                artwork.getTags().size();
                artwork.getArtworkPrices().size();
                artworksSoldByNotBannedUser.add(artwork);
            }
        }

        return artworksSoldByNotBannedUser;
    }

    @Override
    public List<SelfCareBox> retrieveAllSelfCareBox() {
        Query query = em.createQuery("SELECT s FROM SelfCareBox s");
        List<SelfCareBox> boxes = query.getResultList();
        List<SelfCareBox> scb = new ArrayList<>();
        for (SelfCareBox box : boxes) {
            if (!box.getSeller().isIsBanned() && !box.getSeller().isIsDeleted()) {
                box.getRatings().size();
                box.getReviews().size();
                box.getTags().size();
                scb.add(box);
            }
        }

        return scb;
    }

    @Override
    public List<Artwork> filterArtworksByTags(Long tagId) throws TagNotFoundException {
        List<Artwork> artworksSoldByNotBannedUser = new ArrayList<>();
        try {
            List<Artwork> filteredArtwork = new ArrayList<>();
            if (tagId == null) {
                return filteredArtwork;
            } else {
                filteredArtwork = tagSessionBeanLocal.retrieveTagByTagId(tagId).getArtworks();
                for (Artwork artwork : filteredArtwork) {
                    if (!artwork.getSeller().isIsBanned() && !artwork.getSeller().isIsDeleted()) {
                        artwork.getTags().size();
                        artwork.getArtworkPrices().size();
                        artworksSoldByNotBannedUser.add(artwork);
                    }
                }
            }
            return artworksSoldByNotBannedUser;
        } catch (TagNotFoundException ex) {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }

//        List<Artwork> artworks = new ArrayList<>();
//        
//        if(tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR")))
//        {
//            return artworks;
//        }
//        else
//        {
//            if(condition.equals("OR"))
//            {
//                Query query = em.createQuery("SELECT DISTINCT a FROM Artwork a, IN (a.tags) t WHERE t.tagId IN :inTagIds ORDER BY a.name ASC");
//                query.setParameter("inTagIds", tagIds);
//                artworks = query.getResultList();                                                          
//            }
//            else // AND
//            {
//                String selectClause = "SELECT s FROM Artwork a";
//                String whereClause = "";
//                Boolean firstTag = true;
//                Integer tagCount = 1;
//
//                for(Long tagId:tagIds)
//                {
//                    selectClause += ", IN (s.tags) t" + tagCount;
//
//                    if(firstTag)
//                    {
//                        whereClause = "WHERE t1.tagId = " + tagId;
//                        firstTag = false;
//                    }
//                    else
//                    {
//                        whereClause += " AND t" + tagCount + ".tagId = " + tagId; 
//                    }
//                    
//                    tagCount++;
//                }
//                
//                String jpql = selectClause + " " + whereClause + " ORDER BY s.name ASC";
//                Query query = em.createQuery(jpql);
//                artworks = query.getResultList();                                
//            }
//            
//            for(Artwork artwork:artworks)
//            {
//                artwork.getTags().size();
//            }
//            
//            Collections.sort(artworks, new Comparator<Artwork>()
//            {
//                public int compare(Artwork a1, Artwork a2) {
//                    return a1.getName().compareTo(a2.getName());
//                }
//            });
//            
//            return artworks;
//        }
    }

    @Override
    public List<SelfCareBox> filterSelfCareBoxesByTags(Long tagId) throws TagNotFoundException {
        try {
            List<SelfCareBox> selfCareBox = new ArrayList<>();
            if (tagId == null) {
                return selfCareBox;
            } else {
                selfCareBox = tagSessionBeanLocal.retrieveTagByTagId(tagId).getSelfCareBoxes();
                for (SelfCareBox selfCare : selfCareBox) {
                    selfCare.getTags().size();
                }
                return selfCareBox;
            }
        } catch (TagNotFoundException ex) {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }
//        List<SelfCareBox> boxes = new ArrayList<>();
//        
//        if(tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR")))
//        {
//            return boxes;
//        }
//        else
//        {
//            if(condition.equals("OR"))
//            {
//                Query query = em.createQuery("SELECT DISTINCT s FROM SelfCareBox s, IN (s.tags) t WHERE t.tagId IN :inTagIds ORDER BY s.name ASC");
//                query.setParameter("inTagIds", tagIds);
//                boxes = query.getResultList();                                                          
//            }
//            else // AND
//            {
//                String selectClause = "SELECT s FROM SelfCareBox s";
//                String whereClause = "";
//                Boolean firstTag = true;
//                Integer tagCount = 1;
//
//                for(Long tagId:tagIds)
//                {
//                    selectClause += ", IN (s.tags) t" + tagCount;
//
//                    if(firstTag)
//                    {
//                        whereClause = "WHERE t1.tagId = " + tagId;
//                        firstTag = false;
//                    }
//                    else
//                    {
//                        whereClause += " AND t" + tagCount + ".tagId = " + tagId; 
//                    }
//                    
//                    tagCount++;
//                }
//                
//                String jpql = selectClause + " " + whereClause + " ORDER BY s.name ASC";
//                Query query = em.createQuery(jpql);
//                boxes = query.getResultList();                                
//            }
//            
//            for(SelfCareBox box:boxes)
//            {
//                box.getRatings().size();
//                box.getReviews().size();
//                box.getTags().size();
//            }
//            
//            Collections.sort(boxes, new Comparator<SelfCareBox>()
//            {
//                public int compare(SelfCareBox s1, SelfCareBox s2) {
//                    return s1.getName().compareTo(s2.getName());
//                }
//            });
//            
//            return boxes;
//        }
    }

    @Override
    public Artwork createArtwork(Artwork artwork, List<Long> tagIdList, Seller seller) throws ArtworkNotCreatedException, TagNotFoundException {
        Set<ConstraintViolation<Artwork>> constraintViolations = validator.validate(artwork);
        if (constraintViolations.isEmpty()) {
            em.persist(artwork);
            Seller managedSeller = em.find(Seller.class, seller.getUserId());
            artwork.setSeller(managedSeller);
            managedSeller.addArtwork(artwork);
            em.flush();
            //Unable to add tags to artworks. Need others help (fixed now)
            if (tagIdList != null && (!tagIdList.isEmpty())) {
                for (Long tagId : tagIdList) {
                    Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                    artwork.getTags().add(tag);
                    tag.getArtworks().add(artwork);
                }
            }
            return artwork;
        } else {
            throw new ArtworkNotCreatedException("Artwork's name or description is invalid.");
        }
    }

    @Override
    public ArtworkPrice createArtworkPrice(ArtworkPrice artworkPrice, Artwork artwork) throws ArtworkNotCreatedException {
        Set<ConstraintViolation<ArtworkPrice>> constraintViolations = validator.validate(artworkPrice);
        if (constraintViolations.isEmpty()) {
            em.persist(artworkPrice);
            Artwork managedArtwork = em.find(Artwork.class, artwork.getArtworkId());
            artworkPrice.setArtwork(managedArtwork);
            managedArtwork.getArtworkPrices().add(artworkPrice);
            return artworkPrice;
        } else {
            throw new ArtworkNotCreatedException("Artwork's price is not valid");
        }
    }

    @Override
    public ArtworkPrice retrieveArtworkPriceById(Long artworkPriceId) throws ArtworkPriceNotFoundException {
        ArtworkPrice artworkPrice = em.find(ArtworkPrice.class, artworkPriceId);

        if (artworkPrice != null) {
            return artworkPrice;
        } else {
            throw new ArtworkPriceNotFoundException("Artwork Price ID " + artworkPriceId + " does not exist!");
        }
    }

    @Override
    public void updateArtworkPrice(ArtworkPrice artworkPrice) throws ArtworkPriceNotFoundException, InputDataValidationException {

        if (artworkPrice != null) {
            Set<ConstraintViolation<ArtworkPrice>> constraintViolations = validator.validate(artworkPrice);

            if (constraintViolations.isEmpty()) {
                ArtworkPrice artworkPriceToUpdate = retrieveArtworkPriceById(artworkPrice.getArtworkPriceId());
                artworkPriceToUpdate.setPrice(artworkPrice.getPrice());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForArtworkPrice(constraintViolations));
            }
        } else {
            throw new ArtworkPriceNotFoundException("Artwork ID not provided for artwork to be updated");
        }
    }

    @Override
    public void updateArtwork(Artwork artwork, List<Long> tagIds, List<ArtworkPrice> prices) throws ArtworkNotFoundException, TagNotFoundException, InputDataValidationException, ArtworkPriceNotFoundException {
        if (artwork != null && artwork.getArtworkId() != null) {
            Set<ConstraintViolation<Artwork>> constraintViolations = validator.validate(artwork);

            if (constraintViolations.isEmpty()) {
                Artwork artworkToUpdate = retrieveArtworkById(artwork.getArtworkId());

                if (tagIds != null) {
                    for (Tag tag : artworkToUpdate.getTags()) {
                        tag = tagSessionBeanLocal.retrieveTagByTagId(tag.getTagId());
                        tag.getArtworks().remove(artworkToUpdate);
                    }

                    artworkToUpdate.getTags().clear();

                    for (Long tagId : tagIds) {
                        Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                        artworkToUpdate.addTag(tag);
                        tag.getArtworks().add(artworkToUpdate);
                    }
                }

                if (prices != null) {
                    for (ArtworkPrice currPrice : prices) {
                        try {
                            updateArtworkPrice(currPrice);
                        } catch (ArtworkPriceNotFoundException ex) {
                            throw new ArtworkPriceNotFoundException("Could not find artwork price to update");
                        }
                    }
                }

                artworkToUpdate.setName(artwork.getName());
                artworkToUpdate.setImage(artwork.getImage());
                artworkToUpdate.setDescription(artwork.getDescription());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForArtwork(constraintViolations));
            }
        } else {
            throw new ArtworkNotFoundException("Artwork ID not provided for artwork to be updated");
        }
    }

    @Override
    public void deleteArtwork(Long artworkId) throws ArtworkNotFoundException, DeleteArtworkException {
        Artwork artworkToRemove = retrieveArtworkById(artworkId);

        List<ArtworkOrder> artworkOrders = artworkToRemove.getArtworkOrders();

        if (artworkOrders.isEmpty()) {
            List<ArtworkPrice> prices = artworkToRemove.getArtworkPrices();

            for (ArtworkPrice price : prices) {
                em.remove(price);
            }
            artworkToRemove.getArtworkPrices().clear();
            em.remove(artworkToRemove);
        } else {
            throw new DeleteArtworkException("Artwork ID " + artworkId + " is associated with existing order and cannot be deleted!");
        }
    }

    @Override
    public Artwork retrieveArtworkById(Long artworkId) throws ArtworkNotFoundException {
        Query query = em.createQuery("SELECT a FROM Artwork a WHERE a.artworkId =:ArtworkId");
        query.setParameter("ArtworkId", artworkId);
        Artwork artwork = (Artwork) query.getSingleResult();
        if (artwork != null) {
            return artwork;
        } else {
            throw new ArtworkNotFoundException("Artwork Id: " + artworkId + " does not exist.");
        }
    }

    @Override
    public SelfCareBox createSelfCareBox(SelfCareBox newSelfCareBox, List<Long> tagIds, Seller seller) throws SelfCareBoxExistException, TagNotFoundException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<SelfCareBox>> constraintViolations = validator.validate(newSelfCareBox);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newSelfCareBox);
                Seller managedSeller = em.find(Seller.class, seller.getUserId());
                newSelfCareBox.setSeller(managedSeller);
                managedSeller.addSelfCareBox(newSelfCareBox);

                if (tagIds != null && (!tagIds.isEmpty())) {
                    for (Long tagId : tagIds) {
                        Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                        newSelfCareBox.addTag(tag);
                        tag.getSelfCareBoxes().add(newSelfCareBox);
                    }
                }

                em.flush();

                return newSelfCareBox;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new SelfCareBoxExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (TagNotFoundException ex) {
                throw new TagNotFoundException("An error has occurred while creating the new SelfCareBox: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForSelfCareBox(constraintViolations));
        }
    }

    @Override
    public SelfCareSubscriptionDiscount createSelfCareSubscriptionDiscount(SelfCareSubscriptionDiscount discount, SelfCareBox selfCareBox) throws SelfCareSubscriptionDiscountExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<SelfCareSubscriptionDiscount>> constraintViolations = validator.validate(discount);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(discount);
                SelfCareBox managedSelfCareBox = em.find(SelfCareBox.class, selfCareBox.getSelfCareBoxId());
                discount.setSelfCareBox(managedSelfCareBox);
                managedSelfCareBox.addSelfCareSubscriptionDiscount(discount);

                em.flush();

                return discount;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new SelfCareSubscriptionDiscountExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForSubscriptionDiscount(constraintViolations));
        }
    }

    @Override
    public SelfCareBox retrieveSelfCareBoxById(Long selfCareBoxId) throws SelfCareBoxNotFoundException {

        SelfCareBox selfCareBox = em.find(SelfCareBox.class, selfCareBoxId);

        if (selfCareBox != null) {
            selfCareBox.getSelfCareSubscriptionDiscounts();
            selfCareBox.getRatings();
            selfCareBox.getReviews();
            selfCareBox.getTags();

            return selfCareBox;
        } else {
            throw new SelfCareBoxNotFoundException("Self Care Box ID " + selfCareBoxId + " does not exist!");
        }
    }

    @Override
    public void updateSelfCareBox(SelfCareBox selfCareBox, List<Long> tagIds, List<SelfCareSubscriptionDiscount> discounts) throws SelfCareBoxNotFoundException, TagNotFoundException, InputDataValidationException, DiscountNotFoundException {
        if (selfCareBox != null && selfCareBox.getSelfCareBoxId() != null) {
            Set<ConstraintViolation<SelfCareBox>> constraintViolations = validator.validate(selfCareBox);

            if (constraintViolations.isEmpty()) {
                SelfCareBox selfCareBoxToUpdate = retrieveSelfCareBoxById(selfCareBox.getSelfCareBoxId());

                if (tagIds != null) {
                    for (Tag tag : selfCareBoxToUpdate.getTags()) {
                        tag = tagSessionBeanLocal.retrieveTagByTagId(tag.getTagId());
                        tag.getSelfCareBoxes().remove(selfCareBoxToUpdate);
                    }

                    selfCareBoxToUpdate.getTags().clear();

                    for (Long tagId : tagIds) {
                        Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                        selfCareBoxToUpdate.addTag(tag);
                        tag.getSelfCareBoxes().add(selfCareBoxToUpdate);
                    }
                }

                if (discounts != null) {
                    for (SelfCareSubscriptionDiscount currDiscount : discounts) {
                        try {
                            updateSubscriptionDiscount(currDiscount);
                        } catch (DiscountNotFoundException ex) {
                            throw new DiscountNotFoundException("Could not find subscription discount to update");
                        }
                    }
                }

                selfCareBoxToUpdate.setName(selfCareBox.getName());
                selfCareBoxToUpdate.setImage(selfCareBox.getImage());
                selfCareBoxToUpdate.setDescription(selfCareBox.getDescription());
                selfCareBoxToUpdate.setPricePerMonth(selfCareBox.getPricePerMonth());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForSelfCareBox(constraintViolations));
            }
        } else {
            throw new SelfCareBoxNotFoundException("Self Care Box ID not provided for Self Care Box to be updated");
        }
    }

    @Override
    public void updateSubscriptionDiscount(SelfCareSubscriptionDiscount discountPercentage) throws InputDataValidationException, DiscountNotFoundException {

        if (discountPercentage != null) {
            Set<ConstraintViolation<SelfCareSubscriptionDiscount>> constraintViolations = validator.validate(discountPercentage);

            if (constraintViolations.isEmpty()) {
                SelfCareSubscriptionDiscount discountPercentageToUpdate = retrieveSubscriptionDiscountById(discountPercentage.getDiscountId());
                discountPercentageToUpdate.setDiscountPercentage(discountPercentage.getDiscountPercentage());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForDiscount(constraintViolations));
            }
        } else {
            throw new DiscountNotFoundException("Subscription Discount ID not provided for self care box to be updated");
        }
    }

    @Override
    public SelfCareSubscriptionDiscount retrieveSubscriptionDiscountById(Long discountId) throws DiscountNotFoundException {
        SelfCareSubscriptionDiscount discount = em.find(SelfCareSubscriptionDiscount.class, discountId);

        if (discount != null) {
            return discount;
        } else {
            throw new DiscountNotFoundException("Discount ID " + discountId + " does not exist!");
        }
    }

    @Override
    public void deleteSelfCareBox(Long selfCareBoxId) throws SelfCareBoxNotFoundException, DeleteSelfCareBoxException {
        SelfCareBox selfCareBoxToRemove = retrieveSelfCareBoxById(selfCareBoxId);

        List<SelfCareBoxOrder> selfCareBoxOrders = selfCareBoxToRemove.getSelfCareBoxOrders();
        List<Review> selfCareBoxReviews = selfCareBoxToRemove.getReviews();
        List<Rating> selfCareBoxRatings = selfCareBoxToRemove.getRatings();

        if (selfCareBoxOrders.isEmpty() && selfCareBoxReviews.isEmpty() && selfCareBoxRatings.isEmpty()) {
            List<SelfCareSubscriptionDiscount> discounts = selfCareBoxToRemove.getSelfCareSubscriptionDiscounts();

            for (SelfCareSubscriptionDiscount discount : discounts) {
                em.remove(discount);
            }
            selfCareBoxToRemove.getSelfCareSubscriptionDiscounts().clear();
            em.remove(selfCareBoxToRemove);
        } else {
            throw new DeleteSelfCareBoxException("Self Care Box ID " + selfCareBoxId + " is associated with existing order/review/rating and cannot be deleted!");
        }
    }

    private String prepareInputDataValidationErrorsMessageForSelfCareBox(Set<ConstraintViolation<SelfCareBox>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareInputDataValidationErrorsMessageForArtwork(Set<ConstraintViolation<Artwork>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareInputDataValidationErrorsMessageForSubscriptionDiscount(Set<ConstraintViolation<SelfCareSubscriptionDiscount>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareInputDataValidationErrorsMessageForArtworkPrice(Set<ConstraintViolation<ArtworkPrice>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareInputDataValidationErrorsMessageForDiscount(Set<ConstraintViolation<SelfCareSubscriptionDiscount>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
