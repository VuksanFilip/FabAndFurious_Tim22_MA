package com.example.uberapp_tim22.service;

import com.example.uberapp_tim22.DTO.ChangePasswordDTO;
import com.example.uberapp_tim22.DTO.LoginDTO;
import com.example.uberapp_tim22.DTO.LoginResponseDTO;
import com.example.uberapp_tim22.DTO.MessageDTO;
import com.example.uberapp_tim22.DTO.SendMessageDTO;
import com.example.uberapp_tim22.DTO.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserService {

    @Headers(
            {"User-Agent: Mobile-Android",
                    "Content-Type:application/json"}
    )

    @PUT(ServiceUtils.user + "/{id}/changePassword")
    Call<String> changePassword(@Path("id") String id, @Body ChangePasswordDTO changePasswordDTO);

    @GET(ServiceUtils.user + "/email")
    Call<UserDTO> findByEmail(@Query("email") String email);

//    @GET(ServiceUtils.user + "/admin-user")
//    Call<Long> getAdminId(); //?
    @GET(ServiceUtils.user + "/{id}/user")
    Call<UserDTO> findById(@Path("id") String id);

    @POST(ServiceUtils.user + "/{id}/message")
    Call<MessageDTO> sendMessage(@Path("id") String id, @Body SendMessageDTO sendMessageDTO);

    @GET(ServiceUtils.user + "/{id}/message")
    Call<Paginated<MessageDTO>> getMessages(@Path("id") String id);

    @POST(ServiceUtils.user + "/login")
    Call<LoginResponseDTO> login(@Body LoginDTO loginDTO);

}
