
package com.sandcage.api.service.delete;

import com.sandcage.api.service.File;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.Payload;
import java.util.ArrayList;


/**
 *  Implements the {@link Payload} associated with the destroy-files service.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/destroy_files">destroy-files docs</a>
 */
public class DestroyPayload extends Payload {

    private static final int MIN_FILES = 1;
    private static final int MAX_FILES_FREEPLAN = 10;
    private static final int MAX_FILES_PAIDPLAN = 1000;
    private static final int MIN_CALLBACK_URL_LEN = 4;
    private static final int MAX_CALLBACK_URL_LEN = 1000;

    // MANDATORY; CONDITION -> LENGTH: 1..1000
    private ArrayList<File> files;
    // OPTIONAL; CONDITION -> LENGTH: 4..1000
    private String callbackUrl;


    /**
     *  Creates a {@link DestroyPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      files           the files associated with this {@link DestroyPayload}
     * 
     *  @throws     OutOfBoundsException    if any value is outside permissible 
     *                                      bounds
     *  @throws     NullPointerException    if any required value is null
     */
    public DestroyPayload(String key, ArrayList<File> files) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
        checkPreconditions(files, null);
        this.files = files;
    }

    /**
     *  Creates a {@link DestroyPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      files           the files associated with this {@link DestroyPayload}
     *  @param      callbackUrl     the URL to use for the API callback
     * 
     *  @throws     OutOfBoundsException    if any value is outside permissible
     *                                      bounds
     *  @throws     NullPointerException    if any required value is null
     */
    public DestroyPayload(String key, ArrayList<File> files, String callbackUrl) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
        checkPreconditions(files, callbackUrl);
        this.files = files;
        if(callbackUrl!=null)
            this.callbackUrl = callbackUrl;
    }

    /**
     *  Conducts basic checks as to whether the given data conforms to spec.
     * 
     *  @param      files           the files associated with this {@link DestroyPayload}
     *  @param      callbackUrl     the URL to use for the API callback, if any
     * 
     *  @throws     OutOfBoundsException    if the number of files or callbackUrl
     *                                      length does not meet the spec
     *  @throws     NullPointerException    if files is null
     */
    private void checkPreconditions(ArrayList<File> files, String callbackUrl)  
            throws OutOfBoundsException, NullPointerException {
        if(files==null)
            throw new NullPointerException("The file, or set thereof, to delete was missing from the request");
        // ASSUME YOU ARE OPERATING UNDER A FREE PLAN: AMEND CONSTANTS IF OTHERWISE
        if(files!=null && (files.size()<MIN_FILES || files.size()>MAX_FILES_FREEPLAN))
            throw new OutOfBoundsException("files", String.valueOf(files.size())+" entries", 
                "NUMBER OF FILES->("+MIN_FILES+"<="+files.size()+"<="+MAX_FILES_FREEPLAN+")=="+(files.size()<MIN_FILES||files.size()>MAX_FILES_FREEPLAN));
        if(callbackUrl!=null && (callbackUrl.length()<MIN_CALLBACK_URL_LEN || callbackUrl.length()>MAX_CALLBACK_URL_LEN))
            throw new OutOfBoundsException("callbackUrl", callbackUrl, 
                "LENGTH->("+MIN_CALLBACK_URL_LEN+"<="+callbackUrl.length()+"<="+MAX_CALLBACK_URL_LEN+")==false");
    }

    /**
     *  The set of files associated with this {@link DestroyPayload}, if any.
     *  
     *  @return     the set of files associated with this {@link DestroyPayload}, 
     *              if any.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public ArrayList<File> getFiles() {
        return this.files;
    }

    /**
     *  The URL at which to send {@link Task} state, if any.
     *  
     *  @return     the URL at which to send {@link Task} state.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCallback_url() {
        return this.callbackUrl;
    }
}