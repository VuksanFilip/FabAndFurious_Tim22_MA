package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.PassengerDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IPassengerService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )
    @GET(ServiceUtils.passenger + "/{id}")
    Call<PassengerDTO> getPassenger(@Path("id") String id);
}
