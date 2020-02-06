package backend.enums;

public enum HairColor {
    BLOND, BROWN, BLACK, RED, OTHER;


    public static HairColor FromInt(int x){
        switch (x){
            case 1:
                return BLOND;
            case 2:
                return BROWN;
            case 3:
                return BLACK;
            case 4:
                return RED;
            default:
                return OTHER;
        }
    }

}
