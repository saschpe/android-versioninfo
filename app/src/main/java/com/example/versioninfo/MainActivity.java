package com.example.versioninfo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import saschpe.versioninfo.widget.VersionInfoDialogFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showVersionInfoDialog(View view) {
        VersionInfoDialogFragment
                .newInstance(
                        getString(R.string.app_name),
                        BuildConfig.VERSION_NAME,
                        "Sascha Peilicke",
                        R.mipmap.ic_launcher)
                .show(getFragmentManager(), "version_info");
    }

    public void showVersionInfoFragment(View view) {
        DialogFragment fragment = VersionInfoDialogFragment.newInstance(
                getString(R.string.app_name),
                BuildConfig.VERSION_NAME,
                "Sascha Peilicke",
                R.mipmap.ic_launcher);

        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
