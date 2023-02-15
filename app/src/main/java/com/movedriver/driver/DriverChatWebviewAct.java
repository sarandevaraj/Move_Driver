package com.movedriver.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.movedriver.R;
import com.movedriver.driver.data.DriverCommonData;
import com.movedriver.driver.earningchart.DriverEarningsAct;
import com.movedriver.driver.utils.DirverColorchange;
import com.movedriver.driver.utils.DriverNetworkStatus;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.service.FirebaseService;
import com.movedriver.util.SessionSave;

import java.io.File;
import java.io.IOException;

import static com.movedriver.driver.utils.DriverGpsStatus.mDialog;
import static com.movedriver.util.ConstantsKt.API_BASE;

/**
 * Created by developer on 2/1/18.
 */
public class DriverChatWebviewAct extends DriverBaseActivity {
    WebView simpleWebView;
    String driverId, encodeSTr, link_1 = "", link_2, link_2_attach, lang_Str, Id = "", chat_type = "", to_type;
    private String fromAct = "";
    private String type, trip_id = "";
    private DriverNetworkStatus networkStatus;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;
    private final boolean isFromEarningsAct = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_chat_webview_act);
        FirebaseService.activity = this;
        DriverNetworkStatus.appContext = this;
        networkStatus = new DriverNetworkStatus();
        registerReceiver(networkStatus, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        // initiate buttons and a web view
        if (getIntent() != null) {
            fromAct = getIntent().getStringExtra("fromMyStatus");
            type = getIntent().getStringExtra("type");
            Id = getIntent().hasExtra("Id") ? getIntent().getStringExtra("Id") : "";
            chat_type = getIntent().hasExtra("chat_type") ? getIntent().getStringExtra("chat_type") : "";
            trip_id = getIntent().hasExtra("trip_id") ? getIntent().getStringExtra("trip_id") : "";
            to_type = getIntent().hasExtra("to_type") ? getIntent().getStringExtra("to_type") : "";
        }
        simpleWebView = findViewById(R.id.simpleWebView);
        WebSettings webSettings = simpleWebView.getSettings();
        webSettings.setAllowFileAccess(true);
        simpleWebView.getSettings().setDomStorageEnabled(true);
        simpleWebView.getSettings().setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
            simpleWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            simpleWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < 19) {
            simpleWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webSettings.setJavaScriptEnabled(true);
        simpleWebView.setWebViewClient(new MyWebViewClient());
        simpleWebView.setWebChromeClient(new WebChromeClient() {
            //For Android 5.0+
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mUMA != null) {
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;
                Uri imageUri;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(DriverChatWebviewAct.this.getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    } catch (IOException ex) {
                        Log.e("TAG ingrete", "Image file creation failed", ex);
                    }
                    if (photoFile != null) {
                        mCM = "file:" + photoFile.getAbsolutePath();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            imageUri = FileProvider.getUriForFile(DriverChatWebviewAct.this, DriverChatWebviewAct.this.getPackageName().concat(".files_root"), photoFile);
                        } else {
                            imageUri = Uri.fromFile(photoFile);
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);
                return true;
            }
        });
        driverId = DriverSessionSave.getSession("Id", DriverChatWebviewAct.this);
        encodeSTr = driverId;
        lang_Str = DriverSessionSave.getSession("Lang", DriverChatWebviewAct.this);

        if (!TextUtils.isEmpty(Id)) {
            link_1 = DriverSessionSave.getSession("chat_node_url", DriverChatWebviewAct.this) + "?name=" + DriverSessionSave.getSession("Name", DriverChatWebviewAct.this) + "(" + DriverSessionSave.getSession("Id", DriverChatWebviewAct.this) + ")&id=" + Id + "&image=" + DriverSessionSave.getSession("d_image_name", DriverChatWebviewAct.this) + "&type=D" + "&chat_type=" + chat_type + "&to_type=" + to_type;
        }

        if (TextUtils.isEmpty(link_1.trim())) {
            /* link_1 = "http://54.157.148.77:4002/"+ "?name="+ DriverSessionSave.getSession("Name",DriverChatWebviewAct.this)+"("
                + DriverSessionSave.getSession("Id", DriverChatWebviewAct.this)+")&id=Job_ID_"+DriverSessionSave.getSession("trip_id", DriverChatWebviewAct.this)+"&image="+ DriverSessionSave.getSession("d_image_name",DriverChatWebviewAct.this)
                +"&type=D";*/
            if (type.equals("3")) {
                link_1 = DriverSessionSave.getSession("chat_node_url", DriverChatWebviewAct.this) + "?name=" + DriverSessionSave.getSession("Name", DriverChatWebviewAct.this) + "(" + DriverSessionSave.getSession("Id", DriverChatWebviewAct.this) + ")&id=Driver_Chat_Id_" + DriverSessionSave.getSession("Id", DriverChatWebviewAct.this) + "&image=" + DriverSessionSave.getSession("d_image_name", DriverChatWebviewAct.this) + "&type=D" + "&chat_type=2" + "&to_type=A";
            } else {
                link_1 = DriverSessionSave.getSession("chat_node_url", DriverChatWebviewAct.this) + "?name=" + DriverSessionSave.getSession("Name", DriverChatWebviewAct.this) + "(" + DriverSessionSave.getSession("Id", DriverChatWebviewAct.this) + ")&id=Job_ID_" + trip_id + "&image=" + DriverSessionSave.getSession("d_image_name", DriverChatWebviewAct.this) + "&type=D" + "&chat_type=1" + "&to_type=P";

            }
        }
        System.out.println("link====" + link_1);
        simpleWebView.loadUrl(link_1);
        showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null || intent.getData() == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseService.activity = this;
    }

    public void showDialog() {
        try {
            if (DriverNetworkStatus.isOnline(DriverChatWebviewAct.this)) {
                if (mDialog != null) mDialog.dismiss();
                View view = View.inflate(DriverChatWebviewAct.this, R.layout.driver_progress_bar, null);
                mDialog = new Dialog(DriverChatWebviewAct.this, R.style.dialogwinddow);
                DirverColorchange.ChangeColor((ViewGroup) view, DriverChatWebviewAct.this);
                mDialog.setContentView(view);
                mDialog.setCancelable(false);
                try {
                    mDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ImageView iv = mDialog.findViewById(R.id.giff);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
                Glide.with(DriverChatWebviewAct.this).load(R.raw.driver_loading_anim).into(imageViewTarget);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //To check whether it is came from MyStatus Activity
        if (!TextUtils.isEmpty(Id)) {
            startActivity(new Intent(DriverChatWebviewAct.this, DriverOngoingAct.class));
            finish();
        } else if (type.equals("2")) {
            finish();
        } else {
            startActivity(new Intent(DriverChatWebviewAct.this, DriverMyStatus.class));
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && simpleWebView.canGoBack()) {
            if (type.equals("12")) {
                simpleWebView.goBack();
            } else if (type.equals("1")) {
                startActivity(new Intent(DriverChatWebviewAct.this, DriverEarningsAct.class));
                finish();
            } else {
                startActivity(new Intent(DriverChatWebviewAct.this, DriverMeAct.class));
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void closeDialog() {
        try {
            if (mDialog != null) if (mDialog.isShowing()) mDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(SessionSave.getSession(API_BASE, DriverChatWebviewAct.this) + "back")) {
                onBackPressed();
            } else if (url.contains(SessionSave.getSession(API_BASE, DriverChatWebviewAct.this) + "cancel")) {
                onBackPressed();
            } else {

                view.loadUrl(url);
            }
            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                closeDialog();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
        }

        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
        }
    }


    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = DriverCommonData.getDateForCreateImageFile();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseService.activity = null;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkStatus);
        super.onDestroy();
    }
}