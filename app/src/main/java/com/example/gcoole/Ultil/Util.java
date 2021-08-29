package com.example.gcoole.Ultil;

import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static boolean validate(AppCompatActivity activity, int requestCode, String[] permissions) {
        List<String> list = new ArrayList<String>();
        for (String permission : permissions) {
            boolean ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (!ok) { // se for false
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            return true;
        }
        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);
//solicita a permissao
        ActivityCompat.requestPermissions(activity, newPermissions, requestCode);
        return false;
    }
}
