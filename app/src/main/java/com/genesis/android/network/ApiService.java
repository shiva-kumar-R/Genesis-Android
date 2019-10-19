package com.genesis.android.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiService {

    @Streaming
    @GET("app/{app-name}/patch/{patch-file}")
    Call<ResponseBody> downloadPatchFile(
            @Path(value = "app-name", encoded = true) String appName,
            @Path(value = "patch-file", encoded = true) String patchFile
    );

    @GET("app/{app-name}/update/{version-id}")
    Call<CheckUpdateModel> checkForUpdate(
            @Path(value = "app-name", encoded = true) String appName,
            @Path(value = "version-id", encoded = true) String version);
}
