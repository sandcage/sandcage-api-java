
package com.sandcage.api.service;


/**
 *  Exception for values which exceed the permissible bounds.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/">SandCage API v0.2</a>
 */
public class OutOfBoundsException extends Exception {


    /**
     *  Creates an {@link OutOfBoundsException}.
     * 
     *  @param  fields  the field which is, or fields which are, out of bounds
     *  @param  values  the value which is, or values which are, out of bounds 
     */
    public OutOfBoundsException(String fields, String values) {
        super("The value(s) [ "+values+" ] provided for field(s) [ "+fields+" ] was/were out of bounds");
    }

    /**
     *  Creates an {@link OutOfBoundsException}.
     * 
     *  @param  fields  the field which is, or fields which are, out of bounds
     *  @param  values  the value which is, or values which are, out of bounds 
     *  @param  range   the permissible range
     */
    public OutOfBoundsException(String fields, String values, String range) {
        super("The value(s) [ "+values+" ] provided for field(s) [ "+fields+" ] was/were out of bounds [ "+range+" ]");
    }
}