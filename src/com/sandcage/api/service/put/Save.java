
package com.sandcage.api.service.put;


/**
 *  Instructs the API to save the associated resource/image.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class Save extends Task {

    private static final String ACTION = Save.class.getSimpleName().toLowerCase();


    /**
     *  Creates an instance of {@link Save}, which instructs the API to save the 
     *  associated resource/image.
     * 
     *  @throws     NullPointerException    if a null action is provided
     */
    public Save() 
            throws NullPointerException {
        super(ACTION);
    }
}