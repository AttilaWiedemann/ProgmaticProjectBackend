package backend.enums;

public enum Interest {
    MAN(1),WOMAN(2),BOTH(3);

    private final int value;

    private Interest(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
