package backend.dto;

import backend.enums.*;

import java.time.LocalDate;

public class UserProfileWithVisibleFields {

    private long id;
    private String name;
    private int age;    //TODO kiszámolni a születési dátumból
    private String aboutMe;
    private String city;
    private double height;
    private Enum<BodyShape> bodyShape;
    private Enum<Gender> gender;
    private Enum<Interest> interest;
    private LocalDate birthDate;
    private int minAge;
    private int maxAge;
    private Enum<EyeColor> eyeColor;
    private Enum<HairColor> hairColor;
    private Enum<Horoscope> horoscopeEnum;   //TODO Horoszkóp kiszámolása konstruktorban
    private boolean isSmoking;
    private String imgUrl;
    private boolean likesMovies;
    private boolean likesSports;
    private boolean likesMusic;
    private boolean likesBooks;
    private boolean likesCulture;
    private boolean likesTravels;
    private boolean likesPolitics;

    public UserProfileWithVisibleFields(){}
    public UserProfileWithVisibleFields(LocalDate birthDate){
        long years = java.time.temporal.ChronoUnit.YEARS.between( birthDate , LocalDate.now() );
        this.horoscopeEnum = Horoscope.HoroscopeFromBirthDate(birthDate);
    }


    //A konstruktor az kort és a horoszkópot a születési dátumból számolja ki
    public UserProfileWithVisibleFields(long id, String name, LocalDate birthDate, String aboutMe, String city, int height,
                                        Enum<BodyShape> bodyShape, Enum<EyeColor> eyeColor, Enum<HairColor> hairColor,
                                        Enum<Gender> gender, Enum<Interest> interest,
                                        boolean isSmoking, String imgUrl,
                                        boolean likesMovies, boolean likesSports, boolean likesMusic, boolean likesBooks,
                                        boolean likesCulture, boolean likesTravels, boolean likesPolitics,
                                        int minAge, int maxAge) {
        this.id = id;
        this.name = name;
        long years = java.time.temporal.ChronoUnit.YEARS.between( birthDate , LocalDate.now() );
        this.age = (int)years;
        this.aboutMe = aboutMe;
        this.city = city;
        this.height = height;
        this.bodyShape = bodyShape;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.horoscopeEnum = Horoscope.HoroscopeFromBirthDate(birthDate);
        this.isSmoking = isSmoking;
        this.imgUrl = imgUrl;
        this.likesMovies = likesMovies;
        this.likesSports = likesSports;
        this.likesMusic = likesMusic;
        this.likesBooks = likesBooks;
        this.likesCulture = likesCulture;
        this.likesTravels = likesTravels;
        this.likesPolitics = likesPolitics;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.birthDate = birthDate;
        this.gender = gender;
        this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public Enum<BodyShape> getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(Enum<BodyShape> bodyShape) {
        this.bodyShape = bodyShape;
    }

    public Enum<EyeColor> getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Enum<EyeColor> eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Enum<HairColor> getHairColor() {
        return hairColor;
    }

    public void setHairColor(Enum<HairColor> hairColor) {
        this.hairColor = hairColor;
    }

    public Enum<Horoscope> getHoroscopeEnum() {
        return horoscopeEnum;
    }

    public void setHoroscopeEnum(Enum<Horoscope> horoscopeEnum) {
        this.horoscopeEnum = horoscopeEnum;
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isLikesMovies() {
        return likesMovies;
    }

    public void setLikesMovies(boolean likesMovies) {
        this.likesMovies = likesMovies;
    }

    public boolean isLikesSports() {
        return likesSports;
    }

    public void setLikesSports(boolean likesSports) {
        this.likesSports = likesSports;
    }

    public boolean isLikesMusic() {
        return likesMusic;
    }

    public void setLikesMusic(boolean likesMusic) {
        this.likesMusic = likesMusic;
    }

    public boolean isLikesBooks() {
        return likesBooks;
    }

    public void setLikesBooks(boolean likesBooks) {
        this.likesBooks = likesBooks;
    }

    public boolean isLikesCulture() {
        return likesCulture;
    }

    public void setLikesCulture(boolean likesCulture) {
        this.likesCulture = likesCulture;
    }

    public boolean isLikesTravels() {
        return likesTravels;
    }

    public void setLikesTravels(boolean likesTravels) {
        this.likesTravels = likesTravels;
    }

    public boolean isLikesPolitics() {
        return likesPolitics;
    }

    public void setLikesPolitics(boolean likesPolitics) {
        this.likesPolitics = likesPolitics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Enum<Gender> getGender() {
        return gender;
    }

    public void setGender(Enum<Gender> gender) {
        this.gender = gender;
    }

    public Enum<Interest> getInterest() {
        return interest;
    }

    public void setInterest(Enum<Interest> interest) {
        this.interest = interest;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

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
}
