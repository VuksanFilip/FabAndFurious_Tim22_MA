package com.example.uberapp_tim22.DTO;

public class NewLocationDTO {

    private NewLocationWithAddressDTO departure;
    private NewLocationWithAddressDTO destination;

    public NewLocationDTO(){

    }

    public NewLocationDTO(NewLocationWithAddressDTO departure, NewLocationWithAddressDTO destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public NewLocationWithAddressDTO getDeparture() {
        return departure;
    }

    public void setDeparture(NewLocationWithAddressDTO departure) {
        this.departure = departure;
    }

    public NewLocationWithAddressDTO getDestination() {
        return destination;
    }

    public void setDestination(NewLocationWithAddressDTO destination) {
        this.destination = destination;
    }
}
