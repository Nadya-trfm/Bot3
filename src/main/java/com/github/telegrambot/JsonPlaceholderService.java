package com.github.telegrambot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.github.telegrambot.ENV.TOKEN;

public class JsonPlaceholderService {
    private Retrofit retrofit;
    private static JsonPlaceholderService instance;
    private static final String BASE_URL="https://api.telegram.org/bot"+TOKEN+"/";
    private JsonPlaceholderService(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static  JsonPlaceholderService getInstance(){
        if(instance == null){
            instance = new JsonPlaceholderService();
        }
        return instance;
    }

    public JsonPlaceholderApi getJSONApi()
    {
        return retrofit.create(JsonPlaceholderApi.class);
    }
}
