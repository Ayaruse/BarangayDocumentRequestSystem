package Data;

import java.io.*;
import java.util.*;
import java.time.*;

public class UserDataSaver {

    public static void saveCounters(ArrayList<CounterDetails> counters) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter("counters.txt"))) {
            for (int i = 0; i < counters.size(); i++) {
                CounterDetails counter = counters.get(i);

                write.write(
                        counter.getUserName() + "|"
                        + counter.getPassword() + "|"
                        + counter.getRole() + "|"
                        + counter.getCounterUserId() + "|"
                        + counter.getStatus() + "|"
                        + counter.getSecurity() + "|"
                        + counter.getFailedAttempts() + "|"
                        + counter.getFName() + "|"
                        + counter.getMName() + "|"
                        + counter.getLName() + "|"
                        + counter.getSuffix());
                write.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCounters(ArrayList<CounterDetails> counters) {
        try (BufferedReader read = new BufferedReader(new FileReader("counters.txt"))) {
            String line;
            counters.clear();

            while ((line = read.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length != 11) {
                    continue;
                }

                CounterDetails counter = new CounterDetails(
                        data[0],
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        data[4],
                        data[7],
                        data[8],
                        data[9],
                        data[10]);

                counter.setSecurity(data[5]);
                counter.setFailedAttempts(Integer.parseInt(data[6]));
                counters.add(counter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRequesters(ArrayList<RequesterDetails> requesters) {
        try (BufferedWriter write = new BufferedWriter(new FileWriter("requesters.txt"))) {
            for (int i = 0; i < requesters.size(); i++) {
                RequesterDetails user = requesters.get(i);

                write.write(
                        user.getUserName() + "|"
                        + user.getPassword() + "|"
                        + user.getRole() + "|"
                        + user.getUserID() + "|"
                        + user.getDateCreated() + "|"
                        + user.getStatus() + "|"
                        + user.getSecurity() + "|"
                        + user.getFailedAttempts() + "|"
                        + user.getFName() + "|"
                        + user.getMName() + "|"
                        + user.getLName() + "|"
                        + user.getSuffix() + "|"
                        + user.getContactNum() + "|"
                        + user.getHouseBuildingNoStrt() + "|"
                        + user.getBaranggay() + "|"
                        + user.getCity() + "|"
                        + user.getProvince() + "|"
                        + user.getBirtMonth() + "|"
                        + user.getBirtDay() + "|"
                        + user.getBirtYear() + "|"
                        + user.getFrontID() + "|"
                        + user.getBackID() + "|"
                        + user.getBrthCerti());
                write.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRequesters(ArrayList<RequesterDetails> requesters) {
        try (BufferedReader read = new BufferedReader(new FileReader("requesters.txt"))) {
            String line;
            requesters.clear();

            while ((line = read.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length != 23) {
                    continue;
                }

                RequesterDetails user = new RequesterDetails(
                        data[0],
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        LocalDateTime.parse(data[4]),
                        data[5],
                        data[8],
                        data[9],
                        data[10],
                        data[11],
                        data[12],
                        data[13],
                        data[14],
                        data[15],
                        data[16],
                        Integer.parseInt(data[17]),
                        Integer.parseInt(data[18]),
                        Integer.parseInt(data[19]),
                        data[20],
                        data[21],
                        data[22]);

                user.setSecurity(data[6]);
                user.setFailedAttempts(Integer.parseInt(data[7]));
                requesters.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRequests(ArrayList<RequestedDocuments> requests) {
        try (BufferedWriter write = new BufferedWriter(
                new FileWriter("documentRequests.txt"))) {

            for (int i = 0; i < requests.size(); i++) {
                RequestedDocuments request = requests.get(i);

                write.write(
                        request.getRequestId() + "|"
                        + request.getRequesterId() + "|"
                        + request.getDocument().replace("|", "/") + "|"
                        + request.getPurpose().replace("|", "/") + "|"
                        + request.getStatus() + "|"
                        + request.getCounterID() + "|"
                        + (request.getCounterName() == null
                        ? "" : request.getCounterName().replace("|", "/"))
                );

                write.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadRequests(ArrayList<RequestedDocuments> requests) {
        try (BufferedReader read = new BufferedReader(
                new FileReader("documentRequests.txt"))) {

            String line;
            requests.clear();

            while ((line = read.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length != 5 && data.length != 7) {
                    continue;
                }

                RequestedDocuments request = new RequestedDocuments(
                        Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]),
                        data[2],
                        data[3],
                        data[4]
                );

                if (data.length == 7) {
                    request.setCounterID(Integer.parseInt(data[5]));
                    request.setCounterName(data[6]);
                }

                requests.add(request);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
