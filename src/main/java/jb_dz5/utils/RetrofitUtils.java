package jb_dz5.utils;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

import java.time.Duration;

@UtilityClass
public class RetrofitUtils {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    static public Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(11))
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

            return new Retrofit.Builder()
                    .client(client)
                    .baseUrl("http://80.78.248.82:8189/market/api/v1/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
    }



