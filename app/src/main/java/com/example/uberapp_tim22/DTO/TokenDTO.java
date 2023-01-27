package com.example.uberapp_tim22.DTO;

public class TokenDTO {

    private static TokenDTO instance;

    private String accessToken;
    private String refreshToken;

    private TokenDTO() {
    }

    public static TokenDTO getInstance() {
        if (instance == null) {
            instance = new TokenDTO();
        }
        return instance;
    }

    public String getAccessToken() {
        if(accessToken == null) return "";

        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {

        if(refreshToken == null) return "";
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
