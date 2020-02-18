package backend.dto.userDtos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
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
}
