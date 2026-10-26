package Admin;

import java.util.ArrayList;
import java.util.Collections;

public class BlockedSearch {

    public int binarySearch(ArrayList<Integer> userIDs, int targetID) {
        Collections.sort(userIDs);

        int left = 0;
        int right = userIDs.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int currentID = userIDs.get(mid);

            if (currentID == targetID) {
                return mid;
            }

            if (currentID < targetID) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}
