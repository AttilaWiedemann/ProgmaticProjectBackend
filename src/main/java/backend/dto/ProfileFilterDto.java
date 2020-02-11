package backend.dto;

public class ProfileFilterDto {

    private int minAge;
    private int maxAge;
    private String lookingFor;  //TODO ez alapján enumot lehet majd csinálni
    private int statingNumber;
    private int endingNumber;

    public ProfileFilterDto(int minAge, int maxAge, String lookingFor, int statingNumber, int endingNumber) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.lookingFor = lookingFor;
        this.statingNumber = statingNumber;
        this.endingNumber = endingNumber;
    }

    public ProfileFilterDto(){}

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public int getStatingNumber() {
        return statingNumber;
    }

    public void setStatingNumber(int statingNumber) {
        this.statingNumber = statingNumber;
    }

    public int getEndingNumber() {
        return endingNumber;
    }

    public void setEndingNumber(int endingNumber) {
        this.endingNumber = endingNumber;
    }
}
