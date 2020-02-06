package backend.enums;

public enum EyeColor {
    BROWN, BLUE, GREEN;

    public static EyeColor FromInt(int x){
        switch (x){
            case 1:
                return BROWN;
            case 2:
                return BLUE;
            case 3:
                return GREEN;
            default:
                return null;
        }
    }

}
