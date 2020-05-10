/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author yiningxing
 */
public class CommentExistException extends Exception {

    /**
     * Creates a new instance of <code>CommentExistException</code> without
     * detail message.
     */
    public CommentExistException() {
    }

    /**
     * Constructs an instance of <code>CommentExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CommentExistException(String msg) {
        super(msg);
    }
}
