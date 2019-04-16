package lunchbox.csy.com.lunchbox.remote;

import lunchbox.csy.com.lunchbox.item.MemberItem;
import lunchbox.csy.com.lunchbox.model.BasicResult;
import lunchbox.csy.com.lunchbox.model.LoginResult;
import lunchbox.csy.com.lunchbox.item.SearchItem;
import lunchbox.csy.com.lunchbox.model.SearchResult;
import lunchbox.csy.com.lunchbox.model.SignUpData;
import lunchbox.csy.com.lunchbox.model.SignUpResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RemoteService {
    String TAG = RemoteService.class.getSimpleName();

    // ★★★ push 시에 주의할 것
    String BASE_URL = "http://13.125.45.92:3000";

    @POST("/member")
    Call<SignUpResult> insertMember(@Body SignUpData signUpData);

    @FormUrlEncoded
    @POST("/member/login")
    Call<LoginResult> loginMember(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/member/login/myid")
    Call<LoginResult> loginByMyId(@Field("my_id") String myId);

    @GET("/email/send")
    Call<BasicResult> validateEmail(@Query("to") String to);

    @FormUrlEncoded
    @POST("/email/verify")
    Call<BasicResult> verifyEmailAndCode(@Field("email") String email, @Field("code") String code);

//    @GET("/profile/{username}")
//    Call<ProfileResult> getUserProfile(@Path("username") String username); // path != param
//
//    @GET("/profile/newuser/{newUsername}")
//    Call<CheckUsernameResult> checkUsername(@Path("newUsername") String newUsername);
//
//    @PUT("/profile/{username}")
//    Call<ProfileChangeResult> setUserProfile(@Body ChangeProfileItem changeProfileItem);

    @PUT("/member")
    Call<String> updateMember(@Body MemberItem memberItem);

    @DELETE("/member")
    Call<String> deleteMember(@Body MemberItem memberItem);

    @GET("/member")
    Call<String> getMyId(@Body MemberItem memberItem);

//    @Multipart
//    @POST ("/image")
//    Call<ImagePostResult> uploadFeedImage(@Part MultipartBody.Part file);

    @GET("/search/read")
    Call<SearchResult> getSearchData(@Query("category") String category);

}