package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.RequestLoginDTO;
import com.example.uberapp_tim22.DTO.ResponseLoginDTO;
import com.example.uberapp_tim22.DTO.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @GET(ServiceUtils.user + "/{id}/block")
    Call<UserDTO> blockUser(@Path("id") String id);

    @POST(ServiceUtils.user + "/login")
    Call<ResponseLoginDTO> login(@Body RequestLoginDTO loginDTO);

}
