package backend.dto;

public class FilterDto {

    private int minAge;
    private int maxAge;
    private String lookingFor;
    private int resultsMin;
    private int resultsMax;

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

    public int getResultsMin() {
        return resultsMin;
    }

    public void setResultsMin(int resultsMin) {
        this.resultsMin = resultsMin;
    }

    public int getResultsMax() {
        return resultsMax;
    }

    public void setResultsMax(int resultsMax) {
        this.resultsMax = resultsMax;
    }
}
