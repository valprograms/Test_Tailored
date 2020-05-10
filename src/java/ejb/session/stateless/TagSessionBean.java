/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Admin;
import entity.Artwork;
import entity.Customer;
import entity.Post;
import entity.SelfCareBox;
import entity.Tag;
import java.util.List;
import java.util.Set;
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
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author yiningxing
 */
@Stateless
public class TagSessionBean implements TagSessionBeanLocal {
    
    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;
    
    private ValidatorFactory validatorFactory;
    private Validator validator;
   
    public TagSessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewTag(Tag newTag) throws TagExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<Tag>>constraintViolations = validator.validate(newTag);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newTag);
                em.flush();

                return newTag.getTagId();
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
                    throw new TagExistException();
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
    
    public boolean tagExists(Tag tag){
        List<Tag> tags = retrieveAllTags();
        for(Tag t : tags){
            if(t.getTagName().equals(tag.getTagName())){
                return true;
            }
        }
        return false;        
    }
    
    @Override
    public List<Tag> retrieveAllTags()
    {
        Query query = em.createQuery("SELECT t FROM Tag t");
        
        return query.getResultList();
    }

    @Override
    public Tag retrieveTagByTagId(Long tagId) throws TagNotFoundException
    {
        Tag tag = em.find(Tag.class, tagId);
        
        if(tag != null)
        {
            tag.getPosts().size();
            tag.getArtworks().size();
            tag.getSelfCareBoxes().size();
            tag.getCustomers().size();
            return tag;
        }
        else
        {
            throw new TagNotFoundException("TagId " + tagId + " does not exist!");
        }
    }
    
    @Override
    public Tag retrieveTagByTagName(String tagName) throws TagNotFoundException
    {
        Query query = em.createQuery("SELECT t FROM Tag t WHERE t.tagName = :inTagName");
        query.setParameter("inTagName", tagName);
        
        try
        {
            return (Tag) query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new TagNotFoundException("TagName " + tagName + " does not exist!");
        }
    }
    
    //only update tagname when there is syntax error
    @Override
    public void updateTag(Tag updatedTag) throws InputDataValidationException, TagNotFoundException
    {
        Set<ConstraintViolation<Tag>>constraintViolations = validator.validate(updatedTag);
        
        if(constraintViolations.isEmpty())
        {
            if(updatedTag.getTagId()!= null)
            {
                Tag tagToUpdate = retrieveTagByTagId(updatedTag.getTagId());
                tagToUpdate.setTagName(updatedTag.getTagName());
            }
            else
            {
                throw new TagNotFoundException("Tag ID not provided for tagName to be updated");
            }
        }
        else
        {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    
    
    @Override
    public void deleteTag(Long tagId) throws TagNotFoundException
    {
        try {
            Tag tagToRemove = retrieveTagByTagId(tagId);
            
            List<Artwork> artworks = tagToRemove.getArtworks();
            List<SelfCareBox> boxes = tagToRemove.getSelfCareBoxes();
            List<Post> posts = tagToRemove.getPosts();
            List<Customer> customers = tagToRemove.getCustomers();
            
            for(Artwork a: artworks){
                a.getTags().remove(tagToRemove);
            }
            for(SelfCareBox b: boxes){
                b.getTags().remove(tagToRemove);
            }
            for(Post p: posts){
                p.getTags().remove(tagToRemove);
            }
            for(Customer c: customers){
                c.getTags().remove(tagToRemove);
            }
            em.remove(tagToRemove);
            
        } catch (TagNotFoundException ex) {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }       
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Tag>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
