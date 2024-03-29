package com.example.uberapp_tim22.DTO;

import com.example.uberapp_tim22.model.User;

import java.util.Date;

public class PanicDTO{
    private Long id;
    private UserDTO user;
    private PanicRideDTO ride;
    private Date time;
    private String reason;

    public PanicDTO(Long id, UserDTO user, PanicRideDTO ride, Date time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PanicRideDTO getRide() {
        return ride;
    }

    public void setRide(PanicRideDTO ride) {
        this.ride = ride;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
