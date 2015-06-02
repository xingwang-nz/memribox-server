package nz.co.xingsoft.memribox.server.business.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import nz.co.xingsoft.memribox.server.common.Gender;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class RegistrationRequest
        implements Serializable {

    private static final long serialVersionUID = -6342208738961541621L;

    @NotBlank(message = "username is blank")
    private String username;

    @NotBlank(message = "password is blank")
    private String password;

    @NotBlank(message = "firstName is blank")
    private String firstName;

    @NotBlank(message = "lastName is blank")
    private String lastName;

    @Email(message = "invalid email format")
    private String email;

    @NotNull(message = "birthDay is not provided")
    private Date birthDate;

    @NotNull(message = "gender is not provided")
    private Gender gender;

    private String nativeTongue;

    private String homeCountry;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public String getNativeTongue() {
        return nativeTongue;
    }

    public void setNativeTongue(final String nativeTongue) {
        this.nativeTongue = nativeTongue;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(final String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

}
