
package com.sandcage.api.service;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 *  Represents a generic wrapper for services accessing file assets.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://api.sandcage.com/docs/0.2/">SandCage API v0.2</a>
 */
public abstract class File {

    private static final int MIN_FILETOKEN_LEN = 30;
    private static final int MAX_FILETOKEN_LEN = 70;

    // SERVICE get-info: MANDATORY; CONDITIONS -> LENGTH: 30..70
    // SERVICE destroy-files: OPTIONAL; CONDITIONS -> LENGTH: 30..70
    private String fileToken;


    /**
     *  Creates a {@link File}.
     *  
     *  @param      fileToken       the file_token associated with this {@link File}
     *  @param      mandatory       whether to apply a mandatory condition
     * 
     *  @throws     OutOfBoundsException    if the length of the file_token is 
     *                                      not with permissible bounds
     *  @throws     NullPointerException    if the file_token is null
     */
    public File(String fileToken, boolean mandatory) 
            throws OutOfBoundsException, NullPointerException {
        if(mandatory && fileToken==null)
            throw new NullPointerException("The file_token was missing from the request");
        if(fileToken!=null && (fileToken.length()<MIN_FILETOKEN_LEN || fileToken.length()>MAX_FILETOKEN_LEN))
            throw new OutOfBoundsException("file_token", fileToken,
                "LENGTH->("+MIN_FILETOKEN_LEN+"<="+fileToken.length()+"<="+MAX_FILETOKEN_LEN+")==false");
        if(fileToken!=null)
            this.fileToken = fileToken;
    }

    /**
     *  The file_token associated with this file.
     *  
     *  @return     the file_token
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getFile_token() {
        return this.fileToken;
    }
}