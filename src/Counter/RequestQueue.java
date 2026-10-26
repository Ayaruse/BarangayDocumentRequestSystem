package Counter;

import Data.*;
import java.util.ArrayList;

public class RequestQueue {

    private RequestedDocuments[] queue = new RequestedDocuments[10];
    private int front = 0;
    private int rear = -1;

    public boolean enqueue(RequestedDocuments request) {
        if (rear == queue.length - 1) {
            RequestedDocuments[] newQueue = new RequestedDocuments[queue.length * 2];

            for (int i = front; i <= rear; i++) {
                newQueue[i - front] = queue[i];
            }

            rear = rear - front;
            front = 0;
            queue = newQueue;
        }

        rear++;
        queue[rear] = request;
        return true;
    }

    public RequestedDocuments peek() {
        if (isEmpty()) {
            return null;
        }

        return queue[front];
    }

    public RequestedDocuments dequeue() {
        if (isEmpty()) {
            return null;
        }

        RequestedDocuments request = queue[front];

        for (int i = front; i < rear; i++) {
            queue[i] = queue[i + 1];
        }

        queue[rear] = null;
        rear--;

        if (rear < front) {
            front = 0;
            rear = -1;
        }

        return request;
    }

    public void syncPendingRequests(ArrayList<RequestedDocuments> requests) {
        for (int i = 0; i < requests.size(); i++) {
            RequestedDocuments request = requests.get(i);

            if (request.getStatus().equalsIgnoreCase("Pending")
                    && !contains(request.getRequestId())) {
                enqueue(request);
            }
        }
    }

    public boolean contains(int requestId) {
        for (int i = front; i <= rear; i++) {
            if (queue[i].getRequestId() == requestId) {
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty() {
        return rear < front;
    }

    public int getQueueSize() {
        if (isEmpty()) {
            return 0;
        }

        return rear - front + 1;
    }

    public boolean isNextRequest(int requestId) {
        RequestedDocuments request = peek();

        return request != null && request.getRequestId() == requestId;
    }
}
