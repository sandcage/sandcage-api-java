
package com.sandcage.api.service.enumerate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.Payload;


/**
 *  Implements the {@link Payload} associated with the list-files service.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/list_files">list-files docs</a>
 */
public class ListPayload extends Payload {

    private static final int MIN_VALUE = 1;
    private static final int MAX_DIRECTORY_LEN = 500;
    private static final int MAX_RESULTS_PER_PAGE_VALUE = 200;

    public static final int PAGINATION_DEFAULTS = 1;
    public static final int RESULTS_PP_DEFAULTS = 100;

    // OPTIONAL; API DEFAULT:ROOT DIRECTORY; CONDITIONS -> LENGTH: 1..500
    private String directory;
    // OPTIONAL; API DEFAULT:1; CONDITIONS -> LENGTH: 1..
    private int page;
    // OPTIONAL; API DEFAULT:100; CONDITIONS -> LENGTH: 1..200
    private int resultsPerPage;


    /**
     *  Creates a {@link ListPayload}.
     *  
     *  @param      key             the user's unique key
     * 
     *  @throws     OutOfBoundsException    if any value is outside permissible
     *                                      bounds
     *  @throws     NullPointerException    if the key is null
     */
    public ListPayload(String key) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
    }

    /**
     *  Creates a {@link ListPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      directory       the requested directory from which to list 
     *                              available files
     * 
     *  @throws     OutOfBoundsException    if any value is outside permissible
     *                                      bounds
     *  @throws     NullPointerException    if the key is null
     */
    public ListPayload(String key, String directory) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
        checkPreconditions(directory, PAGINATION_DEFAULTS, RESULTS_PP_DEFAULTS);
        if(directory!=null)
            this.directory = directory;
    }

    /**
     *  Creates a {@link ListPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      directory       the requested directory from which to list 
     *                              available files
     *  @param      page            the targeted pagination results
     *  @param      resultsPerPage  the number of entries to be returned in the 
     *                              response
     * 
     *  @throws     OutOfBoundsException    if any value is outside permissible
     *                                      bounds
     *  @throws     NullPointerException    if the key is null
     */
    public ListPayload(String key, String directory, int page, int resultsPerPage) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
        checkPreconditions(directory, page, resultsPerPage);
        if(directory!=null)
            this.directory = directory;
        this.page = page;
        this.resultsPerPage = resultsPerPage;
    }

    /**
     *  Conducts basic checks as to whether the given data conforms to spec.
     * 
     *  @param      directory       the requested directory from which to list 
     *                              available files
     *  @param      page            the targeted pagination results
     *  @param      resultsPerPage  the number of entries to be returned in the 
     * 
     *  @throws     OutOfBoundsException    if any value is outside permissible
     *                                      bounds
     */
    private void checkPreconditions(String directory, int page, int resultsPerPage)  
            throws OutOfBoundsException {
        if(directory!=null && (directory.length()<MIN_VALUE || directory.length()>MAX_DIRECTORY_LEN))
            throw new OutOfBoundsException("directory", directory, "LENGTH->("+MIN_VALUE+"<="+directory.length()+"<="+MAX_DIRECTORY_LEN+")=="+(directory.length()<MIN_VALUE||directory.length()>MAX_DIRECTORY_LEN));
        if(page<MIN_VALUE)
            throw new OutOfBoundsException("page", String.valueOf(page), "page->("+page+">="+MIN_VALUE+")=="+(page>=MIN_VALUE));
        if(resultsPerPage<MIN_VALUE || resultsPerPage>MAX_RESULTS_PER_PAGE_VALUE)
            throw new OutOfBoundsException("results_per_page", String.valueOf(resultsPerPage), "results_per_page->("+MIN_VALUE+"<="+resultsPerPage+"<="+MAX_RESULTS_PER_PAGE_VALUE+")=="+(resultsPerPage<MIN_VALUE||resultsPerPage>MAX_RESULTS_PER_PAGE_VALUE));
    }

    /**
     *  The directory associated with this {@link ListPayload}, if any.
     *  
     *  @return     the directory associated with this {@link ListPayload}.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getDirectory() {
        return this.directory;
    }

    /**
     *  The pagination associated with this {@link ListPayload}, if any.
     *  
     *  @return     the pagination associated with this {@link ListPayload}.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getPage() {
        return this.page;
    }

    /**
     *  The results per page associated with this {@link ListPayload}, if any.
     *  
     *  @return     the results per page associated with this {@link ListPayload}.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getResults_per_page() {
        return this.resultsPerPage;
    }
}