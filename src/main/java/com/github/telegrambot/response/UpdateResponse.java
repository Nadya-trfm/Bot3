package com.github.telegrambot.response;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
public class UpdateResponse {
    @ToString
    @Getter
    public class Chat{
        public int id;
        public String first_name;
        public String last_name;
        public String username;
        public String type;
    }

    @ToString
    @Getter
    public class From{
        public int id;
        public boolean is_bot;
        public String first_name;
        public String last_name;
        public String username;
        public String language_code;
    }

    @ToString
    @Getter
    public class Message{
        public int message_id;
        public From from;
        public Chat chat;
        public int date;
        public String text;
        public ArrayList<Photo> photo;
    }

    @ToString
    @Getter
    public class Photo{
        public String file_id;
        public String file_unique_id;
        public int file_size;
        public int width;
        public int height;
    }
    @ToString
    @Getter
    public class Result{
        public int update_id;
        public Message message;
    }



        public boolean ok;
        public ArrayList<Result> result;

}
