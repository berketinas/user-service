package com.berketinas.sm.berkemedia.model;

import java.util.UUID;

// ***
// user model, the empty constructor is required by jdbc (?), not sure
// ***
public class User {
    private UUID id;
    private String given_name;
    private String family_name;
    private String email;
    private String country;

    public User() {}

    public User(String given_name, String family_name, String email, String country) {
        this.given_name = given_name;
        this.family_name = family_name;
        this.email = email;
        this.country = country;
    }

    public User(UUID id, String given_name, String family_name, String email) {
        this.id = id;
        this.given_name = given_name;
        this.family_name = family_name;
        this.email = email;
    }

    public User(UUID id, String given_name, String family_name, String email, String country) {
        this(id, given_name, family_name, email);
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public String getGivenName() {
        return given_name;
    }

    public void setGivenName(String given_name) {
        this.given_name = given_name;
    }

    public String getFamilyName() {
        return family_name;
    }

    public void setFamilyName(String family_name) {
        this.family_name = family_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}