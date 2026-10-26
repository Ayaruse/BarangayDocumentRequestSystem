package Data;

import java.util.*;
import java.time.*;

public class RequesterDetails extends UserDetails {

    private int userID;
    private LocalDateTime dateCreated;
    private String status;
    private String security;
    private int failedAttempts;

    private String contactNum;
    private String fullAddress;

    private String houseBuildingNoStrt;
    private String baranggay;
    private String city;
    private String province;

    private int age;
    private int birtMonth;
    private int birtDay;
    private int birtYear;

    private String frontID;
    private String backID;
    private String brthCerti;

    private ArrayList<RequestedDocuments> documents = new ArrayList<>();

    public RequesterDetails(String userName, String password, String role,
            int userID, LocalDateTime dateCreated, String status,
            String FName, String MName, String LName, String suffix,
            String contactNum, String houseBuildingNoStrt,
            String baranggay, String city, String province,
            int birtMonth, int birtDay, int birtYear,
            String frontID, String backID, String brthCerti) {

        super(userName, password, role, FName, MName, LName, suffix);

        this.userID = userID;
        this.dateCreated = dateCreated;
        this.status = status;
        this.security = "UNVERIFIED";
        this.failedAttempts = 0;
        this.contactNum = contactNum;
        setFullAddress(houseBuildingNoStrt, baranggay, city, province);

        this.birtMonth = birtMonth;
        this.birtDay = birtDay;
        this.birtYear = birtYear;
        calcAge();

        this.frontID = frontID;
        this.backID = backID;
        this.brthCerti = brthCerti;
    }

    public void setFullAddress(String houseBuildingNoStrt,
            String baranggay, String city, String province) {

        this.houseBuildingNoStrt = houseBuildingNoStrt;
        this.baranggay = baranggay;
        this.city = city;
        this.province = province;

        fullAddress = (houseBuildingNoStrt + " " + baranggay
                + " " + city + " " + province).trim();
    }

    public void calcAge() {
        LocalDate today = LocalDate.now();
        this.age = today.getYear() - this.birtYear;

        if (today.getMonthValue() < this.birtMonth
                || (today.getMonthValue() == this.birtMonth
                && today.getDayOfMonth() < this.birtDay)) {
            age--;
        }
    }

    public void addDocument(RequestedDocuments document) {
        documents.add(document);
    }

    public ArrayList<RequestedDocuments> getDocuments() {
        return documents;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getHouseBuildingNoStrt() {
        return houseBuildingNoStrt;
    }

    public void setHouseBuildingNoStrt(String houseBuildingNoStrt) {
        setFullAddress(houseBuildingNoStrt, baranggay, city, province);
    }

    public String getBaranggay() {
        return baranggay;
    }

    public void setBaranggay(String baranggay) {
        setFullAddress(houseBuildingNoStrt, baranggay, city, province);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        setFullAddress(houseBuildingNoStrt, baranggay, city, province);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        setFullAddress(houseBuildingNoStrt, baranggay, city, province);
    }

    public int getBirtMonth() {
        return birtMonth;
    }

    public void setBirtMonth(int birtMonth) {
        this.birtMonth = birtMonth;
    }

    public int getBirtDay() {
        return birtDay;
    }

    public void setBirtDay(int birtDay) {
        this.birtDay = birtDay;
    }

    public int getBirtYear() {
        return birtYear;
    }

    public void setBirtYear(int birtYear) {
        this.birtYear = birtYear;
    }

    public String getFrontID() {
        return frontID;
    }

    public void setFrontID(String frontID) {
        this.frontID = frontID;
    }

    public String getBackID() {
        return backID;
    }

    public void setBackID(String backID) {
        this.backID = backID;
    }

    public String getBrthCerti() {
        return brthCerti;
    }

    public void setBrthCerti(String brthCerti) {
        this.brthCerti = brthCerti;
    }
}
