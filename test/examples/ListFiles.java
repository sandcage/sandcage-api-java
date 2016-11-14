
package examples;

import com.sandcage.api.SandCage;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.enumerate.ListPayload;


/**
 *  Class which allows you to list/enumerate your assets/files, including some
 *  basic information thereof.
 *  <p>
 *  This sample provides four examples:
 *  <ol type="1">
 *  <li>list all files (up to the max default API limits)</li>
 *  <li>list files of a named (sub)directory (up to the max default API limits)</li>
 *  <li>list the first ten files of a named (sub)directory</li>
 *  <li>list the first ten files</li>
 *  </ol>
 *  <p>
 *  You should comment out or remove what does not meet your requirements and 
 *  ideally build on the sample.
 *
 *  @date       03/11/2016
 *  @version    0.2
 */
public class ListFiles {


    public static void main(String args[]) 
            throws OutOfBoundsException {

        // NOTE: add, remove, or comment out the below as dictated by your requirements

        // Example 1) List all files, considering API defaults
        listAllFiles();

        // Example 2) List files of a given (sub)directory only, considering API defaults
        listFilesForGivenDirectory("DIRECTORY_NAME_GOES_HERE");

        // Example 3) List the first ten files of a given (sub)directory only
        listLimitedNumberOfFilesForGivenDirectory("DIRECTORY_NAME_GOES_HERE", 1, 10);

        // Example 4) List the first ten files
        listLimitedNumberOfFiles(1, 10);
    }

    public static void listAllFiles() 
            throws OutOfBoundsException {

        // Create a payload without specifying directory, pagination, or # of results/page
        ListPayload payload = new ListPayload(SandCage.getKey());

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.listFiles();
    }

    public static void listFilesForGivenDirectory(String directory) 
            throws OutOfBoundsException {

        // Create a payload which specifies a (sub)directory to search, but which 
        // specifies no pagination or the # of results/page
        ListPayload payload = new ListPayload(SandCage.getKey(), directory);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.listFiles();
    }

    public static void listLimitedNumberOfFilesForGivenDirectory(
                String directory, int page, int resultsPerPage) 
            throws OutOfBoundsException {

        // Create a payload which specifies directory, pagination, and # of results/page
        ListPayload payload = new ListPayload(SandCage.getKey(), directory, page, resultsPerPage);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.listFiles();
    }

    public static void listLimitedNumberOfFiles(int page, int resultsPerPage) 
            throws OutOfBoundsException {

        // Create a payload which specifies pagination and # of results/page
        ListPayload payload = new ListPayload(SandCage.getKey(), null, page, resultsPerPage);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.listFiles();
    }
}