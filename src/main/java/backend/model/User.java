package backend.model;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String password;

    @Column(unique=true)
    @Email
    private String email;

    private boolean enabled;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate birthDate;

    @OneToOne
    private UserProfile userProfile;

    @OneToOne
    private UserInterest userInterest;

    @OneToOne(mappedBy = "user")
    private VerificationToken token;

    public Image getProfilePicture() {
        return profilePicture;
    }

    @OneToOne
   private Image profilePicture;

//    @ManyToOne
//    private List<Image> imageList;

    public void setProfilePicture(Image profilePicture) {
      this.profilePicture = profilePicture;
  }

//    public List<Image> getImageList() {
//        return imageList;
//    }
//
//    public void setImageList(List<Image> imageList) {
//        this.imageList = imageList;
//    }

    public User() {
        this.enabled = false;
    }

    public User(@NotEmpty String name, @NotEmpty String password, @Email String email, LocalDate birthDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.enabled = false;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


    //@NotEmpty
    //@NotNull
    //@EnumNamePattern(regexp = "MAN|WOMAN")
    //private Gender gender;
    //@NotEmpty
    //@NotNull
    //@EnumNamePattern(regexp = "MAN|WOMAN|BOTH")
    //private Interest interest;

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
    }
*/

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @ManyToMany
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public UserInterest getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(UserInterest userInterest) {
        this.userInterest = userInterest;
    }

//    public void addPicture(Image image){
//        imageList.add(image);
//    }
//    public Image showPicture(int index){
//        return imageList.get(index);
//    }
}
