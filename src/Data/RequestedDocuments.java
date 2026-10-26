package Data;

import java.util.*;

public class RequestedDocuments {

    private int requestId;
    private int requesterId;
    private String document;
    private String purpose;
    private String status;
    private int counterID;
    private String counterName;

    private ArrayList<RequesterRequiredDocu> attachment = new ArrayList<>();

    public RequestedDocuments() {
    }

    public RequestedDocuments(int requestId, int requesterId, String document,
            String purpose, String status) {
        this.requestId = requestId;
        this.requesterId = requesterId;
        this.document = document;
        this.purpose = purpose;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<RequesterRequiredDocu> getAttachment() {
        return attachment;
    }

    public void addAttachment(RequesterRequiredDocu attachments) {
        attachment.add(attachments);
    }

    public int getCounterID() {
        return counterID;
    }

    public void setCounterID(int counterID) {
        this.counterID = counterID;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
    
    
}
