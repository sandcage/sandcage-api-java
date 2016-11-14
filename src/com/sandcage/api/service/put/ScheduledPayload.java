
package com.sandcage.api.service.put;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.Payload;
import java.util.ArrayList;


/**
 *  Implements the {@link Payload} associated with the schedule-tasks service.
 *
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class ScheduledPayload extends Payload {

    private static final int MIN_JOBS_FREEPLAN = 1;
    private static final int MAX_JOBS_FREEPLAN = 10;
    private static final int MIN_JOBS_PAIDPLAN = 1;
    private static final int MAX_JOBS_PAIDPLAN = 1000;
    private static final int MIN_CALLBACK_URL_LEN = 4;
    private static final int MAX_CALLBACK_URL_LEN = 1000;

    // MANDATORY; CONDITIONS -> PAID PLANS: 1..1000 JOBS/REQUEST; FREE PLANS: 1..10 JOBS/REQUEST
    private ArrayList<Job> jobs;
    // OPTIONAL; CONDITIONS -> LENGTH: 4..1000
    private String callbackUrl;


    /**
     *  Creates a {@link ScheduledPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      jobs            the {@link Job jobs} associated with the
     *                              {@link ScheduledPayload}
     * 
     *  @throws     OutOfBoundsException    if the number of jobs is not within
     *                                      the permissible threshold
     *  @throws     NullPointerException    if any required value is null
     */
    public ScheduledPayload(String key, ArrayList<Job> jobs) 
            throws OutOfBoundsException, NullPointerException {
        super(key);
        checkPreconditions(jobs, null);
        this.jobs = jobs;
    }

    /**
     *  Creates a {@link ScheduledPayload}.
     *  
     *  @param      key             the user's unique key
     *  @param      jobs            the {@link Job jobs} associated with the 
     *                              {@link ScheduledPayload}
     *  @param      callbackUrl     the URL to use for the API callback
     * 
     *  @throws     OutOfBoundsException    if the number of jobs is not within
     *                                      the permissible threshold
     *  @throws     NullPointerException    if any required value is null
     */
    public ScheduledPayload(String key, ArrayList<Job> jobs, String callbackUrl) 
            throws OutOfBoundsException {
        super(key);
        checkPreconditions(jobs, callbackUrl);
        this.jobs = jobs;
        if(callbackUrl!=null)
            this.callbackUrl = callbackUrl;
    }

    /**
     *  Conducts basic checks as to whether the given data conforms to spec.
     * 
     *  @param  jobs        the {@link Job jobs} associated with the {@link ScheduledPayload}
     *  @param  callbackUrl the URL to use for the API callback
     * 
     *  @throws     OutOfBoundsException    if the number of jobs is not within
     *                                      the permissible threshold
     *  @throws     NullPointerException    if jobs is null
     */
    private void checkPreconditions(ArrayList<Job> jobs, String callbackUrl)  
            throws OutOfBoundsException {
        if(jobs==null)
            throw new NullPointerException("Mandatory field [ jobs ] was missing from the request");
        // ASSUME YOU ARE OPERATING UNDER A FREE PLAN: AMEND CONSTANTS IF OTHERWISE
        if(jobs.size()<MIN_JOBS_FREEPLAN || jobs.size()>MAX_JOBS_FREEPLAN)
            throw new OutOfBoundsException("jobs", String.valueOf(jobs.size())+" entries", 
                "NUMBER OF JOBS->("+MIN_JOBS_FREEPLAN+"<="+jobs.size()+"<="+MAX_JOBS_FREEPLAN+")==false");
        if(callbackUrl!=null && (callbackUrl.length()<MIN_CALLBACK_URL_LEN || callbackUrl.length()>MAX_CALLBACK_URL_LEN))
            throw new OutOfBoundsException("callbackUrl", callbackUrl, 
                "LENGTH->("+MIN_CALLBACK_URL_LEN+"<="+callbackUrl.length()+"<="+MAX_CALLBACK_URL_LEN+")==false");
    }

    /**
     *  The {@link Job jobs} associated with this {@link ScheduledPayload}
     *  
     *  @return     the {@link Job jobs} associated with the {@link ScheduledPayload}
     */
    public ArrayList<Job> getJobs() {
        return this.jobs;
    }

    /**
     *  The URL at which to send {@link Task} state, if any.
     *  
     *  @return     the URL at which to send {@link Task} state, if any.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCallback_url() {
        return this.callbackUrl;
    }
}