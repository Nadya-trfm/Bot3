package com.github.telegrambot;

import com.github.telegrambot.response.PhotoResponse;
import com.github.telegrambot.response.UpdateResponse;
import com.github.telegrambot.response.UpdateResponse.Result;
import lombok.val;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;

import static com.github.telegrambot.ENV.TOKEN;


public class Main {
    private static void downloadFile(String file_path) {

        String BASE_URL = "https://api.telegram.org/file/bot" + TOKEN + "/";
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);
        Retrofit retrofit = builder.build();
        String fileUrl = BASE_URL + file_path;
        FileDownloadClient fileDownloadClient = retrofit.create(FileDownloadClient.class);
        Call<ResponseBody> call = fileDownloadClient.downloadFile(fileUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                writeResponseBodyToDisk(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }

    private static long countFilesInFolder() {
        long count;
        try (Stream<Path> files = Files.list(Paths.get("F:/bots/Bot3/file/photo"))) {
            count = files.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;

    }

    private static void writeResponseBodyToDisk(ResponseBody body) {

        try {
            long count = countFilesInFolder() + 1;
            File futureStudioIconFile = new File("file/photo/" + count + ".jpg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

            } catch (IOException e) {
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
        }

    }

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
                        if (update.message.text != null) {
                            System.out.println(update);
                            long id = update.message.chat.id;
                            String text = update.message.text;
                            String keyWord = "фото ";
                            int minLength = keyWord.length();
                            if (text.length() <= minLength || !text.substring(0, minLength).toLowerCase(Locale.ROOT).equals(keyWord)) {
                                val message = api.sendMessage(id, text).execute();
                            } else {
                                try {
                                    long id_poiska = Long.parseLong(text.substring(minLength));
                                    if (id_poiska <= 0 || id_poiska > countFilesInFolder()) {
                                        val message = api.sendMessage(id, "фото не найдено").execute();
                                    } else if (id_poiska % 100 == 0) {
                                        val message = api.sendMessage(id, "лох 100 низя").execute();
                                    } else {
                                        String path = "F:/bots/Bot3/file/photo/" + id_poiska + ".jpg";
                                        File file = new File(path);
                                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

                                        String photo = api.sendPhoto(id, body).execute().body();
                                    }
                                } catch (NumberFormatException nfe) {
                                    val message = api.sendMessage(id, "фото не найдено").execute();
                                }


                            }

                        }
                        if (!update.message.photo.isEmpty()) {
                            System.out.println(update);
                            long id = update.message.chat.id;

                            ArrayList<UpdateResponse.Photo> photos = update.message.photo;
                            UpdateResponse.Photo photo = photos.get(photos.size() - 1);
                            String file_id = photo.file_id;

                            PhotoResponse photoRes = api.getFile(file_id).execute().body();
                            String file_path = photoRes.result.file_path;
                            downloadFile(file_path);
                            long id_save_photo = countFilesInFolder() + 1;
                            val message = api.sendMessage(id, "id: " + id_save_photo).execute();
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
