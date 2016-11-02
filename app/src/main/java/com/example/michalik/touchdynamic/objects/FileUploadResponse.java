package com.example.michalik.touchdynamic.objects;

/**
 * Created by michalik on 30.10.16
 */

public class FileUploadResponse {
    String filename;
    String filesize;
    String creationDate;
    String type;
    String id;

    /**
     * {"filename":"acc_57bad2f0-7c78-49e6-a592-3375706c2d77.csv",
     * "filesize":8920,
     * "creationDate":1478038247651,
     * "type":"text/csv",
     * "id":"c09aea789c57ca87"}
     * @return
     */

    /**
     * [
     *  {
     *  "filename":"acc_748caa79-bb7f-4c80-b605-53649f5899e1.csv",
     *  "filesize":13717,
     *  "creationDate":1478072511307,
     *  "type":"text/csv",
     *  "id":"c636e1111cce68fe"
     *  }
     * ]
     * @return
     */

    public String getFilename() {
        return filename;
    }

    public String getType() {
        return type;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getId() {
        return id;
    }

    public String getFilesize() {
        return filesize;
    }
}
