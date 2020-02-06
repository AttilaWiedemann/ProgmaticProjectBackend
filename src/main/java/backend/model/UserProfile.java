package backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String aboutMe;

    private String city;

    private int height;

    private Enum bodyShape;

    private Enum eyeColor;

    private Enum hairColor;

    private Enum horoscope;

    private boolean smoking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Enum getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(Enum bodyShape) {
        this.bodyShape = bodyShape;
    }

    public Enum getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Enum eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Enum getHairColor() {
        return hairColor;
    }

    public void setHairColor(Enum hairColor) {
        this.hairColor = hairColor;
    }

    public Enum getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(Enum horoscope) {
        this.horoscope = horoscope;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public UserProfile() {
    }
}
