package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.PassengerDTO;
import com.example.uberapp_tim22.DTO.UnregisteredUserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUnregisteredUserService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )
    @POST(ServiceUtils.unregisteredUser) //Getting the assumption about the time and cost of the ride
    Call<UnregisteredUserDTO> getAssumption(@Body UnregisteredUserDTO unregisteredUserDTO);
}
