
package com.sandcage.api.service.info;

import com.sandcage.api.service.File;
import com.sandcage.api.service.OutOfBoundsException;


/**
 *  Represents a file-asset wrapper within the context of the get-info service.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/get_info">get-info</a>
 */
public class InfoFile extends File {


    /**
     *  Creates an {@link InfoFile}.
     *  
     *  @param      fileToken       the file_token associated with this {@link InfoFile}
     * 
     *  @throws     OutOfBoundsException    if the length of the file_token is 
     *                                      not with permissible bounds
     *  @throws     NullPointerException    if the file_token is null
     */
    public InfoFile(String fileToken) 
            throws OutOfBoundsException, NullPointerException {
        super(fileToken, true);
    }
}