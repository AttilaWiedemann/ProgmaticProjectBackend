package backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private boolean movies;
    private boolean sports;
    private boolean music;
    private boolean books;
    private boolean culture;
    private boolean travels;
    private boolean technology;
    private boolean politics;

    public UserInterest(boolean movies, boolean sports, boolean music,
                        boolean books, boolean culture,
                        boolean travels, boolean technology, boolean politics) {
        this.movies = movies;
        this.sports = sports;
        this.music = music;
        this.books = books;
        this.culture = culture;
        this.travels = travels;
        this.technology = technology;
        this.politics = politics;
    }

    public UserInterest(){}

    public boolean isMovies() {
        return movies;
    }

    public void setMovies(boolean movies) {
        this.movies = movies;
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isBooks() {
        return books;
    }

    public void setBooks(boolean books) {
        this.books = books;
    }

    public boolean isCulture() {
        return culture;
    }

    public void setCulture(boolean culture) {
        this.culture = culture;
    }

    public boolean isTravels() {
        return travels;
    }

    public void setTravels(boolean travels) {
        this.travels = travels;
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
