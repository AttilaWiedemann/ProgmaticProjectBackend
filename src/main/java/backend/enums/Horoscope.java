package backend.enums;

import java.time.LocalDate;
import java.time.Month;

public enum Horoscope {
    CAPRICORN, AQUARIUS, PISCES, ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS;

    private static boolean compareDates(LocalDate birthDate, LocalDate from, LocalDate to){
        return from.compareTo(birthDate) * birthDate.compareTo(to) >= 0;
    }

    public static Horoscope HoroscopeFromBirthDate(LocalDate birthDate){
        LocalDate startingDate = LocalDate.of(birthDate.getYear(), Month.JANUARY, 1);
        LocalDate endingDate = LocalDate.of(birthDate.getYear(), Month.JANUARY, 20);
        if(compareDates(birthDate, startingDate, endingDate))return CAPRICORN;
        startingDate = LocalDate.of(birthDate.getYear(), Month.JANUARY, 21);
        endingDate = LocalDate.of(birthDate.getYear(), Month.FEBRUARY, 19);
        if(compareDates(birthDate, startingDate, endingDate))return AQUARIUS;
        startingDate = LocalDate.of(birthDate.getYear(), Month.FEBRUARY, 20);
        endingDate = LocalDate.of(birthDate.getYear(), Month.MARCH, 20);
        if(compareDates(birthDate, startingDate, endingDate))return PISCES;
        startingDate = LocalDate.of(birthDate.getYear(), Month.MARCH, 21);
        endingDate = LocalDate.of(birthDate.getYear(), Month.APRIL, 20);
        if(compareDates(birthDate, startingDate, endingDate))return ARIES;
        startingDate = LocalDate.of(birthDate.getYear(), Month.APRIL, 21);
        endingDate = LocalDate.of(birthDate.getYear(), Month.MAY, 21);
        if(compareDates(birthDate, startingDate, endingDate))return TAURUS;
        startingDate = LocalDate.of(birthDate.getYear(), Month.MAY, 22);
        endingDate = LocalDate.of(birthDate.getYear(), Month.JUNE, 21);
        if(compareDates(birthDate, startingDate, endingDate))return GEMINI;
        startingDate = LocalDate.of(birthDate.getYear(), Month.JUNE, 22);
        endingDate = LocalDate.of(birthDate.getYear(), Month.JULY, 22);
        if(compareDates(birthDate, startingDate, endingDate))return CANCER;
        startingDate = LocalDate.of(birthDate.getYear(), Month.JULY, 23);
        endingDate = LocalDate.of(birthDate.getYear(), Month.AUGUST, 23);
        if(compareDates(birthDate, startingDate, endingDate))return LEO;
        startingDate = LocalDate.of(birthDate.getYear(), Month.AUGUST, 24);
        endingDate = LocalDate.of(birthDate.getYear(), Month.SEPTEMBER, 22);
        if(compareDates(birthDate, startingDate, endingDate))return VIRGO;
        startingDate = LocalDate.of(birthDate.getYear(), Month.SEPTEMBER, 23);
        endingDate = LocalDate.of(birthDate.getYear(), Month.OCTOBER, 23);
        if(compareDates(birthDate, startingDate, endingDate))return LIBRA;
        startingDate = LocalDate.of(birthDate.getYear(), Month.OCTOBER, 24);
        endingDate = LocalDate.of(birthDate.getYear(), Month.NOVEMBER, 22);
        if(compareDates(birthDate, startingDate, endingDate))return SCORPIO;
        startingDate = LocalDate.of(birthDate.getYear(), Month.NOVEMBER, 23);
        endingDate = LocalDate.of(birthDate.getYear(), Month.DECEMBER, 21);
        if(compareDates(birthDate, startingDate, endingDate))return SAGITTARIUS;
        startingDate = LocalDate.of(birthDate.getYear(), Month.DECEMBER, 22);
        endingDate = LocalDate.of(birthDate.getYear(), Month.DECEMBER, 31);
        if(compareDates(birthDate, startingDate, endingDate))return CAPRICORN;
        return null;
    }
}
