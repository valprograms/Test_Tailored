/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Kaijing
 */
public class UnableToReportUserException extends Exception {

    /**
     * Creates a new instance of <code>UnableToReportUserException</code>
     * without detail message.
     */
    public UnableToReportUserException() {
    }

    /**
     * Constructs an instance of <code>UnableToReportUserException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnableToReportUserException(String msg) {
        super(msg);
    }
}
