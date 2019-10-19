package com.genesis.android.network;

import android.content.Context;
import android.util.Log;

import com.genesis.android.ModelCheckUpdate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public final class CheckUpdate {
    private Context mContext;

    private CheckUpdateDialogListener checkUpdateDialogListener;
    private int currentVersionId;

    public static final String TAG = "CheckUpdate";


    private static ApiService apiService;


    public CheckUpdate(Context context) {
        this.mContext = context;
        init();
    }

    public CheckUpdate setCurrentVersionId(int currentVersionId) {
        this.currentVersionId = currentVersionId;
        return this;
    }

    public CheckUpdate setCheckUpdateDialogListener(CheckUpdateDialogListener checkUpdateDialogListener) {
        this.checkUpdateDialogListener = checkUpdateDialogListener;
        return this;
    }

    private void init() {
        apiService = ApiServiceGenerator.createService(mContext, ApiService.class);
    }

    public final void check() {
        // Make a request object and set the paremeters
        JSONObject checkUpdateRequest = new JSONObject();
        try {
            checkUpdateRequest.put("currentVersionId", currentVersionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Execute sequence
        Call<CheckUpdateModel> executeSequenceCall = apiService.checkupdate(checkUpdateRequest.toString());

        executeSequenceCall.enqueue(new Callback<CheckUpdateModel>() {
            @Override
            public void onResponse(Call<CheckUpdateModel> call, Response<CheckUpdateModel> response) {

                ModelCheckUpdate modelCheckUpdate2 = new ModelCheckUpdate().getInstance();
                modelCheckUpdate2.setName("test");
                modelCheckUpdate2.setVersionId(10);
                modelCheckUpdate2.setReleasedate("december");

                if (checkUpdateDialogListener != null)
                    checkUpdateDialogListener.onRecieveData(modelCheckUpdate2);

                if (response.isSuccessful()) {
                    String name = "";
                    int versionId = 0;
                    String releaseDate = "";
                    if (response.body() != null) {
                        CheckUpdateModel body = response.body();
                        name = body.getName();
                        versionId = body.getVersionId();
                        releaseDate = body.getReleasedate();
                        ModelCheckUpdate modelCheckUpdate = new ModelCheckUpdate().getInstance();
                        modelCheckUpdate.setName(name);
                        modelCheckUpdate.setVersionId(versionId);
                        modelCheckUpdate.setReleasedate(releaseDate);

                        if (checkUpdateDialogListener != null)
                            checkUpdateDialogListener.onRecieveData(modelCheckUpdate);
                    }
                } else {
                    Log.i(TAG, "else failure");
                }
            }

            @Override
            public void onFailure(Call<CheckUpdateModel> call, Throwable t) {
                // Notify registered listener
                Log.i(TAG, "failure ");
                t.printStackTrace();
            }
        });
    }
}