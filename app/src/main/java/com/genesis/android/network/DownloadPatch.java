package com.genesis.android.network;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.genesis.android.R;
import com.genesis.android.network.bsdiff.BSPatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private static File oldApkFile;

    public static final String TAG = "DownloadPatch";
    private static File file = null;

    private static ApiService apiService;


    public DownloadPatch(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        apiService = ApiServiceGenerator.createService(mContext, ApiService.class);
    }

    public void downloadPatchFile(final Context context, final String patch_file) {
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

                            oldApkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                                    File.separator + R.string.app_name); //this needs to be set
                            File patchFile = new File(file.getAbsolutePath());

                            try {

                                int status = PatchHelper.applyPatch(oldApkFile, patchFile);

                    /*if(status == BSPatch.RETURN_SUCCESS)
                    {
                        //Success
                    }*/

                                switch (status) {
                                    case BSPatch.RETURN_DIFF_FILE_ERR: {
                                        throw new PatchException("Diff File Error");
                                    }
                                    case BSPatch.RETURN_NEW_FILE_ERR: {
                                        throw new PatchException("New File Error");
                                    }
                                    case BSPatch.RETURN_OLD_FILE_ERR: {
                                        throw new PatchException("Old File Error");
                                    }

                                }
                            } catch (PatchException e) {
                                e.printStackTrace();
                            }

                            installPatchedAPK(new File(file.getParentFile().getAbsolutePath() + File.separator + "updated_" + oldApkFile.getName()));
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

    public void installPatchedAPK(File newAPKPath) {
        copy(newAPKPath, oldApkFile);
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(Uri.parse(oldApkFile.getAbsolutePath()),
                        "application/vnd.android.package-archive");
        mContext.startActivity(promptInstall);

        //to check the behaviour if the below statement is removed
        //startActivity(intent);
    }

    public static void copy(File src, File dst) {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean writeResponseBodyToDisk(Context context, ResponseBody body, String fileName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // TODO Change file name and location
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
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