package backend.dto;

public class UserInterestDto {

    private boolean movie;
    private boolean sport;
    private boolean music;
    private boolean bookAndLiterature;
    private boolean culture;
    private boolean travel;
    private boolean technology;
    private boolean politics;

    public UserInterestDto(boolean movie, boolean sport, boolean music,
                           boolean bookAndLiterature, boolean culture, boolean travel,
                           boolean technology, boolean politics) {
        this.movie = movie;
        this.sport = sport;
        this.music = music;
        this.bookAndLiterature = bookAndLiterature;
        this.culture = culture;
        this.travel = travel;
        this.technology = technology;
        this.politics = politics;
    }

    public UserInterestDto(){}

    public boolean isMovie() {
        return movie;
    }

    public void setMovie(boolean movie) {
        this.movie = movie;
    }

    public boolean isSport() {
        return sport;
    }

    public void setSport(boolean sport) {
        this.sport = sport;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isBookAndLiterature() {
        return bookAndLiterature;
    }

    public void setBookAndLiterature(boolean bookAndLiterature) {
        this.bookAndLiterature = bookAndLiterature;
    }

    public boolean isCulture() {
        return culture;
    }

    public void setCulture(boolean culture) {
        this.culture = culture;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public boolean isTechnology() {
        return technology;
    }

    public void setTechnology(boolean technology) {
        this.technology = technology;
    }

    public boolean isPolitics() {
        return politics;
    }

    public void setPolitics(boolean politics) {
        this.politics = politics;
    }
}
