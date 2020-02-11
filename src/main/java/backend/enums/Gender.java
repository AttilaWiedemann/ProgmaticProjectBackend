package backend.enums;

public enum Gender {
    MAN(1),WOMAN(2);


    private final int value;


    private Gender(int value) {
        this.value = value;
    }
}
