package com.genesis.android.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiService {

    @Streaming
    @GET("download")
    Call<ResponseBody> downloadPatchFile();

    @POST("checkupdate")
    Call<CheckUpdateModel> checkupdate(@Body String currentVersionId);
}
