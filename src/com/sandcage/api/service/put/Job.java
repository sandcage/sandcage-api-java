
package com.sandcage.api.service.put;

import com.sandcage.api.service.OutOfBoundsException;
import java.util.ArrayList;


/**
 *  A SandCage API {@link Job} consists of a URL, at which the image/resource 
 *  of interest resides, and an array of one or more {@link Task tasks}, which 
 *  determine what operation(s) is(are) to be effected on the given image/resource.
 *  <p>
 *  Both the URL and {@link Task tasks} must meet certain prerequisites. Some 
 *  checks against the said prerequisites are implemented herein. However, as 
 *  these may be subject to change, please refer to the SandCage API, service 
 *  schedule-tasks, for further information.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 *  @see        <a href="https://www.sandcage.com/docs/0.2/schedule_tasks">schedule-tasks docs</a>
 */
public class Job {

    private static final int MIN_URL_LEN = 4;
    private static final int MAX_URL_LEN = 1000;
    private static final int MIN_TASKS = 1;
    private static final int MAX_TASKS = 10;

    // MANDATORY; CONDITIONS -> LENGTH 4..1000
    // SOME EXAMPLES: www.abc.com/imagex, http://www.abc.com/img.png, https://www.xyz.co/a/b/c/d?image=x, a.ag
    private String url;
    // MANDATORY; CONDITIONS -> 1..10 TASKS/REQUEST
    private ArrayList<Task> tasks;


    /**
     *  Creates a {@link Job} using the given URL.
     *  <p>
     *  As any useful instance of this class requires an array of {@link Task},
     *  the {@link #setTasks(java.util.ArrayList)} should be invoked separately
     *  to set the relevant {@link Task tasks}.
     *  
     *  @param      url         the URL at which the image of interest resides
     * 
     *  @throws     OutOfBoundsException    if the URL length is below/above
     *                                      permissible length
     *  @throws     NullPointerException    if the URL is null
     */
    public Job(String url) 
            throws OutOfBoundsException, NullPointerException {
        if(url==null)
            throw new NullPointerException("Mandatory field [ url ] was missing from the request");
        if(url.length()<MIN_URL_LEN || url.length()>MAX_URL_LEN)
            throw new OutOfBoundsException("url", url, "URL LENGTH->("+MIN_URL_LEN+"<="+url.length()+"<="+MAX_URL_LEN+")==false");
        this.url = url;
    }

    /**
     *  Creates a {@link Job} using the given URL and array of {@link Task tasks}.
     *  
     *  @param      url         the URL at which the image of interest resides
     *  @param      tasks       the {@link Task tasks} associated with the {@link Job}
     * 
     *  @throws     OutOfBoundsException    if the URL lenght is below/above
     *                                      permissible length OR if the number
     *                                      of tasks is below/above the amount
     *                                      permissible
     *  @throws     NullPointerException    if the URL and/or tasks are/is null
     */
    public Job(String url, ArrayList<Task> tasks) 
            throws OutOfBoundsException, NullPointerException {
        if(url==null)
            throw new NullPointerException("Mandatory field [ url ] was missing from the request");
        if(url.length()<MIN_URL_LEN || url.length()>MAX_URL_LEN)
            throw new OutOfBoundsException("url", url, "URL LENGTH->("+MIN_URL_LEN+"<="+url.length()+"<="+MAX_URL_LEN+")==false");
        if(tasks==null)
            throw new NullPointerException("Mandatory field [ tasks ] was missing from the request");
        if(tasks.size()<MIN_TASKS || tasks.size()>MAX_TASKS)
            throw new OutOfBoundsException("tasks", String.valueOf(tasks.size())+" entries", "NUMBER OF TASKS->("+MIN_TASKS+"<="+tasks.size()+"<="+MAX_TASKS+")==false");
        this.url = url;
        this.tasks = tasks;
    }

    /**
     *  The URL associated with this {@link Job}
     * 
     *  @return     the URL at which the desired image/resource resides
     */
    public String getUrl() {
        return this.url;
    }

    /**
     *  Sets the {@link Task tasks} associated with this {@link Job}
     *  
     *  @param      tasks       the {@link Task tasks} associated with the {@link Job}
     * 
     *  @throws     OutOfBoundsException    the number of tasks is below/above 
     *                                      the amount permissible
     *  @throws     NullPointerException    if tasks is null
     */
    public void setTasks(ArrayList<Task> tasks) 
            throws OutOfBoundsException {
        if(tasks==null)
            throw new NullPointerException("Mandatory field [ tasks ] was missing from the request");
        if(tasks.size()<MIN_TASKS || tasks.size()>MAX_TASKS)
            throw new OutOfBoundsException("tasks", String.valueOf(tasks)+" entries", "NUMBER OF TASKS->("+MIN_TASKS+"<="+tasks.size()+"<="+MAX_TASKS+")==false");
        this.tasks = tasks;
    }

    /**
     *  The {@link Task tasks} associated with this {@link Job}
     *  
     *  @return     the {@link Task tasks} associated with the {@link Job}
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }
}