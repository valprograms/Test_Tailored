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
public class CommentNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>CommentNotFoundException</code> without
     * detail message.
     */
    public CommentNotFoundException() {
    }

    /**
     * Constructs an instance of <code>CommentNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
