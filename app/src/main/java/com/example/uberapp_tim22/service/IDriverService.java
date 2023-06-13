package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.DriverActivityDTO;
import com.example.uberapp_tim22.DTO.DriverDTO;
import com.example.uberapp_tim22.DTO.DriverRideDTO;
import com.example.uberapp_tim22.model.Driver;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IDriverService {
    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )
    @GET(ServiceUtils.driver + "/{id}") //Get driver details
    Call<Driver> getDriver(@Path("id") String id);

    @PUT(ServiceUtils.driver + "/{id}") //Update existing driver
    Call<DriverDTO> updateDriver(@Path("id") String id, @Body DriverDTO driverDTO);

    @GET(ServiceUtils.driver + "/{id}/ride")
    Call<Paginated<DriverRideDTO>> getRides(@Path("id") String id,
                                            @Query("page") int page,
                                            @Query("size") int size,
                                            @Query("sort") String sort);

    @POST(ServiceUtils.driver + "/{id}/activity")
    Call<String> changeActivity(@Path("id") String id, @Body DriverActivityDTO driverActivityDTO);

//    @POST(ServiceUtils.driver + "/{id}/activity")
//    Call<String> changeActivity(@Path("id") String id, @Body DriverActivityDTO driverActivityDTO); cemu ovo


}
