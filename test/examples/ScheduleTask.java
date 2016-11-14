
package examples;

import com.sandcage.api.SandCage;
import com.sandcage.api.service.put.Job;
import com.sandcage.api.service.put.ScheduledPayload;
import com.sandcage.api.service.put.Task;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.put.Cover;
import com.sandcage.api.service.put.Crop;
import com.sandcage.api.service.put.Resize;
import com.sandcage.api.service.put.Rotate;
import com.sandcage.api.service.put.Save;
import java.util.ArrayList;


/**
 *  Class which allows you to schedule the asynchronous execution of action(s) 
 *  (ie: save, resize, crop, cover, rotate) on a given asset, or set thereof.
 *  <p>
 *  This sample provides three examples:
 *  <ol type="1">
 *  <li>how to instruct the API to save a copy of a given resource, identified
 *  by its URL</li>
 *  <li>how to instruct the API to execute a set of actions, each generating a
 *  distinct asset as a result, on an image identified by its URL</li>
 *  <li>how to instruct the API to execute a set of actions, each generating a
 *  distinct asset as a result, on a set of images, each identified by its URL</li>
 *  </ol>
 *  <p>
 *  You should comment out or remove what does not meet your requirements and 
 *  ideally build on the sample.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 */
public class ScheduleTask {


    public static void main(String args[]) 
            throws OutOfBoundsException {

        // NOTE: add, remove, or comment out the below as dictated by your requirements

        // Example 1: Save a file
        scheduleExecutionSingleFile("URL_OF_FILE_OR_IMAGE_TO_SAVE");

        // Example 2: Save, Crop, Resize, Cover, and Rotate an image
        ArrayList<Task> example2Tasks = new ArrayList<Task>();
        example2Tasks.add(new Save());                                          // save a copy of the image on SandCage
        example2Tasks.add(new Crop(100,150,500,450));                           // save a cropped copy, x-axis:100-500, y-axis: 150-450, of the image on SandCage
        example2Tasks.add(new Resize(75f));                                     // save a scaled copy, 75% the size of the original, on SandCage
        example2Tasks.add(new Resize(300,300));                                 // save a resized copy, 300x300, on SandCage
        example2Tasks.add(new Cover(250,350,null,null));                        // save a copy which covers dimensions 250x350, using default alignment, on SandCage
        example2Tasks.add(
            new Cover(250,350,Cover.COVER_X_LEFT,Cover.COVER_Y_TOP));           // save a copy which covers dimensions 250x350, using top-left-hand-side alignment, on SandCage
        example2Tasks.add(new Rotate(180));                                     // save a 180 degree, clockwise-rotated, copy on SandCage

        scheduleExecutionSingleFileMultipleTasks("URL_OF_IMAGE", example2Tasks);

        // Example 3: Resize and Rotate three images
        ArrayList<Task> example3Tasks1 = new ArrayList<Task>();
        example3Tasks1.add(new Resize(60f));                                    // save a scaled copy, 60% the size of the original, on SandCage
        example3Tasks1.add(new Rotate(90));                                     // save a 90 degree, clockwise-rotated, copy on SandCage

        ArrayList<Task> example3Tasks2 = new ArrayList<Task>();
        example3Tasks2.add(new Resize(70f));                                    // save a scaled copy, 70% the size of the original, on SandCage
        example3Tasks2.add(new Rotate(180));                                    // save a 180 degree, clockwise-rotated, copy on SandCage

        ArrayList<Task> example3Tasks3 = new ArrayList<Task>();
        example3Tasks3.add(new Resize(80f));                                    // save a scaled copy, 80% the size of the original, on SandCage
        example3Tasks3.add(new Rotate(270));                                    // save a 270 degree, clockwise-rotated, copy on SandCage

        ArrayList<Job> jobs = new ArrayList<Job>();
        jobs.add(new Job("URL_OF_IMAGE1", example3Tasks1));
        jobs.add(new Job("URL_OF_IMAGE2", example3Tasks2));
        jobs.add(new Job("URL_OF_IMAGE3", example3Tasks3));

        scheduleExecutionMultipleFilesMultipleTasks(jobs);
    }

    private static void scheduleExecutionSingleFile(String imageUrl) 
            throws OutOfBoundsException {

        ArrayList<Task> tasks = new ArrayList<Task>();
        // Specify the action to execute (here a Save)
        tasks.add(new Save());

        ArrayList<Job> jobs = new ArrayList<Job>();
        // Add the action(s) to the list of jobs associated with the given URL (only one here)
        jobs.add(new Job(imageUrl, tasks));

        // Create a payload specifying the jobs to execute
        ScheduledPayload payload = new ScheduledPayload(SandCage.getKey(), jobs);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.scheduleTasks();
    }

    private static void scheduleExecutionSingleFileMultipleTasks(String imageUrl, ArrayList<Task> tasks) 
            throws OutOfBoundsException {

        // Add the action(s) to the list of jobs associated with the given URL (only one here)
        ArrayList<Job> jobs = new ArrayList<Job>();
        jobs.add(new Job(imageUrl, tasks));

        // Create a payload specifying the jobs to execute
        ScheduledPayload payload = new ScheduledPayload(SandCage.getKey(), jobs);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.scheduleTasks();
    }

    private static void scheduleExecutionMultipleFilesMultipleTasks(ArrayList<Job> jobs) 
            throws OutOfBoundsException {

        // Create a payload specifying the jobs to execute
        ScheduledPayload payload = new ScheduledPayload(SandCage.getKey(), jobs);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.scheduleTasks();
    }
}