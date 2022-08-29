package com.github.telegrambot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.github.telegrambot.ENV.TOKEN;

public class TelegramService {
    private Retrofit retrofit;
    private static TelegramService instance;
    private static final String BASE_URL="https://api.telegram.org/bot"+TOKEN+"/";
    private TelegramService(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static TelegramService getInstance(){
        if(instance == null){
            instance = new TelegramService();
        }
        return instance;
    }

    public TelegramApi getJSONApi()
    {
        return retrofit.create(TelegramApi.class);
    }
}
