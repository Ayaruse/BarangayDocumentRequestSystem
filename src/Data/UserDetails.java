
package Data;

public class UserDetails {

    private String userName;
    private String password;
    private String role;

    private String FName;
    private String MName;
    private String LName;
    private String suffix;

    public UserDetails(String userName, String password, String role,
            String FName, String MName, String LName, String suffix) {

        this.userName = userName;
        this.password = password;
        this.role = role;
        this.FName = FName;

        if (MName == null) {
            this.MName = "";
        } else {
            this.MName = MName;
        }

        this.LName = LName;

        if (suffix == null) {
            this.suffix = "";
        } else {
            this.suffix = suffix;
        }
    }

    public String getFullName() {
        String fulname;

        if (!MName.isEmpty() && !suffix.isEmpty()) {
            fulname = FName + " " + MName.charAt(0)
                    + ". " + LName + " " + suffix;
        } else if (!MName.isEmpty()) {
            fulname = FName + " " + MName.charAt(0)
                    + ". " + LName;
        } else if (!suffix.isEmpty()) {
            fulname = FName + " " + LName + " " + suffix;
        } else {
            fulname = FName + " " + LName;
        }

        return fulname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        if (MName == null) {
            this.MName = "";
        } else {
            this.MName = MName;
        }
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            this.suffix = "";
        } else {
            this.suffix = suffix;
        }
    }
}
