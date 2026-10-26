package Admin;
import Data.AdminDetails;

public class AdminMain {
    public static AdminDetails admin;
    
    public static void main(String[] args) {

        admin = new AdminDetails();
        new AdminLogin();
    }
}
