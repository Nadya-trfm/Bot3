package com.github.telegrambot;

import com.github.telegrambot.response.CopyMessageResponse;
import com.github.telegrambot.response.PhotoResponse;
import com.github.telegrambot.response.UpdateResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface TelegramApi {

    @GET("getUpdates")
    Call<UpdateResponse> getUpdates(@Query("offset") long offset);

    @GET("sendMessage")
    Call<CopyMessageResponse> sendMessage(@Query("chat_id") long chat_id,
                                          @Query("text") String text);

    @GET("getFile")
    Call<PhotoResponse> getFile(@Query("file_id") String file_id);
    @Multipart
    @POST("sendPhoto")
    Call<String> sendPhoto(@Query("chat_id") long chat_id,
                           @Part MultipartBody.Part photo);

}

