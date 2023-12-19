package com.bikehub.model.dto;

import com.bikehub.model.validator.UniqueUsername;

import javax.validation.constraints.*;

public class UserRegisterDTO {

    @NotNull(message = "Username must be between 3 and 20 characters.")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    @UniqueUsername(message = "Username should be unique.")
    private String username;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email
    private String email;

    @NotNull(message = "Age is required.")
    @Positive
    private int age;

    @NotNull(message = "Password is required.")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters.")
    private String password;

    @NotBlank(message = "Confirmation of password is required.")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters.")
    private String confirmPassword;

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
