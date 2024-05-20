package com.example.technology_app.retrofit;

import com.example.technology_app.models.AddProduct.AddProductModel;
import com.example.technology_app.models.AddToCartModel;
import com.example.technology_app.models.CartModel;
import com.example.technology_app.models.CategoryModel;
import com.example.technology_app.models.GetOrder.GetOrderModel;
import com.example.technology_app.models.Meeting.CreateMeeting;
import com.example.technology_app.models.Products.Laptop.ProductModel;
import com.example.technology_app.models.SignUp;
import com.example.technology_app.models.Statistic.GetStatisticModel;
import com.example.technology_app.models.UpdateOrder.UpdateOrderModel;
import com.example.technology_app.models.UserInfoModel;
import com.example.technology_app.models.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @POST("login")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("signup")
    @FormUrlEncoded
    Observable<SignUp> signup(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("passwordConfirm") String passwordConfirm
    );

    @POST("verify?type=signUp")
    @FormUrlEncoded
    Observable<UserModel> verifyCodeForSignUp(
            @Field("email") String name,
            @Field("OTP") String otp
    );

    @POST("verify?type=forgotPwd")
    @FormUrlEncoded
    Observable<UserModel> verifyCodeForForgotPass(
            @Field("email") String name,
            @Field("OTP") String otp
    );

    @POST("forgotPassword")
    @FormUrlEncoded
    Observable<UserModel> forgotPass(
            @Field("email") String name
    );

    @PATCH("user/tokenFirebase")
    @FormUrlEncoded
    Observable<UserModel> updateFirebaseToken(
            @Field("userId") String userId,
            @Field("tokenFirebase") String tokenFirebase
    );

    @GET("category")
    Observable<CategoryModel> getCategory();


    @GET("user")
    Observable<UserInfoModel> getUserInfo(
            @Header("x-client-id") String userId,
            @Header("authorization") String author
    );

    @GET("product")
    Observable<ProductModel> getAllProductByCateId(@Query("category") String cateId);

//    @GET("product/{productId}")
//    Observable<DetailProductModel> getDetailProduct(@Path("productId") String productId);

    @POST("cart")
    @FormUrlEncoded
    Observable<AddToCartModel> addToCart(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Field("product") String productId,
            @Field("quantity") int quantity
    );

    @GET("cart")
    Observable<CartModel> getCartUser(@Header("x-client-id") String userId,
                                      @Header("authorization") String author);

    @GET("order")
    Observable<GetOrderModel> getOrderUser(
            @Header("x-client-id") String userId,
                                           @Header("authorization") String author);


    @PATCH("order/{orderId}/status")
    @FormUrlEncoded
    Observable<UpdateOrderModel> updateStatusOrder(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Path("orderId") String orderId,
            @Field("status") String status
    );

    @Multipart
    @POST("product")
    Call<AddProductModel> addProduct(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Part("name") RequestBody name,
            @Part("sale_price") RequestBody salePrice,
            @Part MultipartBody.Part images,
            @Part("category") RequestBody category,
            @Part("type") RequestBody type,
            @Part("left") RequestBody left);


    @POST("meeting")
    @FormUrlEncoded
    Observable<CreateMeeting> createMeeting(
            @Header("x-client-id") String userId,
            @Header("authorization") String author,
            @Field("id") String meetingId,
            @Field("token") String token
    );

    @GET("statistic/soldOfProducts?year=2024")
    Observable<GetStatisticModel> getStatistic(
            @Header("x-client-id") String userId,
            @Header("authorization") String author);
}
