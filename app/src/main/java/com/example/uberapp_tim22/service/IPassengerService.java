package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.PassengerDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPassengerService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )
    @GET(ServiceUtils.passenger + "/{id}") //Passenger details
    Call<PassengerDTO> getPassenger(@Path("id") String id);

    @PUT(ServiceUtils.passenger + "/{id}") //Update existing passenger
    Call<PassengerDTO> updatePassenger(@Path("id") String id, @Body PassengerDTO passengerDTO);

//    @GET(ServiceUtils.passenger + "/{id}/rides") //Passenger rides
//    Call<Paginated<PassengerRideDTO>> getPassengerRides(@Path("id") String id, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);
//
//    @POST(ServiceUtils.passenger) //Create passenger
//    Call<CreatePassengerDTO> createPassenger(@Body CreatePassengerDTO createPassengerDTO);

//    @GET(ServiceUtils.passenger + "/activate/{activationId}") //Activate passenger account
//    Call<> activatePassenger(@Path("id") String id);

//    @GET(ServiceUtils.passenger) //Getting passengers ???
//    Call<Paginated<PassengerDTO> getPassengers(@Query("page") int page, @Query("size") int size);


}
