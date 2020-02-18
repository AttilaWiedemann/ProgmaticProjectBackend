package backend.dto.userDtos;

import backend.enums.*;

import java.time.LocalDate;

public class UserProfileWithVisibleFields {

    private long id;
    private String name;
    private int age;    //TODO kiszámolni a születési dátumból
    private String aboutMe;
    private String city;
    private double height;
    private String bodyShape;
    private Gender gender;
    private String interest;
    private LocalDate birthDate;
    private int minAge;
    private int maxAge;
    private String eyeColor;
    private String hairColor;
    private String horoscopeEnum;   //TODO Horoszkóp kiszámolása konstruktorban
    private boolean isSmoking;
    private String imgUrl;
    private boolean likesMovies;
    private boolean likesSports;
    private boolean likesMusic;
    private boolean likesTechnology;
    private boolean likesBooks;
    private boolean likesCulture;
    private boolean likesTravels;
    private boolean likesPolitics;

    public UserProfileWithVisibleFields(){}
    public UserProfileWithVisibleFields(LocalDate birthDate){
        long years = java.time.temporal.ChronoUnit.YEARS.between( birthDate , LocalDate.now() );
        this.horoscopeEnum = Horoscope.HoroscopeFromBirthDate(birthDate).toString();
    }


    //A konstruktor az kort és a horoszkópot a születési dátumból számolja ki
    public UserProfileWithVisibleFields(long id, String name, LocalDate birthDate, String aboutMe, String city, int height,
                                        Enum<BodyShape> bodyShape, Enum<EyeColor> eyeColor, Enum<HairColor> hairColor,
                                        Gender gender, Enum<Interest> interest,
                                        boolean isSmoking, String imgUrl,
                                        boolean likesMovies, boolean likesSports, boolean likesMusic, boolean likesBooks,
                                        boolean likesCulture, boolean likesTravels, boolean likesPolitics,
                                        int minAge, int maxAge, boolean likesTechnology) {
        this.id = id;
        this.name = name;
        long years = java.time.temporal.ChronoUnit.YEARS.between( birthDate , LocalDate.now() );
        this.age = (int)years;
        this.aboutMe = aboutMe;
        this.city = city;
        this.height = height;
        this.bodyShape = bodyShape.toString();
        this.eyeColor = eyeColor.toString();
        this.hairColor = hairColor.toString();
        this.horoscopeEnum = Horoscope.HoroscopeFromBirthDate(birthDate).toString();
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
        this.interest = interest.toString();
        this.likesTechnology = likesTechnology;
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

    public String getBodyShape() {
        return bodyShape;
    }

    public void setBodyShape(String bodyShape) {
        this.bodyShape = bodyShape;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getHoroscopeEnum() {
        return horoscopeEnum;
    }

    public void setHoroscopeEnum(String horoscopeEnum) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
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

    public boolean isLikesTechnology() {
        return likesTechnology;
    }

    public void setLikesTechnology(boolean likesTechnology) {
        this.likesTechnology = likesTechnology;
    }
}
