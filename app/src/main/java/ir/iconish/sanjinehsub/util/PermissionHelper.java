package ir.iconish.sanjinehsub.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {
    public static final int REQUEST_PERMISSION_LOCATION = 2;
    public static final int REQUEST_WRITE_EXTERNAL = 3;

  public static void permission(Activity activity, PermissionListener listener, String permission) {
    Dexter.withActivity(activity)
      .withPermission(permission)
      .withListener(listener)
      .check();
  }

  public static void permissions(Activity activity, MultiplePermissionsListener listener, String... permissions) {
    Dexter.withActivity(activity)
      .withPermissions(permissions)
      .withListener(listener)
      .check();
  }



  public static void checkAlreadyGrantPermissin(Activity activity){
      PermissionListener permissionListener =new PermissionListener() {
          @Override
          public void onPermissionGranted(PermissionGrantedResponse response) {
          }

          @Override
          public void onPermissionDenied(PermissionDeniedResponse response) {

          }

          @Override
          public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
token.continuePermissionRequest();
          }
      };
      permission(activity,permissionListener,Manifest.permission.ACCESS_COARSE_LOCATION);
  }

    public static boolean checkAndRequestPermissions(Activity activity) {
        System.out.println("PermissionsUtils checkAndRequestPermissions()");

        int permissionLocation = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionWriteExternal = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();



        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }



        return true;
    }



    public static void requestLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(activity, "LOCATION permission is needed to display location info ", Toast.LENGTH_SHORT)
                        .show();

                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        REQUEST_PERMISSION_LOCATION);

                Toast.makeText(activity, "REQUEST LOCATION PERMISSION", Toast.LENGTH_LONG).show();

            } else {

                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                        REQUEST_PERMISSION_LOCATION);
                Toast.makeText(activity, "REQUEST LOCATION PERMISSION", Toast.LENGTH_LONG).show();

            }
        } else {

        }
    }

    public static void requestWriteExternalPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "Write permission is needed to create Excel file ", Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_WRITE_EXTERNAL);

                Toast.makeText(activity, "REQUEST LOCATION PERMISSION", Toast.LENGTH_LONG).show();

            } else {
                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_WRITE_EXTERNAL);

            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static String staticMapUrlMaker(double latitude, double longitude, int height, int width, int zoom) {

        String url = "https://api.mapbox.com/v4/mapbox.emerald/pin-m-heart+e6353b(" + longitude + "," + latitude + ")/"+ longitude + "," + latitude + "," + zoom + "/" + width + "x" + height + ".png?" + "access_token=" + "pk.eyJ1IjoiYWxpMTM3NiIsImEiOiJjanFjMXh4YjYxMDBhM3htbmp3d3Bsd3UyIn0.7gknkaPOCzjZFc7lNyfBYg";

        return url;
    }
}
