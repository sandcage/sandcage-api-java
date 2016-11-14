
package examples;

import com.sandcage.api.SandCage;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.File;
import com.sandcage.api.service.info.InfoFile;
import com.sandcage.api.service.info.InfoPayload;
import java.util.ArrayList;


/**
 *  Class which allows you to obtain information on a given set of assets/files.
 *  <p>
 *  This sample provides three examples:
 *  <ol type="1">
 *  <li>get file information for a given file, identified by its file token</li>
 *  <li>get file information for a given set of files, identified by file tokens</li>
 *  <li>get file information for any file, or files, associated with a given 
 *  request identifier</li>
 *  </ol>
 *  <p>
 *  You should comment out or remove what does not meet your requirements and 
 *  ideally build on the sample.
 *
 *  @date       03/11/2016
 *  @version    0.2
 */
public class GetInfo {


    public static void main(String args[]) 
            throws OutOfBoundsException {

        // NOTE: add, remove, or comment out the below as dictated by your requirements

        // Example 1) Get file information of a single file, identified by the given file token
        getInfoForSingleFile("THE_FILE_TOKEN_TO_GET_INFO_ON");

        // Example 2) Get file information for multiple files, identified by the given file tokens
        ArrayList<File> files = new ArrayList<File>();
        files.add(new InfoFile("THE_FILE_TOKEN_TO_GET_INFO_ON"));
        files.add(new InfoFile("THE_FILE_TOKEN_TO_GET_INFO_ON"));
        files.add(new InfoFile("THE_FILE_TOKEN_TO_GET_INFO_ON"));
        getInfoForMultipleFiles(files);

        // Example 3) Get information on any files identified by a given request identifier
        getInfoForRequestId("THE_REQUEST_ID");
    }

    private static void getInfoForSingleFile(String fileToken) 
            throws OutOfBoundsException {

        ArrayList<File> files = new ArrayList<File>();
        // Specify the file, identifying it by its file token
        files.add(new InfoFile(fileToken));

        // Create a payload specifying the specific file to get info on
        InfoPayload payload = new InfoPayload(SandCage.getKey(), null, files);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.getInfo();
    }

    private static void getInfoForMultipleFiles(ArrayList<File> files) 
            throws OutOfBoundsException {

        // Create a payload specifying the specific files to get info on
        InfoPayload payload = new InfoPayload(SandCage.getKey(), null, files);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.getInfo();
    }

    private static void getInfoForRequestId(String requestId) 
            throws OutOfBoundsException {

        // Create a payload specifying the request identifier get info on
        InfoPayload payload = new InfoPayload(SandCage.getKey(), requestId, null);

        // Dispatch your request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.getInfo();
    }
}
