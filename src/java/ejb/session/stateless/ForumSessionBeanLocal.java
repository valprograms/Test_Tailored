/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Comment;
import entity.Post;
import java.util.List;
import javax.ejb.Local;
import util.exception.CommentExistException;
import util.exception.CommentNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PostExistException;
import util.exception.PostNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UserNotFoundException;

/**
 *
 * @author decimatum
 */
@Local
public interface ForumSessionBeanLocal {
  
    public Long createNewPost(Post newPost, Long userId, List<Long> tagIds) throws PostExistException, UserNotFoundException, TagNotFoundException, UnknownPersistenceException, InputDataValidationException;
    public List<Post> retrieveAllMyPosts(Long userId);
    public Post retrieveSinglePost(Long postId) throws PostNotFoundException;
    public List<Post> retrieveAllPosts();
    public void updateMyPost(Post post) throws InputDataValidationException, PostNotFoundException;
    public void deletePostById(Long postId) throws PostNotFoundException;
    public List<Post> filterPostsByTags(Long tagId) throws TagNotFoundException;
    public Long createNewComment(Comment newComment, Long userId, Long postId) throws CommentExistException, UnknownPersistenceException, InputDataValidationException, PostNotFoundException, UserNotFoundException;
    public List<Comment> retrieveAllMyComments(Long userId);
    public Comment retrieveSingleComment(Long commentId) throws CommentNotFoundException;
    public void updateComment(Comment comment) throws InputDataValidationException, CommentNotFoundException;
    public void deleteCommentById(Long commentId) throws CommentNotFoundException;
    public List<Comment> retrieveAllComment();
}
