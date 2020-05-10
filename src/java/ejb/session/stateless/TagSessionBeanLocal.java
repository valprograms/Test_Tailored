/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tag;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author yiningxing
 */
@Local
public interface TagSessionBeanLocal {
    
    public Long createNewTag(Tag newTag) throws TagExistException, UnknownPersistenceException, InputDataValidationException;
    public Tag retrieveTagByTagId(Long tagId) throws TagNotFoundException;
    public Tag retrieveTagByTagName(String tagName) throws TagNotFoundException;
    public void updateTag(Tag updatedTag) throws InputDataValidationException, TagNotFoundException;
    public void deleteTag(Long tagId) throws TagNotFoundException;
    public boolean tagExists(Tag tag);

    public List<Tag> retrieveAllTags();
}
