package Admin;

import java.util.*;

public class DC_DocsToRequest {

    private String docsToReq;
    private ArrayList<DC_Requirements> required = new ArrayList<>();

    public DC_DocsToRequest(String docsToReq) {
        this.docsToReq = docsToReq;
    }

    public String getDocsToReq() {
        return docsToReq;
    }

    public ArrayList<DC_Requirements> getRequired() {
        return required;
    }

    public void addRequirement(DC_Requirements requirement) {
        required.add(requirement);
    }
}