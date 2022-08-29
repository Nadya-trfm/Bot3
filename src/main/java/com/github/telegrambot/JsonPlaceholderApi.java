package com.github.telegrambot;

import com.github.telegrambot.request.CopyMessageRequest;
import com.github.telegrambot.response.UpdateResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderApi {

    @GET("getUpdates")
    Call<UpdateResponse> getUpdates(@Query("offset") long offset);

    @GET("sendMessage")
    Call<CopyMessageRequest> sendMessage(@Query("chat_id") long chat_id,
                                         @Query("text") String text);
}
