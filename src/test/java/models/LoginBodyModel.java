package models;

import lombok.Data;

public @Data class LoginBodyModel {
    private String email, password;
}
