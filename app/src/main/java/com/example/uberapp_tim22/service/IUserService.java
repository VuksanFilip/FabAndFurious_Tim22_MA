package com.example.uberapp_tim22.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class IUserService {

    @GET(ServiceUtils.user + "/{id}/user")
    Call<UserDTO> findById(@Path("id") Long id) {
        return null;
    }

}
