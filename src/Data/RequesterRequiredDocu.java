package Data;

public class RequesterRequiredDocu {

    private String requirementName;
    private String filePath;

    public RequesterRequiredDocu() {
    }

    public RequesterRequiredDocu(String requirementName, String filePath) {
        this.requirementName = requirementName;
        this.filePath = filePath;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
