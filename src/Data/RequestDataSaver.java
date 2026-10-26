package Data;

import java.io.*;
import java.util.*;

public class RequestDataSaver {

    private static final String REQUEST_FILE = "RequestedDocuments.txt";
    private static final String ATTACHMENT_FILE = "RequestAttachments.txt";

    public static void saveRequests(ArrayList<RequestedDocuments> requestList) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(REQUEST_FILE));

            for (int i = 0; i < requestList.size(); i++) {
                RequestedDocuments request = requestList.get(i);

                write.write(request.getRequestId() + "|"
                        + request.getRequesterId() + "|"
                        + request.getDocument() + "|"
                        + request.getPurpose() + "|"
                        + request.getStatus());

                write.newLine();
            }

            write.close();

        } catch (IOException e) {
        }

        saveAttachments(requestList);
    }

    public static void saveAttachments(ArrayList<RequestedDocuments> requestList) {
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(ATTACHMENT_FILE));

            for (int i = 0; i < requestList.size(); i++) {
                RequestedDocuments request = requestList.get(i);
                ArrayList<RequesterRequiredDocu> attachments = request.getAttachment();

                for (int j = 0; j < attachments.size(); j++) {
                    RequesterRequiredDocu attachment = attachments.get(j);

                    write.write(request.getRequestId() + "|"
                            + attachment.getRequirementName() + "|"
                            + attachment.getFilePath());

                    write.newLine();
                }
            }

            write.close();

        } catch (IOException e) {
        }
    }

    public static void loadRequests(ArrayList<RequestedDocuments> requestList) {
        requestList.clear();

        try {
            BufferedReader read = new BufferedReader(new FileReader(REQUEST_FILE));
            String line;

            while ((line = read.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 5) {
                    RequestedDocuments request = new RequestedDocuments(
                            Integer.parseInt(data[0]),
                            Integer.parseInt(data[1]),
                            data[2],
                            data[3],
                            data[4]
                    );

                    requestList.add(request);
                }
            }

            read.close();

        } catch (IOException e) {
        }

        loadAttachments(requestList);
    }

    public static void loadAttachments(ArrayList<RequestedDocuments> requestList) {
        try {
            BufferedReader read = new BufferedReader(new FileReader(ATTACHMENT_FILE));
            String line;

            while ((line = read.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 3) {
                    int requestId = Integer.parseInt(data[0]);

                    for (int i = 0; i < requestList.size(); i++) {
                        RequestedDocuments request = requestList.get(i);

                        if (request.getRequestId() == requestId) {
                            RequesterRequiredDocu attachment
                                    = new RequesterRequiredDocu(data[1], data[2]);

                            request.addAttachment(attachment);
                            break;
                        }
                    }
                }
            }

            read.close();

        } catch (IOException e) {
        }
    }
}
