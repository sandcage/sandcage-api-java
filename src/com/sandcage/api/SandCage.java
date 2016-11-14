
package com.sandcage.api;

import com.sandcage.api.io.Dispatch;
import com.sandcage.api.service.Payload;
import com.sandcage.api.service.put.ScheduledPayload;


/**
 *  The SandCage API request dispatcher.
 *  <p>
 *  All requests to the SandCage API should be directed through an instance of 
 *  this class, in sequence invoking the relevant operation ({@link #scheduleTasks()}, 
 *  {@link #getInfo()}, {@link #getInfo()}, {@link #listFiles()}, or 
 *  {@link #destroyFiles() }).
 * 
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://api.sandcage.com/docs/0.2/">SandCage API v0.2</a>
 */
public class SandCage {

    private static final String ENDPOINT_BASE = "https://api.sandcage.com/";
    private static final String KEY = "YOUR_API_KEY";

    private static final String ENDPOINT_VERSION = "0.2";

    private static final String ENDPOINT_SCHEDULE_TASKS = "schedule-tasks";
    private static final String ENDPOINT_GET_INFO = "get-info";
    private static final String ENDPOINT_LIST_FILES = "list-files";
    private static final String ENDPOINT_DESTROY_FILES = "destroy-files";

    private Payload payload;


    /**
     *  Non-default constructor.
     *  <p>
     *  Sets the specialized {@link Payload} associated with a given request to 
     *  the SandCage API.
     * 
     *  @param  payload     the specialized {@link Payload} to dispatch to SandCage API
     */
    public SandCage(Payload payload) {
        if(payload==null)
            throw new NullPointerException("The payload was missing from the request");
        this.payload = payload;
    }

    /**
     *  Dispatches a given {@link ScheduledPayload}, via {@link Dispatch}, to the
     *  SandCage API schedule-tasks endpoint.
     * 
     *  @see    <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
     *  @see    <a href="https://api.sandcage.com/0.2/schedule-tasks">schedule-tasks endpoint</a>
     */
    public void scheduleTasks() {

        Dispatch dispatch = new Dispatch(this.payload, ENDPOINT_SCHEDULE_TASKS);
        dispatch.post();
    }

    /**
     *  Dispatches a given {@link InfoPayload}, via {@link Dispatch}, to the
     *  SandCage API get-info endpoint.
     * 
     *  @see    <a href="hhttps://www.sandcage.com/docs/0.2/get_info">get-info docs</a>
     *  @see    <a href="https://api.sandcage.com/0.2/get-info">get-info endpoint</a>
     */
    public void getInfo() {

        Dispatch dispatch = new Dispatch(this.payload, ENDPOINT_GET_INFO);
        dispatch.post();
    }

    /**
     *  Dispatches a given {@link ListPayload}, via {@link Dispatch}, to the
     *  SandCage API list-files endpoint.
     * 
     *  @see    <a href="https://www.sandcage.com/docs/0.2/list_files">list-files docs</a>
     *  @see    <a href="https://api.sandcage.com/0.2/list-files">list-files endpoint</a>
     */
    public void listFiles() {

        Dispatch dispatch = new Dispatch(this.payload, ENDPOINT_LIST_FILES);
        dispatch.post();
    }

    /**
     *  Dispatches a given {@link DestroyPayload}, via {@link Dispatch}, to the
     *  SandCage API destroy-files endpoint.
     * 
     *  @see    <a href="https://www.sandcage.com/docs/0.2/destroy_files">destroy-files docs</a>
     *  @see    <a href="https://api.sandcage.com/0.2/destroy-files">destroy-files endpoint</a>
     */
    public void destroyFiles() {

        Dispatch dispatch = new Dispatch(this.payload, ENDPOINT_DESTROY_FILES);
        dispatch.post();
    }

    /**
     *  The endpoint scheme and host.
     * 
     *  @return the endpoint scheme and host
     */
    public static String getEndpointBase() {
        return ENDPOINT_BASE;
    }

    /**
     *  The endpoint version to which the request will be submitted.
     * 
     *  @return the endpoint version
     */
    public static String getEndpointVersion() {
        return ENDPOINT_VERSION;
    }

    /**
     *  The user's unique SandCage key.
     * 
     *  @return the user's key
     */
    public static String getKey() {
        return KEY;
    }
}