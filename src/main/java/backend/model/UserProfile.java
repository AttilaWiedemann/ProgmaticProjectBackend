package backend.model;

import backend.enums.BodyShape;
import backend.enums.EyeColor;
import backend.enums.HairColor;
import backend.enums.Horoscope;

import javax.persistence.*;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String aboutMe;

    private String city;

    private double height;

    private Enum bodyShape;

    private Enum eyeColor;

    private Enum hairColor;

    private Enum horoscope;

    private boolean smoking;

    @OneToOne
    private Image profileImage;

    @OneToOne
    private User user;

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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

    public UserProfile(String aboutMe, String city, double height, Enum<BodyShape> bodyShape, Enum<EyeColor> eyeColor,
                       Enum<HairColor> hairColor, Enum<Horoscope> horoscope, boolean isSmoking){
        this.aboutMe = aboutMe;
        this.city = city;
        this.height = height;
        this.bodyShape = bodyShape;
        this.hairColor = hairColor;
        this.horoscope = horoscope;
        this.smoking = isSmoking;
    }
}
