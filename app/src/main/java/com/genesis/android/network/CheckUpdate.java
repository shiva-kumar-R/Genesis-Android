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
    private String app_name;

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

    public CheckUpdate setApp_name(String app_name) {
        this.app_name = app_name;
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
//        JSONObject checkUpdateRequest = new JSONObject();
//        try {
//            checkUpdateRequest.put("app-name", "");
//            checkUpdateRequest.put("patch-file", currentVersionId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        // Execute sequence
        Call<CheckUpdateModel> executeSequenceCall = apiService.checkForUpdate(app_name, String.valueOf(currentVersionId));

        executeSequenceCall.enqueue(new Callback<CheckUpdateModel>() {
            @Override
            public void onResponse(Call<CheckUpdateModel> call, Response<CheckUpdateModel> response) {

//                ModelCheckUpdate modelCheckUpdate2 = new ModelCheckUpdate().getInstance();
//                modelCheckUpdate2.setName("test");
//                modelCheckUpdate2.setVersionId(10);
//                modelCheckUpdate2.setReleasedate("december");
//
//                if (checkUpdateDialogListener != null)
//                    checkUpdateDialogListener.onRecieveData(modelCheckUpdate2);

                if (response.isSuccessful()) {
                    String name = "";
                    String version = "";
                    String release_date = "";
                    int patch_id = 0;
                    String patch_file = "";
                    if (response.body() != null) {
                        CheckUpdateModel body = response.body();
                        name = body.getName();
                        version = body.getVersion();
                        release_date = body.getRelease_date();
                        ModelCheckUpdate modelCheckUpdate = new ModelCheckUpdate().getInstance();
                        modelCheckUpdate.setName(name);
                        modelCheckUpdate.setVersion(version);
                        modelCheckUpdate.setRelease_date(release_date);
                        modelCheckUpdate.setPatch_id(patch_id);
                        modelCheckUpdate.setPatch_file(patch_file);

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