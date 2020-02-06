package backend.dto;

public class UserProfileDto {
    private String aboutMe;

    private String city;

    private int height;

    private Enum bodyShape;

    private Enum eyeColor;

    private Enum hairColor;

    private Enum horoscope;

    private boolean smoking;

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public UserProfileDto() {
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
}
