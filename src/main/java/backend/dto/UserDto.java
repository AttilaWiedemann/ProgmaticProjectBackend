package backend.dto;

import backend.enums.Gender;
import backend.enums.Intrest;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class UserDto {
    @NotBlank
    @NotEmpty
    private String name;
    @NotBlank
    @NotEmpty
    private String password;
    @Email
    private String email;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birthDate;

    //private Gender gender;
    //private Intrest intrest;

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    /*
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Intrest getIntrest() {
        return intrest;
    }

    public void setIntrest(Intrest intrest) {
        this.intrest = intrest;
    }*/
}
