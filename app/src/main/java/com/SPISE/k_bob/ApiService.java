package com.SPISE.k_bob;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("custom/v1/31155/3b3c0a4e3b00a76fdc022c988cbbfeb45cc619a14455600118998669e4176d30/general")
    Call<ResponseBody> performOcrRequest(
            @Header("X-OCR-SECRET") String secretKey,
            @Part MultipartBody.Part file,
            @Part("message") RequestBody message
    );
}
