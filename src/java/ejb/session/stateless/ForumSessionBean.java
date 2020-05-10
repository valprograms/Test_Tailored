/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Comment;
import entity.Post;
import entity.Tag;
import entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
@Stateless
public class ForumSessionBean implements ForumSessionBeanLocal {

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB(name = "BackEndUserSessionBeanLocal")
    private BackEndUserSessionBeanLocal backEndUserSessionBeanLocal;

    //user, tag, comment
    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;

    private ValidatorFactory validatorFactory;
    private Validator validator;

    public ForumSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewPost(Post newPost, Long userId, List<Long> tagIds) throws PostExistException, UserNotFoundException, TagNotFoundException, UnknownPersistenceException, InputDataValidationException {
        try {
            User user = backEndUserSessionBeanLocal.retrieveUserByUserId(userId);
            List<Tag> tags = new ArrayList<>();
            for (Long tagId : tagIds) {
                Tag tag = tagSessionBeanLocal.retrieveTagByTagId(tagId);
                tags.add(tag);
                System.out.println(tags.size());
            }
            Set<ConstraintViolation<Post>> constraintViolations = validator.validate(newPost);
            if (constraintViolations.isEmpty()) {
                em.persist(newPost);
                user.getPosts().add(newPost);
                newPost.setUser(user);
                em.flush();
                for (int i = 0; i < tags.size(); i++) {
                    tags.get(i).getPosts().add(newPost);
                    newPost.getTags().add(tags.get(i));
                }
                em.flush();
                return newPost.getPostId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForPost(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new PostExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("Unable to find the user as the user does not exist");
        } catch (TagNotFoundException ex) {
            throw new TagNotFoundException("Unable to find the tag as the tag does not exist");
        }
    }

    @Override
    public List<Post> retrieveAllMyPosts(Long userId) {
        Query query = em.createQuery("SELECT p FROM Post p WHERE p.user.userId = :inUserId");
        query.setParameter("inUserId", userId);
        List<Post> posts = query.getResultList();

        for (Post post : posts) {
            post.getTags().size();
            post.getComments().size();
        }
        return posts;
    }

    @Override
    public List<Post> retrieveAllPosts() {
        Query query = em.createQuery("SELECT p FROM Post p");

        List<Post> posts = query.getResultList();

        if (posts.size() > 0) {
            for (Post post : posts) {
                post.getTags().size();
                post.getComments().size();
            }
        }
        return posts;
    }

    @Override
    public List<Post> filterPostsByTags(Long tagId) throws TagNotFoundException {
        try {
            List<Post> posts = new ArrayList<>();
            if (tagId == null) {
                return posts;
            } else {
                posts = tagSessionBeanLocal.retrieveTagByTagId(tagId).getPosts();
                for (Post post : posts) {
                    post.getTags().size();
                    post.getComments().size();
                }
                return posts;
            }
        } catch (TagNotFoundException ex) {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }
    }

    @Override
    public Post retrieveSinglePost(Long postId) throws PostNotFoundException {
        Post post = em.find(Post.class, postId);

        if (post != null) {
            post.getTags().size();
            post.getComments().size();
            return post;
        } else {
            throw new PostNotFoundException("PostId: " + postId + " does not exist!");
        }
    }

    @Override
    public void updateMyPost(Post post) throws InputDataValidationException, PostNotFoundException {
        Set<ConstraintViolation<Post>> constraintViolations = validator.validate(post);

        if (constraintViolations.isEmpty()) {
            if (post.getPostId() != null) {
                Post postToUpdate = retrieveSinglePost(post.getPostId());
                postToUpdate.setContent(post.getContent());
                postToUpdate.setImage(post.getImage());
                postToUpdate.setTopic(post.getTopic());
            } else {
                throw new PostNotFoundException("Post ID not provided for post to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForPost(constraintViolations));
        }
    }

    @Override
    public void deletePostById(Long postId) throws PostNotFoundException {
        try {
            //post associated with tag, comment and user
            Post postToRemove = retrieveSinglePost(postId);

            User user = postToRemove.getUser();
            user.getPosts().remove(postToRemove);
            //postToRemove.setUser(null);

            List<Tag> tags = postToRemove.getTags();
            for (int i = 0; i < tags.size(); i++) {
                tags.get(i).getPosts().remove(postToRemove);
            }

//            List<Comment> comments = postToRemove.getComments();
//            for (int i = 0; i < comments.size(); i++) {
//                comments.get(i).getUser().getComments().remove(comments.get(i));
//                em.remove(comments.get(i));
//            }
            em.remove(postToRemove);

        } catch (PostNotFoundException ex) {
            throw new PostNotFoundException("Post ID " + postId + " does not exist!");
        }
    }

    @Override
    public Long createNewComment(Comment newComment, Long userId, Long postId) throws CommentExistException, UnknownPersistenceException, InputDataValidationException, PostNotFoundException, UserNotFoundException {
        //comment asscociated with user and post
        try {
            User user = backEndUserSessionBeanLocal.retrieveUserByUserId(userId);
            Post post = retrieveSinglePost(postId);
            Set<ConstraintViolation<Comment>> constraintViolations = validator.validate(newComment);
            if (constraintViolations.isEmpty()) {

                user.getComments().add(newComment);
                newComment.setUser(user);
                post.getComments().add(newComment);
                newComment.setPost(post);
                em.persist(newComment);
                em.flush();
                return newComment.getCommentId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForComment(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CommentExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("Unable to find the user as the user does not exist");
        } catch (PostNotFoundException ex) {
            throw new PostNotFoundException("Unable to find the post as the post does not exist");
        }
    }

    @Override
    public List<Comment> retrieveAllMyComments(Long userId) {
        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.user.userId = :inUserId");
        query.setParameter("inUserId", userId);
        List<Comment> comments = query.getResultList();
        return comments;
    }

    @Override
    public Comment retrieveSingleComment(Long commentId) throws CommentNotFoundException {
        Comment comment = em.find(Comment.class, commentId);

        if (comment != null) {
            return comment;
        } else {
            throw new CommentNotFoundException("CommentId: " + commentId + " does not exist!");
        }
    }

    @Override
    public void updateComment(Comment comment) throws InputDataValidationException, CommentNotFoundException {
        Set<ConstraintViolation<Comment>> constraintViolations = validator.validate(comment);

        if (constraintViolations.isEmpty()) {
            if (comment.getCommentId() != null) {
                Comment commentToUpdate = retrieveSingleComment(comment.getCommentId());
                commentToUpdate.setContent(comment.getContent());
            } else {
                throw new CommentNotFoundException("Comment ID not provided for post to be updated");
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessageForComment(constraintViolations));
        }
    }

    @Override
    public void deleteCommentById(Long commentId) throws CommentNotFoundException {
        try {
            //comment with post and user
            Comment commentToRemove = retrieveSingleComment(commentId);

            User user = commentToRemove.getUser();
            Post post = commentToRemove.getPost();
            user.getComments().remove(commentToRemove);
            post.getComments().remove(commentToRemove);
            em.remove(commentToRemove);

        } catch (CommentNotFoundException ex) {
            throw new CommentNotFoundException("Comment ID " + commentId + " does not exist!");
        }
    }

    @Override
    public List<Comment> retrieveAllComment() {
        Query query = em.createQuery("SELECT c FROM Comment c");

        List<Comment> comments = query.getResultList();

        return comments;
    }

    private String prepareInputDataValidationErrorsMessageForPost(Set<ConstraintViolation<Post>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    private String prepareInputDataValidationErrorsMessageForComment(Set<ConstraintViolation<Comment>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
