package com.github.telegrambot;

import com.github.telegrambot.response.CopyMessageResponse;
import com.github.telegrambot.response.UpdateResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TelegramApi {

    @GET("getUpdates")
    Call<UpdateResponse> getUpdates(@Query("offset") long offset);

    @GET("sendMessage")
    Call<CopyMessageResponse> sendMessage(@Query("chat_id") long chat_id,
                                          @Query("text") String text);
}

