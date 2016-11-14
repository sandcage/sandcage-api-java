
package com.sandcage.api.service.info;

import com.sandcage.api.service.File;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.Payload;
import java.util.ArrayList;


/**
 *  Implements the {@link Payload} associated with the get-info service.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/get_info">get-info docs</a>
 */
public class InfoPayload extends Payload {

    private static final int MIN_REQID_LEN = 10;
    private static final int MAX_REQID_LEN = 100;
    private static final int MIN_FILES = 1;
    private static final int MAX_FILES_FREEPLAN = 100;
    private static final int MAX_FILES_PAIDPLAN = 10000;

    // CONDITIONAL: EITHER A requestId OR A set of files MUST BE PRESENT IN THE 
    //              REQUEST; IF BOTH ARE PROVIDED, THEN THE API WILL ONLY FACTOR
    //              THE request_id VALUE

    // CONDITIONAL; CONDITIONS -> LENGTH: 10..100
    private String requestId;
    // CONDITIONAL; CONDITIONS -> LENGTH: 1..1000
    private ArrayList<File> files;


    /**
     *  Creates an {@link InfoPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      requestId       the request_id associated with this {@link InfoPayload}
     *  @param      files           the files associated with this {@link InfoPayload}
     * 
     *  @throws     OutOfBoundsException    if any conditional value is outside
     *                                      permissible bounds
     *  @throws     NullPointerException    if any required combination or value 
     *                                      is null
     */
    public InfoPayload(String key, String requestId, ArrayList<File> files) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
        checkPreconditions(requestId, files);
        if(requestId!=null)
            this.requestId = requestId;
        if(files!=null)
            this.files = files;
    }

    /**
     *  Conducts basic checks as to whether the given data conforms to spec.
     * 
     *  @param      requestId       the request_id associated with this {@link InfoPayload}
     *  @param      files           the files associated with this {@link InfoPayload}
     * 
     *  @throws     OutOfBoundsException    if the request_id length or number of
     *                                      files is outside permissible bounds
     *  @throws     NullPointerException    if no input is provided
     */
    private void checkPreconditions(String requestId, ArrayList<File> files)  
            throws OutOfBoundsException, NullPointerException {
        if(requestId==null && files==null)
            throw new NullPointerException("Neither a request_id nor a set of desired files was provided in the request");
        if(requestId==null && files.size()==0)
            throw new OutOfBoundsException("files", String.valueOf(files.size())+" entries", "NUMBER OF FILES->("+files.size()+"==0)=="+(files.size()==0));
        if(requestId!=null && (requestId.length()<MIN_REQID_LEN || requestId.length()>MAX_REQID_LEN))
            throw new OutOfBoundsException("request_id", requestId, "LENGTH->("+MIN_REQID_LEN+"<"+requestId.length()+"<"+MAX_REQID_LEN+")=="+(requestId.length()<MIN_REQID_LEN||requestId.length()>MAX_REQID_LEN));
        // ASSUME YOU ARE OPERATING UNDER A FREE PLAN: AMEND CONSTANTS IF OTHERWISE
        if(files!=null && (files.size()<MIN_FILES || files.size()>MAX_FILES_FREEPLAN))
            throw new OutOfBoundsException("files", String.valueOf(files.size())+" entries", "NUMBER OF FILES->("+MIN_FILES+"<="+files.size()+"<="+MAX_FILES_FREEPLAN+")=="+(files.size()<MIN_FILES||files.size()>MAX_FILES_FREEPLAN));
    }

    /**
     *  The request_id associated with this {@link InfoPayload}, if any.
     *  
     *  @return     the request_id associated with this {@link InfoPayload}.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getRequest_id() {
        return this.requestId;
    }

    /**
     *  The set of files associated with this {@link InfoPayload}, if any.
     *  
     *  @return     the set of files associated with this {@link InfoPayload}.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public ArrayList<File> getFiles() {
        return this.files;
    }
}