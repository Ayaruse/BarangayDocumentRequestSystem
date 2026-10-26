
package Data;

public class CounterDetails extends UserDetails {

    private int counterUserId;
    private String status;
    private String security;
    private int failedAttempts;

    public CounterDetails(String userName, String password, String role,
            int counterUserId, String status, String FName,
            String MName, String LName, String suffix) {

        super(userName, password, role, FName, MName, LName, suffix);

        this.counterUserId = counterUserId;
        this.status = status;
        this.security = "UNVERIFIED";
        this.failedAttempts = 0;
    }

    public int getCounterUserId() {
        return counterUserId;
    }

    public void setCounterUserId(int counterUserId) {
        this.counterUserId = counterUserId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
}
