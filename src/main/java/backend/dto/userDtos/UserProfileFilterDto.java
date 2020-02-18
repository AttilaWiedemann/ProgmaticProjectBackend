package backend.dto.userDtos;

import backend.enums.Gender;


public class UserProfileFilterDto {

    private int minAge;
    private int maxAge;
    private Gender lookingFor;
    private int numberPage;
    final int numberSize = 5;


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

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getNumberSize() {
        return numberSize;
    }
}
