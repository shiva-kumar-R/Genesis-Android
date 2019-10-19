package com.genesis.android.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.genesis.android.ModelCheckUpdate;
import com.genesis.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public final class DownloadPatch {
    private Context mContext;


    public static final String TAG = "DownloadPatch";


    private static ApiService apiService;


    public DownloadPatch(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        apiService = ApiServiceGenerator.createService(mContext, ApiService.class);
    }

    public static void downloadPatchFile(final Context context, final String patch_file) {
        String app_name = context.getResources().getString(R.string.app_name);
        Call<ResponseBody> call = apiService.downloadPatchFile(app_name, patch_file);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Log.i("CheckMe", response.toString());

                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        boolean success = false;
                        if (response.body() != null) {
                            success = writeResponseBodyToDisk(context, response.body(), patch_file);
                            response.body().close();
                        }

                        return success;
                    }

                    @Override
                    protected void onPostExecute(Boolean success) {
                        if (success) {
                            // TODO create SnackBar with action to open file
                            Toast.makeText(context, "File has been successfully downloaded", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "Error when downloading file", Toast.LENGTH_SHORT).show();
                        super.onPostExecute(success);
                    }
                }.execute();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // TODO handle this condition
            }
        });
    }

    private static boolean writeResponseBodyToDisk(Context context, ResponseBody body, String fileName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // TODO Change file name and location
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                    File.separator + fileName);
            Log.i("CheckMe", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                    File.separator + fileName);

            try {
                byte[] fileReader = new byte[8 * 1024];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}