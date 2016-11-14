
package com.sandcage.api.service.delete;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.info.*;
import com.sandcage.api.service.File;
import com.sandcage.api.service.OutOfBoundsException;


/**
 *  Represents a file-asset wrapper within the context of the destroy-files service.
 *
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/destroy_files">destroy-files</a>
 */
public class DestroyFile extends File {

    private static final int MIN_LEN = 1;
    private static final int MAX_REFERENCE_ID_LEN = 100;

    // OPTIONAL; CONDITIONS -> LENGTH: 1..100
    private String referenceId;


    /**
     *  Creates an {@link DestroyFile}.
     *  
     *  @param      referenceId     the reference identifier associated the given
     *                              image
     *  @param      fileToken       the file_token associated with this 
     *                              {@link DestroyFile}
     * 
     *  @throws     OutOfBoundsException    if the length of the file_token or
     *                                      reference_id are not with permissible 
     *                                      bounds
     */
    public DestroyFile(String referenceId, String fileToken) 
            throws OutOfBoundsException {
        super(fileToken, false);
        if(referenceId!=null && (referenceId.length()<MIN_LEN && referenceId.length()>MAX_REFERENCE_ID_LEN))
            throw new OutOfBoundsException("reference_id", referenceId, "LENGTH->("+MIN_LEN+"<="+referenceId.length()+"<="+MAX_REFERENCE_ID_LEN+")==false");
        this.referenceId = referenceId;
    }

    /**
     *  The reference identifier.
     * 
     *  @return     the reference identifier associated an instance of this class,
     *              if any.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getReference_id() {
        return this.referenceId;
    }
}