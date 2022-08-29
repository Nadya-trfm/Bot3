package com.github.telegrambot;

import com.github.telegrambot.response.UpdateResponse;
import com.github.telegrambot.response.UpdateResponse.Result;
import lombok.val;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        TelegramApi api = TelegramService.getInstance().getJSONApi();
        long offset = 0;

        while (true) {
            try {
                UpdateResponse updateResponse = api.getUpdates(offset).execute().body();

                ArrayList<Result> results = updateResponse.result;

                if (results.size() != 0) {
                    offset = results.get(results.size() - 1).update_id + 1;
                    for (Result update : results) {
                        System.out.println(update);
                        long id = update.message.chat.id;
                        val message = api.sendMessage(id, update.message.text).execute();
                        System.out.println(message);
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
