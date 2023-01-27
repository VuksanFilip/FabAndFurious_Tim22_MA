package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.PassengerDTO;
import com.example.uberapp_tim22.DTO.VehicleDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IVehicleService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @PUT(ServiceUtils.vehicle + "/{id}/location") //Change location of the vehicle
    Call<VehicleDTO> changeLocation(@Path("id") String id, @Body VehicleDTO vehicleDTO);
}
