package com.genesis.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.genesis.android.network.CheckUpdate;
import com.genesis.android.network.CheckUpdateDialogListener;

public class MainActivity extends AppCompatActivity implements CheckUpdateDialogListener {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private static final int MY_PERMISSIONS_REQUEST_READ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        progressBar = findViewById(R.id.progressBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if not granted it will crash
                    //there is no stopping here
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {
                    Toast.makeText(MainActivity.this, "Permissions not issued", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_update: {
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    int version = pInfo.versionCode;
                    String app_name = MainActivity.this.getResources().getString(R.string.app_name).toLowerCase();
                    progressBar.setVisibility(View.VISIBLE);
                    new CheckUpdate(MainActivity.this).setCurrentVersionId(version).setApp_name(app_name).setCheckUpdateDialogListener(MainActivity.this).check();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }

    @Override
    public void onRecieveData(ModelCheckUpdate modelCheckUpdate) {
        progressBar.setVisibility(View.GONE);
        DownloadDialog downloadDialog = new DownloadDialog();
        downloadDialog.setContext(MainActivity.this);
        downloadDialog.setModelCheckUpdate(modelCheckUpdate);
        downloadDialog.show(MainActivity.this.getFragmentManager(), "DownloadDialog");
    }
}
