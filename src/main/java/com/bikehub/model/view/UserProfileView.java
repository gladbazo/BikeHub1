package com.bikehub.model.view;

import com.bikehub.model.enums.UserRoleEnum;

import java.util.Set;

public class UserProfileView {

    private String username;

    private  String firstName;

    private  String lastName;

    private int age;

    private String email;

    private Set<UserRoleEnum> roleEnums;

    public UserProfileView() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserRoleEnum> getRoleEnums() {
        return roleEnums;
    }

    public void setRoleEnums(Set<UserRoleEnum> roleEnums) {
        this.roleEnums = roleEnums;
    }
}
