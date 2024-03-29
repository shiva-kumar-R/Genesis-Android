package com.genesis.android;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.genesis.android.network.DownloadPatch;

public class DownloadDialog extends DialogFragment {
    private Context context;
    private Button material_download;
    private ModelCheckUpdate modelCheckUpdate;
    private TextView textView_name;
    private TextView textView_version;
    private TextView textView_releaseDate;

    private static final String TAG = "downloaddialog";

    public DownloadDialog() {

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setModelCheckUpdate(ModelCheckUpdate modelCheckUpdate) {
        this.modelCheckUpdate = modelCheckUpdate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.alert_layout, container, false);
        material_download = view.findViewById(R.id.material_download);

        textView_name = view.findViewById(R.id.textView_name);
        textView_name.setText(modelCheckUpdate.getName());

        textView_version = view.findViewById(R.id.textView_version);
        textView_version.setText(modelCheckUpdate.getVersion());

        textView_releaseDate = view.findViewById(R.id.textView_releaseDate);
        textView_releaseDate.setText(modelCheckUpdate.getRelease_date());

        material_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadPatch(context).downloadPatchFile(context, modelCheckUpdate.getPatch_id(), modelCheckUpdate.getPatch_file());
            }
        });

        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
