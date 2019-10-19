package com.genesis.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.genesis.android.network.CheckUpdate;
import com.genesis.android.network.CheckUpdateDialogListener;

public class MainActivity extends AppCompatActivity implements CheckUpdateDialogListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar


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
                    String app_name = MainActivity.this.getResources().getString(R.string.app_name);
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
        DownloadDialog downloadDialog = new DownloadDialog();
        downloadDialog.setContext(MainActivity.this);
        downloadDialog.setModelCheckUpdate(modelCheckUpdate);
        downloadDialog.show(MainActivity.this.getFragmentManager(), "DownloadDialog");
    }
}
