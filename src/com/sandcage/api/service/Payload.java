
package com.sandcage.api.service;


/**
 *  Represents a SandCage API request payload.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/">SandCage API v0.2</a>
 */
public abstract class Payload {

    // MANDATORY: ALL SERVICES REQUIRE A CLIENT KEY
    private String key;


    /**
     *  All services implement specializations of this {@link Payload} class.
     * 
     *  @param  key     the user's unique key
     * 
     *  @throws NullPointerException    if the key is null
     */
    public Payload(String key) 
            throws NullPointerException {
        if(key==null)
            throw new NullPointerException("Mandatory field [ key ] was missing from the request");
        this.key = key;
    }

    /**
     *  The user's key
     * 
     *  @return     the user's unique key
     */
    public String getKey() {
        return this.key;
    }
}