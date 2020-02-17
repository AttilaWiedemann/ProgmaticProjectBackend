package backend.dto.userDtos;

import backend.enums.Gender;


public class UserProfileFilterDto {

    private int minAge;
    private int maxAge;
    private Gender lookingFor;  //TODO ez alapján enumot lehet majd csinálni
    private int statingNumber;
    private int endingNumber;   //nem a végszámmal, hanem a találatok számával kell majd a between lekérdezés

    public UserProfileFilterDto(int minAge, int maxAge, Gender lookingFor, int statingNumber, int endingNumber) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.lookingFor = lookingFor;
        this.statingNumber = statingNumber;
        this.endingNumber = endingNumber;
    }

    public UserProfileFilterDto(){}

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

    public Gender getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(Gender lookingFor) {
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
