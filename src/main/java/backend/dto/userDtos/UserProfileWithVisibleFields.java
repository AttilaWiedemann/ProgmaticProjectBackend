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
    private BodyShape bodyShape;
    private Gender gender;
    private Interest interest;
    private LocalDate birthDate;
    private int minAge;
    private int maxAge;
    private EyeColor eyeColor;
    private HairColor hairColor;
    private Horoscope horoscope;   //TODO Horoszkóp kiszámolása konstruktorban
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
    private int birthYear;
    private int numberOfPages;

    public UserProfileWithVisibleFields(){}
    public UserProfileWithVisibleFields(LocalDate birthDate){
        long years = java.time.temporal.ChronoUnit.YEARS.between( birthDate , LocalDate.now() );
        this.horoscope = Horoscope.HoroscopeFromBirthDate(birthDate);
    }


    //A konstruktor az kort és a horoszkópot a születési dátumból számolja ki
    public UserProfileWithVisibleFields(long id, String name, LocalDate birthDate, String aboutMe, String city, int height,
                                        BodyShape bodyShape, EyeColor eyeColor, HairColor hairColor,
                                        Gender gender, Interest interest,
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
        this.bodyShape = bodyShape;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.horoscope = Horoscope.HoroscopeFromBirthDate(birthDate);
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
        this.likesTechnology = likesTechnology;
        this.birthYear = birthDate.getYear();
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

    public Horoscope getHoroscopeEnum() {
        return horoscope;
    }

    public void setHoroscopeEnum(Horoscope horoscopeEnum) {
        this.horoscope = horoscopeEnum;
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

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.birthYear = birthDate.getYear();
        long years = java.time.temporal.ChronoUnit.YEARS.between( birthDate , LocalDate.now() );
        this.age = (int) years;
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

    public int getBirthYear() {
        return birthYear;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
