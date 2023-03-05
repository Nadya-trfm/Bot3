package com.github.telegrambot.response;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
public class PhotoResponse {



    @ToString
    @Getter
    public class Result{
        public String file_id;
        public String file_unique_id;
        public Integer file_size;
        public String file_path;
    }



    public boolean ok;
    public Result result;

}


