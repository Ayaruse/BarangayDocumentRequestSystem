package Requester;

import Data.*;
import java.util.*;

public class RequesterMain {

    public static ArrayList<RequesterDetails> requesterUser = new ArrayList<>();
    public static ArrayList<RequestedDocuments> documentRequests = new ArrayList<>();
    static int nextRequestId;

    public static void main(String[] args) {
        UserDataSaver.loadRequesters(requesterUser);
        UserDataSaver.loadRequests(documentRequests);

        updateNxtReqId();

        new RequesterWelcoming();
    }

    public static void updateNxtReqId() {
        nextRequestId = 1;

        for (int i = 0; i < documentRequests.size(); i++) {
            if (documentRequests.get(i).getRequestId() >= nextRequestId) {
                nextRequestId = documentRequests.get(i).getRequestId() + 1;
            }
        }
    }
}
