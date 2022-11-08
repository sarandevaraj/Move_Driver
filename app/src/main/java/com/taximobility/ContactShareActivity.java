//package com.taximobility;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.taximobility.driver.interfaces.DriverAPIResult;
//import com.taximobility.driver.interfaces.DriverClickInterface;
//import com.taximobility.driver.service.DriverAPIService_Retrofit_JSON;
//import com.taximobility.driver.utils.DriverCToast;
//import com.taximobility.driver.utils.DriverNC;
//import com.taximobility.driver.utils.DriverSessionSave;
//import com.taximobility.driver.utils.Driver_Utils;
//import com.taximobility.features.CToast;
//import com.taximobility.util.NC;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//
//public class ContactShareActivity extends AppCompatActivity implements DriverClickInterface {
//
//    EditText btnview;
//    ImageView contact_img;
//    static final int PICK_CONTACT = 1;
//    String usercontact;
//    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
//    Button submit, cancel;
//    private Dialog dialog1;
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.shareride_alert_view);
//
//
//        btnview = (EditText) findViewById(R.id.contactEdt);
//        contact_img = (ImageView) findViewById(R.id.contact_img);
//        submit = (Button) findViewById(R.id.submit);
//        cancel = (Button) findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (btnview.getText().toString().isEmpty()) {
//                    CToast.ShowToast(ContactShareActivity.this, NC.getResources().getString(R.string.enter_your_mobile_number));
//
//                } else {
//                    String url = "type=trip_sharing_link";
//                    new ContactShareLink(url);
//
//                }
//            }
//        });
//
//
//        contact_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);
//            }
//        });
//
//        AccessContact();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void AccessContact() {
//
//        List<String> permissionsNeeded = new ArrayList<String>();
//        final List<String> permissionsList = new ArrayList<String>();
//        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
//            permissionsNeeded.add("Read Contacts");
//        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
//            permissionsNeeded.add("Write Contacts");
//
////        if (permissionsList.size() > 0) {
////            if (permissionsNeeded.size() > 0) {
////
////                String message = "You need to grant access to " + permissionsNeeded.get(0);
////                for (int i = 1; i < permissionsNeeded.size(); i++)
////                    message = message + ", " + permissionsNeeded.get(i);
////                showMessageOKCancel(message,
////                        new DialogInterface.OnClickListener() {
////                            @RequiresApi(api = Build.VERSION_CODES.M)
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
////                                        REQUEST_MULTIPLE_PERMISSIONS);
////                            }
////                        });
////                return;
////            }
////            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
////                    REQUEST_MULTIPLE_PERMISSIONS);
////            return;
////        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private boolean addPermission(List<String> permissionsList, String permission) {
//        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
//            permissionsList.add(permission);
//
//            if (!shouldShowRequestPermissionRationale(permission))
//                return false;
//        }
//        return true;
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(ContactShareActivity.this).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", null).create().show();
//    }
//
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//        switch (reqCode) {
//            case (PICK_CONTACT):
//                if (resultCode == Activity.RESULT_OK) {
//
//                    Uri contactData = data.getData();
//                    Cursor c = managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst()) {
//
//                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                        try {
//                            if (hasPhone.equalsIgnoreCase("1")) {
//                                Cursor phones = getContentResolver().query(
//                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
//                                        null, null);
//                                phones.moveToFirst();
//                                String cNumber = phones.getString(phones.getColumnIndex("data1"));
//                                System.out.println("number is:" + cNumber);
//                                //  btnview.setText("Phone Number is: " + cNumber);
//                                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                                btnview.setText(cNumber);
//
//
//                            }
//
//
//                        } catch (Exception ex) {
//                            // st.getMessage();
//                        }
//                    }
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void positiveButtonClick(DialogInterface dialog, int id, String s) {
//
//    }
//
//    @Override
//    public void negativeButtonClick(DialogInterface dialog, int id, String s) {
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
//    private class ContactShareLink implements DriverAPIResult {
//        String msg = "";
//        public ContactShareLink(String url) {
//            try {
//                JSONObject j = new JSONObject();
//                j.put("trip_id", DriverSessionSave.getSession("trip_id", ContactShareActivity.this));
//                j.put("phone", btnview.getText().toString().trim());
//
//                if (isOnline()) {
//                    new DriverAPIService_Retrofit_JSON(ContactShareActivity.this, this, j, false).execute(url);
//                } else {
//                    dialog1 = Driver_Utils.alert_view(ContactShareActivity.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, ContactShareActivity.this, "");
//                }
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        public void getResult(boolean isSuccess, String result) {
//
//            try {
//                if (isSuccess) {
//                    JSONObject json = new JSONObject(result);
//                    if (json.getInt("status") == 1) {
//
//                        msg = json.getString("message");
//                        CToast.ShowToast(ContactShareActivity.this, msg);
//                        onBackPressed();
//                    }else {
//                        msg = json.getString("message");
//                        CToast.ShowToast(ContactShareActivity.this, msg);
//                    }
//                }else {
//                    runOnUiThread(() -> DriverCToast.ShowToast(ContactShareActivity.this, DriverNC.getString(R.string.server_error)));
//                }
//            }catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public boolean isOnline() {
//
//        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null) {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null)
//                for (NetworkInfo networkInfo : info)
//                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
//                        return true;
//                    }
//        }
//        return false;
//    }
//
//}
//
