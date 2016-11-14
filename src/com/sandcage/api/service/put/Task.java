
package com.sandcage.api.service.put;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;


/**
 *  A SandCage API {@link Task} instance constitutes an executable action upon a 
 *  resource/image with which it is associated.
 *  <p>
 *  The members associated with an instance of this class are, for the most, optional.
 *  In all cases, member must meet certain prerequisites. Some checks against the 
 *  said prerequisites are implemented herein. However, as these may be subject 
 *  to change, please refer to the SandCage API, service schedule-tasks, for further 
 *  information.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public abstract class Task {

    private static final int MIN_LEN = 0;
    private static final int MAX_REFERENCE_ID_LEN = 100;
    private static final int MAX_DIRECTORY_LEN = 500;
    private static final int MAX_FILENAME_LEN = 250;

    // OPTIONAL; CONDITIONS -> LENGTH: 0..100
    private String referenceId;
    // OPTIONAL; API DEFAULT:FALSE
    private boolean overwriteFile;
    // OPTIONAL; CONDITIONS -> LENGTH: 0..500
    private String directory;
    // OPTIONAL; CONDITIONS -> LENGTH: 0..250
    private String filename;
    // MANDATORY: CONDITIONS -> SUPPORTED ACTIONS: save, resize, crop, rotate, cover
    private String action;


    /**
     *  Creates an instance of this class, identifying itself as the type indicated
     *  by input parameter action.
     * 
     *  @param      action      the type of {@link Task} (ie: save, resize, crop, 
     *                          rotate, cover)
     * 
     *  @throws     NullPointerException    if action is null
     */
    public Task(String action) 
            throws NullPointerException {
        if(action==null)
            throw new NullPointerException("Mandatory field [ action ] was missing from the request");
        this.action = action;
    }

    /**
     *  Sets the reference identifier.
     * 
     *  @param      referenceId     the reference identifier associated with the
     *                              given image
     * 
     *  @throws     OutOfBoundsException    if the reference identifier fails to
     *                                      meet the required length prerequisites
     */
    public void setReferenceId(String referenceId) 
            throws OutOfBoundsException {
        if(referenceId!=null && referenceId.length()>MAX_REFERENCE_ID_LEN)
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

    /**
     *  Whether existing resources should be overwritten.
     * 
     *  @return     <tt>true</tt> if existing resources should be overwritten,
     *              else <tt>false</tt>
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public boolean getOverwrite_file() {
        return this.overwriteFile;
    }

    /**
     *  Sets the output directory/path.
     * 
     *  @param  directory   the output directory/path associated with an instance
     *                      of this class
     * 
     *  @throws     OutOfBoundsException    if the output directory/path fails to
     *                                      meet the required length prerequisites
     */
    public void setDirectory(String directory)
            throws OutOfBoundsException {
        if(directory!=null && directory.length()>MAX_DIRECTORY_LEN)
            throw new OutOfBoundsException("directory", directory, "("+MIN_LEN+"<="+directory.length()+"<="+MAX_DIRECTORY_LEN+")==false");
        this.directory = directory;
    }

    /**
     *  The output directory/path.
     * 
     *  @return     the output directory/path, if any
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDirectory() {
        return this.directory;
    }

    /**
     *  Sets the desired filename for the output file.
     * 
     *  @param  filename    the desired filename for the output file
     * 
     *  @throws     OutOfBoundsException    if the filename fails to meet the 
     *                                      required length prerequisites
     */
    public void setFilename(String filename)
            throws OutOfBoundsException {
        if(filename!=null && filename.length()>MAX_FILENAME_LEN)
            throw new OutOfBoundsException("filename", filename, "("+MIN_LEN+"<="+filename.length()+"<="+MAX_FILENAME_LEN+")==false");
        this.filename = filename;
    }

    /**
     *  The desired filename for the output file.
     * 
     *  @return     the desired filename for the output file, if any
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getFilename() {
        return this.filename;
    }

    /**
     *  The type of {@link Task} (ie: save, resize, crop, rotate, cover)
     * 
     *  @return     the type of {@link Task} (ie: action; to be effected on the
     *              associated {@link Job#url resource)
     */
    public String getActions() {
        return this.action;
    }
}