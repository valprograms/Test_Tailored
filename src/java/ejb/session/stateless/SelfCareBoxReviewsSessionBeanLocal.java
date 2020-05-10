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
import javax.ejb.Local;
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
@Local
public interface SelfCareBoxReviewsSessionBeanLocal {

    public Long createNewSelfCareBoxReview(Review newReview, Customer customer, SelfCareBox selfCareBox) throws ReviewExistException, UnknownPersistenceException, InputDataValidationException;

    public Review retrieveSelfCareBoxReviewById(Long reviewId) throws ReviewNotFoundException;

    public void updateSelfCareBoxReview(Review review) throws InputDataValidationException, ReviewNotFoundException;

    public void deleteSelfCareReviewById(Long reviewId) throws ReviewNotFoundException;

    public Long createNewSelfCareBoxRating(Rating newRating, SelfCareBox selfCareBox) throws RatingExistException, UnknownPersistenceException, InputDataValidationException;

    public Rating retrieveSelfCareBoxRatingById(Long ratingId) throws RatingNotFoundException;

    public void updateSelfCareBoxRating(Rating rating, boolean delete) throws InputDataValidationException, RatingNotFoundException;
    
}
