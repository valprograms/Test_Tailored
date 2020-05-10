/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Rating;
import entity.Review;
import entity.SelfCareBox;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.RatingExistException;
import util.exception.RatingNotFoundException;
import util.exception.ReviewExistException;
import util.exception.ReviewNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author decimatum
 */
@Stateless
public class SelfCareBoxReviewsSessionBean implements SelfCareBoxReviewsSessionBeanLocal {

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;

    private ValidatorFactory validatorFactory;
    private Validator validator;

    public SelfCareBoxReviewsSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public Long createNewSelfCareBoxReview(Review newReview, Customer customer, SelfCareBox selfCareBox) throws ReviewExistException, UnknownPersistenceException, InputDataValidationException
        {
            try
            {
                Set<ConstraintViolation<Review>>constraintViolations = validator.validate(newReview);

                if(constraintViolations.isEmpty())
                {
                    em.persist(newReview);
                    newReview.setSelfCareBox(selfCareBox);
                    newReview.setCustomer(customer);
                    selfCareBox.getReviews().add(newReview);
                    customer.getReviews().add(newReview);
                    em.flush();

                    return newReview.getReviewId();
                }
                else
                {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForReview(constraintViolations));
                }            
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new ReviewExistException();
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

    @Override
    public Review retrieveSelfCareBoxReviewById(Long reviewId) throws ReviewNotFoundException
    {
        Review review = em.find(Review.class, reviewId);

        if(review != null)
        {
            review.getSelfCareBox();
            review.getCustomer();
            return review;
        }
        else
        {
            throw new ReviewNotFoundException("ReviewId " + reviewId + " does not exist!");
        }
    }

    @Override
    public void updateSelfCareBoxReview(Review review) throws InputDataValidationException, ReviewNotFoundException
    {
        Set<ConstraintViolation<Review>>constraintViolations = validator.validate(review);

        if(constraintViolations.isEmpty())
        {
            if(review.getReviewId()!= null)
            {
                Review reviewToUpdate = retrieveSelfCareBoxReviewById(review.getReviewId());
                reviewToUpdate.setComment(review.getComment());
            }
            else
            {
                throw new ReviewNotFoundException("Review ID not provided for review to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForReview(constraintViolations));
        }
    }

    @Override
    public void deleteSelfCareReviewById(Long reviewId) throws ReviewNotFoundException
    {
        try {
            Review reviewToRemove = retrieveSelfCareBoxReviewById(reviewId);

            Customer customer = reviewToRemove.getCustomer();
            SelfCareBox box = reviewToRemove.getSelfCareBox();

            customer.getReviews().remove(reviewToRemove);
            box.getReviews().remove(reviewToRemove);
            em.remove(reviewToRemove);

        } catch (ReviewNotFoundException ex) {
            throw new ReviewNotFoundException("Review ID " + reviewId + " does not exist!");
        }       
    }
    
    @Override
    public Long createNewSelfCareBoxRating(Rating newRating, SelfCareBox selfCareBox) throws RatingExistException, UnknownPersistenceException, InputDataValidationException
        {
            try
            {
                Set<ConstraintViolation<Rating>>constraintViolations = validator.validate(newRating);

                if(constraintViolations.isEmpty())
                {
                    em.persist(newRating);
                    newRating.setSelfCareBox(selfCareBox);
                    selfCareBox.getRatings().add(newRating);
                    em.flush();

                    return newRating.getRatingId();
                }
                else
                {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForRating(constraintViolations));
                }            
            }
            catch(PersistenceException ex)
            {
                if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new RatingExistException();
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
    
    @Override
    public Rating retrieveSelfCareBoxRatingById(Long ratingId) throws RatingNotFoundException
    {
        Rating rating = em.find(Rating.class, ratingId);

        if(rating != null)
        {
            rating.getSelfCareBox();
            return rating;
        }
        else
        {
            throw new RatingNotFoundException("RatingId " + ratingId + " does not exist!");
        }
    }
    
    @Override
    public void updateSelfCareBoxRating(Rating rating, boolean delete) throws InputDataValidationException, RatingNotFoundException
    {
        Set<ConstraintViolation<Rating>>constraintViolations = validator.validate(rating);

        if(constraintViolations.isEmpty())
        {
            if(rating.getRatingId()!= null)
            {
                Rating ratingToUpdate = retrieveSelfCareBoxRatingById(rating.getRatingId());
                
                if(delete){
                    SelfCareBox box = ratingToUpdate.getSelfCareBox();

                    box.getRatings().remove(ratingToUpdate);
                    em.remove(ratingToUpdate);
                }
                ratingToUpdate.setNumOfStars(rating.getNumOfStars());
            }
            else
            {
                throw new RatingNotFoundException("Rating ID not provided for rating to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForRating(constraintViolations));
        }
    }

    private String prepareInputDataValidationErrorsMessageForReview(Set<ConstraintViolation<Review>> constraintViolations) {
        String msg = "Input data validation error!:";

        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
    
     private String prepareInputDataValidationErrorsMessageForRating(Set<ConstraintViolation<Rating>> constraintViolations) {
        String msg = "Input data validation error!:";

        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
