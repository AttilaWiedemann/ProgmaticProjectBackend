package backend.model.userModels;

import backend.enums.*;
import backend.model.imageModels.Image;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@Entity
public class UserProfile implements Externalizable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(length=512)
    private String aboutMe;

    private String city;

    private int height;

    private BodyShape bodyShape;

    private EyeColor eyeColor;

    private HairColor hairColor;

    private Horoscope horoscope;

    private Gender gender;

    private boolean smoking;

    @OneToOne
    private Image profileImage;
/*
    @OneToOne
    private User user;
*/
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

    public void setHeight(int height) {
        this.height = height;
    }

    public BodyShape getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(BodyShape bodyShape) {
        this.bodyShape = bodyShape;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public Horoscope getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(Horoscope horoscope) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserProfile(String aboutMe, String city, int height, BodyShape bodyShape, EyeColor eyeColor,
                       HairColor hairColor, Horoscope horoscope, boolean isSmoking, Gender gender){
        this.aboutMe = aboutMe;
        this.city = city;
        this.height = height;
        this.bodyShape = bodyShape;
        this.hairColor = hairColor;
        this.horoscope = horoscope;
        this.smoking = isSmoking;
        this.gender = gender;
        this.eyeColor = eyeColor;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {

    }
}
