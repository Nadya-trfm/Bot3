package com.github.telegrambot.request;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class CopyMessageRequest {
    @ToString
    @Setter
    @Getter
    public class Chat{
        public long id;
        public String first_name;
        public String last_name;
        public String username;
        public String type;
    }

    @ToString
    @Setter
    @Getter
    public class From{
        public long id;
        public boolean is_bot;
        public String first_name;
        public String username;
    }

    @ToString
    @Setter
    @Getter
    public class Result{
        public long message_id;
        public From from;
        public Chat chat;
        public int date;
        public String text;
    }

        public boolean ok;
    public Result result;


}
