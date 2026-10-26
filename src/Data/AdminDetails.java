package Data;

public class AdminDetails {

    private String username;
    private String password;
    private String serverStats;

    public AdminDetails() {
        username = "BarangayAdmin";
        password = "AdminUser1234";
        serverStats = "Running";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getServerStatus() {
        return serverStats;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStats = serverStatus;
    }
}