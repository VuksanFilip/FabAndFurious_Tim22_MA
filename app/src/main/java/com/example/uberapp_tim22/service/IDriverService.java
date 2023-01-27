package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.DriverDTO;
import com.example.uberapp_tim22.DTO.PassengerDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IDriverService {
    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )
    @GET(ServiceUtils.driver + "/{id}")
    Call<DriverDTO> getDriver(@Path("id") String id);

//    @GET(ServiceUtils.driver + "/{id}/rides")
//    Call<Paginated<DriverRideDTO>> getRides(@Path("id") String id, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);

//    @POST(ServiceUtils.driver + "/{id}/activity")
//    Call<String> changeActivity(@Path("id") String id, @Body DriverActivityDTO driverActivityDTO); cemu ovo


}
