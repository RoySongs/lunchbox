package lunchbox.csy.com.lunchbox.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lunchbox.csy.com.lunchbox.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static <S> S createService(Class<S> serviceClass) {
        /**
         * setLevel(): Set the log level specifying which message levels will be logged by this logger.
         * 참고: https://developer.android.com/reference/java/util/logging/Logger
         *
         * HttpLoggingIntercepotr Levels:
         * NONE: No logs.
         * BASIC: Logs request and response lines.
         * HEADERS: Logs request and response lines and their respective headers.
         * BODY: Logs request and response lines and their respective headers and bodies (if present).
         * 참고: https://square.github.io/okhttp/3.x/logging-interceptor/okhttp3/logging/HttpLoggingInterceptor.Level.html
         */
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) { // If Build is Debug Mode, Show Log
            logging.setLevel(HttpLoggingInterceptor.Level.BODY); //
        } else { // Otherwise, show nothing.
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient() // [리니언트]관대한. true. By default, Gson is strict and only accepts JSON as specified by RFC 4627.
                .create();

        Retrofit retrofit;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RemoteService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))  // 서버에서 json 형식으로 데이터를 보내고 이를 파싱해서 받아옵니다.
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 받은 응답을 옵서버블 형태로 변환해 줍니다.
                    .build();

            return retrofit.create(serviceClass); // Retrofit creates Service with the received serviceClass.
        } catch (Exception e) {
            e.printStackTrace(); // Exception(Internal Error) can cause from newProxyInstance().
        }

        return null;
    }
}