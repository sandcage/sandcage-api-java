
package examples;

import com.sandcage.api.SandCage;
import com.sandcage.api.service.File;
import com.sandcage.api.service.OutOfBoundsException;
import com.sandcage.api.service.delete.DestroyFile;
import com.sandcage.api.service.delete.DestroyPayload;
import java.util.ArrayList;


/**
 *  Class which allows you to request the deletion ("destruction") of files.
 *  <p>
 *  This sample provides three examples:
 *  <ol type="1">
 *  <li>deletion of a single file identifying it by its reference_id value</li>
 *  <li>deletion of a single file identifying it by its file_token value</li>
 *  <li>deletion of multiple files, identifying some by reference_id, others
 *  by token_id value</li>
 *  </ol>
 *  <p>
 *  You should comment out or remove what does not meet your requirements and 
 *  ideally build on the sample.
 * 
 *  @date       03/11/2016
 *  @version    0.2
 */
public class DestroyFiles {


    public static void main(String args[]) 
            throws OutOfBoundsException {

        // NOTE: add, remove, or comment out the below as dictated by your requirements

        // Example 1) Delete a single asset, identifying it by its reference_id
        destroySingleFileUsingItsReferenceId("THE_FILE_REFERENCE_ID_TO_DELETE");

        // Example 2) Delete a single asset, identifying it by its file_token
        destroySingleFileUsingItsFileToken("THE_FILE_TOKEN_TO_DELETE");

        // Example 3) Destory multiple assets
        ArrayList<File> files = new ArrayList<File>();
        files.add(new DestroyFile("THE_FILE_REFERENCE_ID_TO_DELETE", null));    // Identify this file to delete by its reference_id
        files.add(new DestroyFile(null, "THE_FILE_TOKEN_TO_DELETE"));           // Identify this file to delete by its file_token
        files.add(new DestroyFile("THE_FILE_REFERENCE_ID_TO_DELETE", null));    // Identify another file to delete by its reference_id
        files.add(new DestroyFile(null, "THE_FILE_TOKEN_TO_DELETE"));           // Identify another file to delete by its file_token
        destroyMultipleAssets(files);
    }

    private static void destroySingleFileUsingItsReferenceId(String referenceId) 
            throws OutOfBoundsException {

        ArrayList<File> files = new ArrayList<File>();
        // Specify the deletion of a file by identifying it by its reference_id
        files.add(new DestroyFile(referenceId,null));

        // Create a payload without specifying a custom callback URL
        DestroyPayload payload = new DestroyPayload(SandCage.getKey(),files);
        // Create a payload specifying a custom callback URL
        // DestroyPayload payload = new DestroyPayload(SandCage.getKey(),files,
        //     "http://YOUR_CALLBACK_URL/PATH?QUERY_PART");

        // Dispatch the request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.destroyFiles();
    }

    private static void destroySingleFileUsingItsFileToken(String fileToken) 
            throws OutOfBoundsException {

        ArrayList<File> files = new ArrayList<File>();
        // Specify the deletion of a file by identifying it by its file_token
        files.add(new DestroyFile(null,fileToken));

        // Create a payload without specifying a custom callback URL
        DestroyPayload payload = new DestroyPayload(SandCage.getKey(),files);
        // Create a payload specifying a custom callback URL
        // DestroyPayload payload = new DestroyPayload(SandCage.getKey(),files,
        //     "http://YOUR_CALLBACK_URL/PATH?QUERY_PART");

        // Dispatch the request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.destroyFiles();
    }

    private static void destroyMultipleAssets(ArrayList<File> files) 
            throws OutOfBoundsException {

        // Create a payload without specifying a custom callback URL
        DestroyPayload payload = new DestroyPayload(SandCage.getKey(),files);
        // Create a payload specifying a custom callback URL
        // DestroyPayload payload = new DestroyPayload(SandCage.getKey(),files,
        //     "http://YOUR_CALLBACK_URL/PATH?QUERY_PART");

        // Dispatch the request/payload to the SandCage API
        SandCage sandcage = new SandCage(payload);
        sandcage.destroyFiles();
    }
}