package com.taximobility.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.taximobility.ProfileImageSetupClass;
import com.squareup.picasso.Picasso;
import com.taximobility.MainHomeFragmentActivity;
import com.taximobility.R;
import com.taximobility.features.CToast;
import com.taximobility.interfaces.APIResult;
import com.taximobility.interfaces.AlertListener;
import com.taximobility.service.APIService_Retrofit_JSON;
import com.taximobility.service.APIService_Retrofit_JSON_NoProgress;
import com.taximobility.util.Colorchange;
import com.taximobility.util.FontHelper;
import com.taximobility.util.ImageUtils;
import com.taximobility.util.NC;
import com.taximobility.util.RoundedImageView;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.taximobility.util.TaxiUtil.Logout;
import com.taximobility.util.Utility;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.taximobility.util.ConstantsKt.LOGOUT;
import static com.taximobility.util.ConstantsKt.PASS_ID;
import static com.taximobility.util.ConstantsKt.PASS_NAME;

/**
 * Created by developer on 4/22/16.
 */

/**
 * this class is used for user profile details
 */

public class ProfileFrag extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 112;
    ImageView linechange;
    TextView chngepwd;
    TextView back_text;
    String convertMD5_Value;
    LinearLayout main;
    private Dialog alertmDialog;
    private LinearLayout profile_bottom;
    private TextView Spinnertext;
    private String destinationFileName = "ProfileImage";
    private boolean isProfileloaded = false;
    //hello hfff
    // Class members declarations.
    private Button SaveImg;
    private TextView HeadTitle;
    private LinearLayout SlideImg;
    private EditText FirstnameEdt;
    private EditText LastnameEdt;
    private EditText EmailEdt;
    private EditText MobileEdt;
    private EditText PasswordEdt;
    private RoundedImageView ProfileImg;
    private TextView CancelTxt;
    private String Firstname;
    private String Lastname;
    private String Email;
    private String Mobile;
    private String Password;
    private String f_Firstname;
    private String f_Lastname;
    private String f_Email;
    private String f_Picture;
    private String f_Mobile;
    private String f_Password;
    private String f_countrycode;
    private String f_Salutation, f_login_from;
    private String encodedImage = "";
    private String dummy_image = "";
    private Uri imageUri;
    private Bitmap mBitmap;
    private Spinner Sal_Spn;
    private ArrayAdapter<String> adapter;
    private String Salutation;
    private LinearLayout LogoutBtn;
    private Bitmap downImage;

   ImageView left_icon_pro,right_icon_pro;
    /**
     * Handler to update the UI.
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    final String imgpath = "" + f_Picture;


                   // f_Firstname = jarry.getJSONObject(i).getString("name")

                    if (f_Picture != null && f_Picture.length() > 0) {
                        Picasso.get().load(f_Picture).error(R.drawable.loadingimage).placeholder(R.drawable.loadingimage).into(ProfileImg);
                    } else {
                        if (f_Firstname != "") {
                            ProfileImageSetupClass.setupProfileImage(
                                    f_Firstname, ProfileImg
                            );
                        } else {
                            Picasso.get().load(R.drawable.loadingimage).into(ProfileImg);
                        }
                    }

                    Drawable drawable = ProfileImg.getDrawable();
                    downImage = ImageUtils.drawableToBitmap(drawable);
                 //   main.setVisibility(View.VISIBLE);
                    profile_bottom.setVisibility(View.VISIBLE);
                    FirstnameEdt.setText(Html.fromHtml(f_Firstname));
                    LastnameEdt.setText(Html.fromHtml(f_Lastname));
                    EmailEdt.setText(f_Email);
                    MobileEdt.setText(f_Mobile);
                    PasswordEdt.setText(f_Password);
                    Spinnertext.setText(f_countrycode);
                    if (f_Salutation.equalsIgnoreCase("Miss"))
                        Sal_Spn.setSelection(2);
                    if (f_Salutation.equalsIgnoreCase("Mrs"))
                        Sal_Spn.setSelection(1);
                    if (f_Salutation.equalsIgnoreCase("Mr"))
                        Sal_Spn.setSelection(0);
                    SessionSave.saveSession("Email", f_Email, getActivity());
                    SessionSave.saveSession("Phone", f_Mobile, getActivity());
                    SessionSave.saveSession("FName", f_Firstname, getActivity());
                    SessionSave.saveSession("Lname", f_Lastname, getActivity());
                    SessionSave.saveSession("Salutation", Salutation, getActivity());
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }

    };
    private Spinner mobilecodespn;
    private int positionFirst;
    private List<String> code = new ArrayList<String>();
    private Dialog mshowDialog;

    private Dialog dialog1;
    private Dialog dialog;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profilelay, container, false);
        Initialize(v);
        new Handler().postDelayed(() -> {
            Colorchange.ChangeColor((ViewGroup) (((ViewGroup) getActivity()
                    .findViewById(android.R.id.content)).getChildAt(0)), getActivity());
        }, 50);
        return v;
    }

    /**
     * Set the layout to activity.
     */


    public void priorChanges(View v) {
        CancelTxt = v.findViewById(R.id.leftIcon);
        CancelTxt.setVisibility(View.GONE);
        back_text = v.findViewById(R.id.back_text);
        back_text.setVisibility(View.VISIBLE);
        HeadTitle = v.findViewById(R.id.header_titleTxt);
        HeadTitle.setText(NC.getResources().getString(R.string.menu_profile));
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.headlayout));

    }

    public void showLoading(Context context) {
        View view = View.inflate(context, R.layout.progress_bar, null);
        mshowDialog = new Dialog(context, R.style.dialogwinddow);
        mshowDialog.setContentView(view);
        mshowDialog.setCancelable(false);
        mshowDialog.show();
        ImageView iv = mshowDialog.findViewById(R.id.giff);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(getActivity())
                .load(R.raw.loading_anim)
                .into(imageViewTarget);
    }

    @Override
    public void onStop() {
        closeLoading();
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);
        super.onStop();

    }

    void closeLoading() {
        try {
            if (mshowDialog.isShowing())
                mshowDialog.dismiss();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // Initialize the views on layout
    @SuppressLint("NewApi")
    public void Initialize(View v) {
        TaxiUtil.current_act = "ProfileAct";
        FontHelper.applyFont(getActivity(), v.findViewById(R.id.profile_contain));
      //  main = v.findViewById(R.id.profile_lay);
        profile_bottom = v.findViewById(R.id.profile_bottom);
        final File photo = new File(Environment.getExternalStorageDirectory() + "/Taxi/Send");
        if (!photo.exists())
            photo.mkdirs();
        TaxiUtil.mActivitylist.add(getActivity());
        SaveImg = v.findViewById(R.id.save);
        SaveImg.setVisibility(View.VISIBLE);
        SlideImg = v.findViewById(R.id.leftIconTxt);
        ProfileImg = v.findViewById(R.id.profile_img);
        FirstnameEdt = v.findViewById(R.id.firstText1);
        LastnameEdt = v.findViewById(R.id.lastText2);
        EmailEdt = v.findViewById(R.id.emailText1);

        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.GONE);
        ((MainHomeFragmentActivity) getActivity()).toolbar_title.setText(NC.getString(R.string.menu_profile));
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("backarrow");
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_back_white);
        ((MainHomeFragmentActivity) getActivity()).toolbarRightIcon(false);

        MobileEdt = v.findViewById(R.id.mobileText2);
        PasswordEdt = v.findViewById(R.id.passwordText3);
        Sal_Spn = v.findViewById(R.id.salSpn);
        chngepwd = v.findViewById(R.id.chgepwdTxt);
        linechange = v.findViewById(R.id.linechange);
        LogoutBtn = v.findViewById(R.id.logoutBtn);
        Spinnertext = v.findViewById(R.id.spinner_value);

        left_icon_pro  = v.findViewById(R.id.left_icon_pro);
        right_icon_pro  = v.findViewById(R.id.right_icon_pro);

//        ImageView iv = v.findViewById(R.id.giff);
//        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
//        Glide.with(getActivity())
//                .load(R.raw.loading_anim)
//                .into(imageViewTarget);

        // Open the popup to change the password
        MobileEdt.setClickable(false);
        MobileEdt.setFocusable(false);
        Sal_Spn.setClickable(false);
        Sal_Spn.setFocusable(false);

//        chngepwd.setOnClickListener(v1 -> changePwdDialog());
        chngepwd.setOnClickListener(v1 -> showPasswordSheet());
        mobilecodespn = v.findViewById(R.id.mobilecodespn);

        Spinnertext.setText(SessionSave.getSession("CountyCode", getActivity()));
        // Call the profile API when the layout loaded.
        try {
            if (TaxiUtil.isOnline(getActivity())) {
                JSONObject j = new JSONObject();
                j.put("userid", SessionSave.getSession(PASS_ID, getActivity()));
                new GetProfile("type=passenger_profile", j);
            } else {
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        ArrayList<String> sal = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.sal_type)));
        adapter = new FontHelper.MySpinnerAdapter(getActivity(), R.layout.spinneritem_lay, sal);
        //  adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem_lay, NC.getResources().getStringArray(R.array.sal_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sal_Spn.setAdapter(adapter);
        // To show the Slider menu for move from one activity to another activity.

        // To change passenger salutation
        Sal_Spn.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View v, final int pos, final long arg3) {
                Colorchange.ChangeColor(parent, getActivity());
                Salutation = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(final AdapterView<?> arg0) {
            }
        });
        // To Update the passenger profile
        SaveImg.setOnClickListener(v12 -> {
            try {
                Firstname = FirstnameEdt.getText().toString().trim();
                Lastname = LastnameEdt.getText().toString().trim();
                Email = EmailEdt.getText().toString().trim();
                Mobile = MobileEdt.getText().toString().trim();
                Password = PasswordEdt.getText().toString().trim();
                Drawable drawable = ProfileImg.getDrawable();
                Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);
                if (Mobile.equalsIgnoreCase(SessionSave.getSession("Phone", getActivity())) && Firstname.equalsIgnoreCase(SessionSave.getSession("FName", getActivity())) && Lastname.equalsIgnoreCase(SessionSave.getSession("Lname", getActivity())) && Salutation.equalsIgnoreCase(SessionSave.getSession("Salutation", getActivity())) && Email.equalsIgnoreCase(SessionSave.getSession("Email", getActivity())) && f_countrycode.equalsIgnoreCase(SessionSave.getSession("CountyCode", getActivity())) && bitmap == downImage) {
                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.no_changes));
                } else {
                    saveEditProfileChanges();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // To update the passenger profile picture from gallery or camera view.
        ProfileImg.setOnClickListener(v13 -> {
            try {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    dialog1 = Utility.alert_view_dialog(getActivity(), "",
                            "" + NC.getResources().getString(R.string.str_media),
                            "" + NC.getResources().getString(R.string.yes),
                            "" + NC.getResources().getString(R.string.no),
                            true, (dialog, which) -> ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, "");
                } else {
                    getCamera();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        left_icon_pro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        right_icon_pro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.actionSheet(getActivity(), NC.getResources().getString(R.string.confirmlogout), NC.getResources().getString(R.string.menu_logout), "", false, new AlertListener() {
                    @Override
                    public void onSuccess() {
                        logout();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
        // To logout from the application.
        LogoutBtn.setOnClickListener(v14 -> {
            Utility.actionSheet(getActivity(), NC.getResources().getString(R.string.confirmlogout), NC.getResources().getString(R.string.menu_logout), "", false, new AlertListener() {
                @Override
                public void onSuccess() {
                    logout();
                }

                @Override
                public void onFailure() {

                }
            });
        });
    }

    private void saveEditProfileChanges() {
        try {
            if (((MainHomeFragmentActivity) getActivity()).validations(MainHomeFragmentActivity.ValidateAction.isValidFirstname, getActivity(), Firstname))
                if (((MainHomeFragmentActivity) getActivity()).validations(MainHomeFragmentActivity.ValidateAction.isValidMail, getActivity(), Email))
                    if (((MainHomeFragmentActivity) getActivity()).validations(MainHomeFragmentActivity.ValidateAction.isValueNULL, getActivity(), Mobile))
                        if (!f_login_from.equals("3")) {
                            if (((MainHomeFragmentActivity) getActivity()).validations(MainHomeFragmentActivity.ValidateAction.isValidPassword, getActivity(), Password)) {
                                JSONObject j = new JSONObject();
                                j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                                j.put("email", Email);
                                j.put("phone", Mobile);
                                j.put("country_code", SessionSave.getSession("CountyCode", getActivity()));
                                j.put("salutation", Salutation);
                                j.put("firstname", Firstname);
                                j.put("lastname", Lastname);
                                j.put("password", "");
                                j.put("profile_image", encodedImage);
                                new EditProfile(j);
                            }
                        } else {
                            JSONObject j = new JSONObject();
                            j.put("passenger_id", SessionSave.getSession(PASS_ID, getActivity()));
                            j.put("email", Email);
                            j.put("phone", Mobile);
                            j.put("country_code", SessionSave.getSession("CountyCode", getActivity()));
                            j.put("salutation", Salutation);
                            j.put("firstname", Firstname);
                            j.put("lastname", Lastname);
                            j.put("password", "");
                            j.put("profile_image", encodedImage);
                            new EditProfile(j);
                        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    getCamera();
                }
            }
            break;

        }
    }

    /**
     * this method is used to get the image from gallery or camera
     */

    private void getCamera() {
        AlertDialog d = new AlertDialog.Builder(getActivity()).setMessage("" + NC.getResources().getString(R.string.choose_an_image)).setTitle("" + NC.getResources().getString(R.string.profile_image)).setCancelable(true).setNegativeButton("" + NC.getResources().getString(R.string.gallery), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                // TODO Auto-generated method stub
                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                getActivity().startActivityForResult(intent, 0);
                dialog.cancel();

            }
        }).setPositiveButton("" + NC.getResources().getString(R.string.camera), (dialog, which) -> {
            dialog.cancel();
            if (getActivity() != null) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            imageUri = FileProvider.getUriForFile(getActivity(),
                                    getActivity().getPackageName().concat(".files_root"),
                                    photoFile);
                        } else {
                            imageUri = Uri.fromFile(photoFile);
                        }

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        getActivity().startActivityForResult(takePictureIntent, 1);
                    }
                }
            }
        }).show();

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            Systems.out.println("Hellow" + resultUri);
            new ImageCompressionAsyncTask().execute(resultUri.toString());
        } else {
        }
    }

    /**
     * Function for getting image from sd card or camera
     */
    @Override
    public void onActivityResult(final int requestcode, final int resultcode, final Intent data) {
        try {
            if (requestcode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (resultcode == getActivity().RESULT_OK) {
                switch (requestcode) {
                    case 0:
                        try {
                            UCrop uCrop = UCrop.of(Uri.fromFile(new File(getRealPathFromURI(data.getDataString()))), Uri.fromFile(new File(getActivity().getCacheDir(), destinationFileName)))
                                    .withAspectRatio(4, 4)
                                    .withMaxResultSize(400, 400);
                            UCrop.Options options = new UCrop.Options();
                            options.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.header_bgcolor));
                            options.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.header_text));
                            options.setToolbarWidgetColor(ContextCompat.getColor(getActivity(), R.color.header_text));
                            uCrop.withOptions(options);
                            uCrop.start(getActivity());
                            //new ImageCompressionAsyncTask().execute(data.getDataString());
//                            CropImage.activity( Uri.parse(data.getDataString()))
//                                    .start(getContext(),this);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            //  new ImageCompressionAsyncTask().execute(imageUri.toString()).get();
//                            CropImage.activity(imageUri)
//                                    .start(getContext(),this);

                            UCrop.of(imageUri, Uri.fromFile(new File(getActivity().getCacheDir(), destinationFileName)))
                                    .withAspectRatio(4, 4)
                                    .withMaxResultSize(400, 400)
                                    .start(getActivity());
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        moveToHomePage();

     /*   if (isProfileloaded) {
            Firstname = FirstnameEdt.getText().toString().trim();
            Lastname = LastnameEdt.getText().toString().trim();
            Email = EmailEdt.getText().toString().trim();
            Mobile = MobileEdt.getText().toString().trim();
            Password = PasswordEdt.getText().toString().trim();
            Drawable drawable = ProfileImg.getDrawable();
            Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);
            if (Mobile.equalsIgnoreCase(SessionSave.getSession("Phone", getActivity())) && Firstname.equalsIgnoreCase(SessionSave.getSession("FName", getActivity())) && Lastname.equalsIgnoreCase(SessionSave.getSession("Lname", getActivity())) && Salutation.equalsIgnoreCase(SessionSave.getSession("Salutation", getActivity())) && Email.equalsIgnoreCase(SessionSave.getSession("Email", getActivity())) && f_countrycode.equalsIgnoreCase(SessionSave.getSession("CountyCode", getActivity())) && bitmap == downImage) {
                moveToHomePage();
            } else {
                dialog1 = Utility.alert_view_dialog(getActivity(), "", NC.getResources().getString(R.string.alert_message_unsaved),
                        "" + NC.getResources().getString(R.string.save), "" + NC.getResources().getString(R.string.leave), true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                               saveEditProfileChanges();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                moveToHomePage();
                            }
                        }, "");
            }
        } else {
            moveToHomePage();
        }*/
    }

    private void moveToHomePage() {
        getActivity().getSupportFragmentManager().beginTransaction().remove((getActivity()).getSupportFragmentManager().findFragmentByTag("ProfileFrag")).commit();
        ((MainHomeFragmentActivity) getActivity()).left_icon.setImageResource(R.drawable.ic_menu);
        ((MainHomeFragmentActivity) getActivity()).left_icon.setTag("menu");
        ((MainHomeFragmentActivity) getActivity()).tool_bar_lay.setVisibility(View.GONE);
    }

    /**
     * get image path from uri
     */

    public String getRealPathFromURI(final String contentURI) {
        final Uri contentUri = Uri.parse(contentURI);
        final Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null)
            return contentUri.getPath();
        else {
            cursor.moveToFirst();
            final int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void alert_view(Context mContext, String title, String message, String success_txt, String failure_txt) {
        try {
            final View view = View.inflate(mContext, R.layout.alert_view, null);
            alertmDialog = new Dialog(mContext, R.style.dialogwinddow);
            alertmDialog.setContentView(view);
            alertmDialog.setCancelable(true);
            FontHelper.applyFont(mContext, alertmDialog.findViewById(R.id.alert_id));
            alertmDialog.show();
            final TextView title_text = alertmDialog.findViewById(R.id.title_text);
            final TextView message_text = alertmDialog.findViewById(R.id.message_text);
            final Button button_success = alertmDialog.findViewById(R.id.button_success);
            final Button button_failure = alertmDialog.findViewById(R.id.button_failure);
            button_failure.setVisibility(View.GONE);
            title_text.setText(title);
            message_text.setText(message);
            button_success.setText(success_txt);
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
            button_failure.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub
                    alertmDialog.dismiss();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        TaxiUtil.mActivitylist.remove(getActivity());

        if (mshowDialog != null && mshowDialog.isShowing()) {
            mshowDialog.dismiss();
            mshowDialog = null;

        }

        if (dialog1 != null) {
            Utility.closeDialog(dialog1);
        }
        super.onDestroy();
    }

    /**
     * getActivity() method used to display the dialog window to change the password
     * <p>
     * getActivity() method used to display the dialog window to change the password
     * </p>
     */
    public void changePwdDialog() {
        final View view = View.inflate(getActivity(), R.layout.changepwd, null);
        dialog = new Dialog(getActivity(), R.style.dialogwinddow);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();

        Colorchange.ChangeColor((ViewGroup) view, getActivity());
        FontHelper.applyFont(getActivity(), dialog.findViewById(R.id.rootlay));
        final EditText oldpwd = dialog.findViewById(R.id.oldpwd);
        final EditText newpwd = dialog.findViewById(R.id.newpwd);
        final EditText confirmpwd = dialog.findViewById(R.id.confirmpwd);
        final TextView hideoldpwd = dialog.findViewById(R.id.hideoldpwd);
        final TextView hidenewpwd = dialog.findViewById(R.id.hidenewpwd);
        final TextView hideconfirmpwd = dialog.findViewById(R.id.hideconfirmpwd);
        final Button submit = dialog.findViewById(R.id.submit);
        final Button cancel = dialog.findViewById(R.id.cancel);
        confirmpwd.setImeOptions(EditorInfo.IME_ACTION_DONE);
        dialog.setCanceledOnTouchOutside(false);





        /* newly add for password hide and show*/

        hidenewpwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hidenewpwd.getText().toString().equals(NC.getResources().getString(R.string.show))) {
                    newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hidenewpwd.setText("" + NC.getResources().getString(R.string.hide));
                    FontHelper.applyFont(getActivity(), newpwd);

                } else {
                    newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hidenewpwd.setText("" + NC.getResources().getString(R.string.show));
                    FontHelper.applyFont(getActivity(), newpwd);

                }
            }
        });


        newpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                    if (newpwd.getText().toString().length() > 0)
                        hidenewpwd.setVisibility(View.VISIBLE);
                    else
                        hidenewpwd.setVisibility(View.INVISIBLE);


                } else {
                    hidenewpwd.setVisibility(View.GONE);
                    newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    FontHelper.applyFont(getActivity(), newpwd);
                    if (hidenewpwd.getText().toString().equals(NC.getResources().getString(R.string.hide))) {
                        hidenewpwd.setText("" + NC.getResources().getString(R.string.show));


                    }
                }
            }
        });
        newpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (newpwd.getText().toString().length() > 0) {
                    hidenewpwd.setVisibility(View.VISIBLE);
                } else {
                    hidenewpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        hideoldpwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hideoldpwd.getText().toString().equals(NC.getResources().getString(R.string.show))) {
                    oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hideoldpwd.setText("" + NC.getResources().getString(R.string.hide));
                    FontHelper.applyFont(getActivity(), oldpwd);

                } else {
                    oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideoldpwd.setText("" + NC.getResources().getString(R.string.show));
                    FontHelper.applyFont(getActivity(), oldpwd);
                }
            }
        });
        oldpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                    if (oldpwd.getText().toString().length() > 0)
                        hideoldpwd.setVisibility(View.VISIBLE);
                    else
                        hideoldpwd.setVisibility(View.INVISIBLE);


                } else {
                    hideoldpwd.setVisibility(View.GONE);
                    oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    FontHelper.applyFont(getActivity(), oldpwd);
                    if (hideoldpwd.getText().toString().equals(NC.getResources().getString(R.string.hide))) {
                        hideoldpwd.setText("" + NC.getResources().getString(R.string.show));
                    }

                }
            }
        });

        oldpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (oldpwd.getText().toString().length() > 0) {
                    hideoldpwd.setVisibility(View.VISIBLE);
                } else {
                    hideoldpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        hideconfirmpwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hideconfirmpwd.getText().toString().equals(NC.getResources().getString(R.string.show))) {
                    confirmpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hideconfirmpwd.setText("" + NC.getResources().getString(R.string.hide));
                    FontHelper.applyFont(getActivity(), confirmpwd);

                } else {
                    confirmpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideconfirmpwd.setText("" + NC.getResources().getString(R.string.show));
                    FontHelper.applyFont(getActivity(), confirmpwd);

                }
            }
        });
        confirmpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                    if (confirmpwd.getText().toString().length() > 0)
                        hideconfirmpwd.setVisibility(View.VISIBLE);
                    else
                        hideconfirmpwd.setVisibility(View.INVISIBLE);


                } else {
                    hideconfirmpwd.setVisibility(View.GONE);
                    confirmpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    FontHelper.applyFont(getActivity(), confirmpwd);

                    if (hideconfirmpwd.getText().toString().equals(NC.getResources().getString(R.string.hide))) {
                        hideconfirmpwd.setText("" + NC.getResources().getString(R.string.show));
                    }
                }
            }
        });

        confirmpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmpwd.getText().toString().length() > 0) {
                    hideconfirmpwd.setVisibility(View.VISIBLE);
                } else {
                    hideconfirmpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {
                    final String opwd = oldpwd.getText().toString().trim();
                    final String npwd = newpwd.getText().toString().trim();
                    final String cpwd = confirmpwd.getText().toString().trim();
                    if (opwd.length() <= 0) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.enter_the_old_password));
                        oldpwd.requestFocus();
                        //  oldpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        //    oldpwd.clearFocus();
                    } else if (npwd.length() <= 0) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.enter_the_new_password));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else if (npwd.length() < 6) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.password_min_character));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else if (cpwd.length() <= 0) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.enter_the_confirmation_password));
                        confirmpwd.requestFocus();
//                        confirmpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                        confirmpwd.clearFocus();
                    } else if (cpwd.length() < 6) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.password_min_character));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else if (!npwd.equals(cpwd)) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.confirmation_password_mismatch_with_password));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else {
                        convertMD5(opwd);
                        if (SessionSave.getSession("encrypt_password", getActivity()).equals(convertMD5_Value)) {
                            convertMD5(npwd);
                            if (opwd.equals(npwd)) {
                                CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.old_pass));
                                oldpwd.requestFocus();
//                                oldpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                                oldpwd.clearFocus();
                            } else
                                new Chngepwd(dialog, opwd, npwd);
                        } else
                            CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.old_password_mismatch_with_password));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                confirmpwd.requestFocus();
                confirmpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                dialog.dismiss();
            }
        });
    }

    /**
     * getActivity() method used to convert the string into md5
     * <p>
     * getActivity() method used to convert the string into md5
     * </p>
     *
     * @param text
     * @return string
     */
    public void convertMD5(final String text) {
        try {
            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            final byte[] array = md.digest(text.getBytes());
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i)
                sb.append(Integer.toHexString(array[i] & 0xFF | 0x100).substring(1, 3));
            // return sb.toString();
            convertMD5_Value = sb.toString();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        // return "";
    }

    /**
     * getActivity() method used to call logout API.
     */
    public void logout() {
        try {


            try {
                // TODO Auto-generated method stub
                JSONObject j = new JSONObject();
                j.put("id", SessionSave.getSession(PASS_ID, getActivity()));
                if (SessionSave.getSession(LOGOUT, getActivity()).equals("")) {
                    new Logout("type=passenger_logout", getActivity(), j);
                    ((MainHomeFragmentActivity) getActivity()).fbLogout();
                } else
                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.bookedtaxi));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }


            /*dialog1 = Utility.alert_view_dialog(getActivity(),
                    "" + NC.getResources().getString(R.string.message),
                    "" + NC.getResources().getString(R.string.confirmlogout),
                    "" + NC.getResources().getString(R.string.menu_logout),
                    "" + NC.getResources().getString(R.string.cancel), true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                dialog.dismiss();
                                // TODO Auto-generated method stub
                                JSONObject j = new JSONObject();
                                j.put("id", SessionSave.getSession(PASS_ID, getActivity()));
                                if (SessionSave.getSession(LOGOUT, getActivity()).equals("")) {
                                    new Logout("type=passenger_logout", getActivity(), j);
                                    ((MainHomeFragmentActivity) getActivity()).fbLogout();
                                } else
                                    CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.bookedtaxi));
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }, "");*/

/*
            final View view = View.inflate(getActivity(), R.layout.netcon_lay, null);
            final Dialog mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
            mDialog.setContentView(view);
            mDialog.setCancelable(false);
            mDialog.show();
            FontHelper.applyFont(getActivity(), mDialog.findViewById(R.id.alert_id));
            final TextView title_text = (TextView) mDialog.findViewById(R.id.title_text);
            final TextView message_text = (TextView) mDialog.findViewById(R.id.message_text);
            final Button button_success = (Button) mDialog.findViewById(R.id.button_success);
            final Button button_failure = (Button) mDialog.findViewById(R.id.button_failure);
            title_text.setText("" + NC.getResources().getString(R.string.message));
            message_text.setText("" + NC.getResources().getString(R.string.confirmlogout));
            button_success.setText("" + NC.getResources().getString(R.string.menu_logout));
            button_failure.setText("" + NC.getResources().getString(R.string.cancel));
            button_success.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO
                    // Auto-generated
                    // method stub
                    try {
                        mDialog.dismiss();
                        // TODO Auto-generated method stub
                        JSONObject j = new JSONObject();
                        j.put("id", SessionSave.getSession(PASS_ID, getActivity()));
                        if (SessionSave.getSession("Logout", getActivity()).equals("")) {
                            new Logout("type=passenger_logout", getActivity(), j);
                            ((MainHomeFragmentActivity) getActivity()).fbLogout();
                        } else
                            Toast.makeText(getActivity(), NC.getResources().getString(R.string.bookedtaxi), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
            button_failure.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // TODO
                    // Auto-generated
                    // method stub
                    mDialog.dismiss();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    void showPasswordSheet(){
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.password_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        Colorchange.ChangeColor((ViewGroup) sheetView, getActivity());
        FontHelper.applyFont(getActivity(), sheetView.findViewById(R.id.rootlay));
        final EditText oldpwd = sheetView.findViewById(R.id.oldpwd);
        final EditText newpwd = sheetView.findViewById(R.id.newpwd);
        final EditText confirmpwd = sheetView.findViewById(R.id.confirmpwd);
        final TextView hideoldpwd = sheetView.findViewById(R.id.hideoldpwd);
        final TextView hidenewpwd = sheetView.findViewById(R.id.hidenewpwd);
        final TextView hideconfirmpwd = sheetView.findViewById(R.id.hideconfirmpwd);
        final Button submit = sheetView.findViewById(R.id.submit);

        confirmpwd.setImeOptions(EditorInfo.IME_ACTION_DONE);

        /* newly add for password hide and show*/

        hidenewpwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hidenewpwd.getText().toString().equals(NC.getResources().getString(R.string.show))) {
                    newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hidenewpwd.setText("" + NC.getResources().getString(R.string.hide));
                    FontHelper.applyFont(getActivity(), newpwd);

                } else {
                    newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hidenewpwd.setText("" + NC.getResources().getString(R.string.show));
                    FontHelper.applyFont(getActivity(), newpwd);

                }
            }
        });


        newpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                    if (newpwd.getText().toString().length() > 0)
                        hidenewpwd.setVisibility(View.VISIBLE);
                    else
                        hidenewpwd.setVisibility(View.INVISIBLE);


                } else {
                    hidenewpwd.setVisibility(View.GONE);
                    newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    FontHelper.applyFont(getActivity(), newpwd);
                    if (hidenewpwd.getText().toString().equals(NC.getResources().getString(R.string.hide))) {
                        hidenewpwd.setText("" + NC.getResources().getString(R.string.show));


                    }
                }
            }
        });
        newpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (newpwd.getText().toString().length() > 0) {
                    hidenewpwd.setVisibility(View.VISIBLE);
                } else {
                    hidenewpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        hideoldpwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hideoldpwd.getText().toString().equals(NC.getResources().getString(R.string.show))) {
                    oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hideoldpwd.setText("" + NC.getResources().getString(R.string.hide));
                    FontHelper.applyFont(getActivity(), oldpwd);

                } else {
                    oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideoldpwd.setText("" + NC.getResources().getString(R.string.show));
                    FontHelper.applyFont(getActivity(), oldpwd);
                }
            }
        });
        oldpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                    if (oldpwd.getText().toString().length() > 0)
                        hideoldpwd.setVisibility(View.VISIBLE);
                    else
                        hideoldpwd.setVisibility(View.INVISIBLE);


                } else {
                    hideoldpwd.setVisibility(View.GONE);
                    oldpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    FontHelper.applyFont(getActivity(), oldpwd);
                    if (hideoldpwd.getText().toString().equals(NC.getResources().getString(R.string.hide))) {
                        hideoldpwd.setText("" + NC.getResources().getString(R.string.show));
                    }

                }
            }
        });

        oldpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (oldpwd.getText().toString().length() > 0) {
                    hideoldpwd.setVisibility(View.VISIBLE);
                } else {
                    hideoldpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        hideconfirmpwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (hideconfirmpwd.getText().toString().equals(NC.getResources().getString(R.string.show))) {
                    confirmpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    hideconfirmpwd.setText("" + NC.getResources().getString(R.string.hide));
                    FontHelper.applyFont(getActivity(), confirmpwd);

                } else {
                    confirmpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideconfirmpwd.setText("" + NC.getResources().getString(R.string.show));
                    FontHelper.applyFont(getActivity(), confirmpwd);

                }
            }
        });
        confirmpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {

                    if (confirmpwd.getText().toString().length() > 0)
                        hideconfirmpwd.setVisibility(View.VISIBLE);
                    else
                        hideconfirmpwd.setVisibility(View.INVISIBLE);


                } else {
                    hideconfirmpwd.setVisibility(View.GONE);
                    confirmpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    FontHelper.applyFont(getActivity(), confirmpwd);

                    if (hideconfirmpwd.getText().toString().equals(NC.getResources().getString(R.string.hide))) {
                        hideconfirmpwd.setText("" + NC.getResources().getString(R.string.show));
                    }
                }
            }
        });

        confirmpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (confirmpwd.getText().toString().length() > 0) {
                    hideconfirmpwd.setVisibility(View.VISIBLE);
                } else {
                    hideconfirmpwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                try {

                    final String opwd = oldpwd.getText().toString().trim();
                    final String npwd = newpwd.getText().toString().trim();
                    final String cpwd = confirmpwd.getText().toString().trim();
                    if (opwd.length() <= 0) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.enter_the_old_password));
                        oldpwd.requestFocus();
                        //  oldpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        //    oldpwd.clearFocus();
                    } else if (npwd.length() <= 0) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.enter_the_new_password));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else if (npwd.length() < 6) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.password_min_character));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else if (cpwd.length() <= 0) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.enter_the_confirmation_password));
                        confirmpwd.requestFocus();
//                        confirmpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                        confirmpwd.clearFocus();
                    } else if (cpwd.length() < 6) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.password_min_character));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else if (!npwd.equals(cpwd)) {
                        CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.confirmation_password_mismatch_with_password));
                        newpwd.requestFocus();
                        newpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        newpwd.clearFocus();
                    } else {
                        convertMD5(opwd);
                        if (SessionSave.getSession("encrypt_password", getActivity()).equals(convertMD5_Value)) {
                            convertMD5(npwd);
                            if (opwd.equals(npwd)) {
                                CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.old_pass));
                                oldpwd.requestFocus();
//                                oldpwd.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                                oldpwd.clearFocus();
                            } else {
                                mBottomSheetDialog.dismiss();
                                new Chngepwd(dialog, opwd, npwd);
                            }
                        } else
                            CToast.ShowToast(getActivity(), "" + NC.getResources().getString(R.string.old_password_mismatch_with_password));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();



    }

    /**
     * getActivity() asyncTask helps to update the selected image to image view from camera or gallery
     */
    private class ImageCompressionAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private Dialog mDialog;
        private String result;
        private int orientation;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            final View view = View.inflate(getActivity(), R.layout.progress_bar, null);
            mDialog = new Dialog(getActivity(), R.style.NewDialog);
            mDialog.setContentView(view);
            mDialog.setCancelable(false);
            mDialog.show();

            ImageView iv = mDialog.findViewById(R.id.giff);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
            Glide.with(getActivity())
                    .load(R.raw.loading_anim)
                    .into(imageViewTarget);
        }

        @Override
        protected Bitmap doInBackground(final String... params) {
            try {
                result = getRealPathFromURI(params[0]);
                final File file = new File(result);
                Systems.out.println("Imageee....." + file.getTotalSpace() + params[0]);
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] image = stream.toByteArray();
                encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                Systems.out.println("Imageee" + encodedImage.getBytes().length);
                Systems.out.println("Imageeen" + encodedImage.length());
            } catch (final Exception e) {
                // TODO: handle exception
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CToast.ShowToast(getActivity(), NC.getResources().getString(R.string.please_select_image_from_valid_path));
                    }
                });
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(final Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (mDialog.isShowing())
                mDialog.dismiss();

            ProfileImg.setBackgroundResource(0);
            if (result != null)
                ProfileImg.setImageBitmap(result);
        }
    }

    /**
     * getActivity() Class helps to call the Profile API and process the response to update the UI.
     */
    private class GetProfile implements APIResult {
        public GetProfile(final String string, final JSONObject data) {
            // TODO Auto-generated constructor stub
            new APIService_Retrofit_JSON_NoProgress(getActivity(), this, data, false).execute(string);
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess)
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        isProfileloaded = true;
                        final JSONArray jarry = json.getJSONArray("detail");

                        final int length = jarry.length();
                        for (int i = 0; i < length; i++) {

                            if (jarry.getJSONObject(i).has(TaxiUtil.SKIP_PASSENGER_EMAIL))
                                SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, jarry.getJSONObject(i).getString(TaxiUtil.SKIP_PASSENGER_EMAIL).equals("1"), getActivity());
                            else
                                SessionSave.saveSession(TaxiUtil.SKIP_PASSENGER_EMAIL, false, getActivity());

                            if (SessionSave.getSession(TaxiUtil.SKIP_PASSENGER_EMAIL, getActivity(), false))
                                EmailEdt.setHint(NC.getString(R.string.email_optional));
                            else
                                EmailEdt.setHint(NC.getString(R.string.email));

                            f_Firstname = jarry.getJSONObject(i).getString("name");
                            if (!f_Firstname.equals(""))
                                f_Firstname = f_Firstname.substring(0, 1).toUpperCase() + f_Firstname.substring(1);
                            f_Lastname = jarry.getJSONObject(i).getString("lastname");
                            f_Email = jarry.getJSONObject(i).getString("email");
                            f_Picture = jarry.getJSONObject(i).getString("profile_image");
                            f_Mobile = jarry.getJSONObject(i).getString("phone");
                            f_Password = jarry.getJSONObject(i).getString("password");
                            f_countrycode = jarry.getJSONObject(i).getString("country_code");
                            SessionSave.saveSession("ProfileImage", f_Picture, getActivity());
                            SessionSave.saveSession("encrypt_password", "" + jarry.getJSONObject(i).getString("password"), getActivity());
                            f_Salutation = jarry.getJSONObject(i).getString("salutation");
                            f_login_from = jarry.getJSONObject(i).getString("login_from");
                            SessionSave.saveSession(PASS_NAME, f_Firstname, getActivity());
                            SessionSave.saveSession("Email", f_Email, getActivity());
                            SessionSave.saveSession("Phone", f_Mobile, getActivity());
                            SessionSave.saveSession("FName", f_Firstname, getActivity());
                            SessionSave.saveSession("Lname", f_Lastname, getActivity());
                            SessionSave.saveSession("CountyCode", f_countrycode, getActivity());
                            SessionSave.saveSession("Salutation", f_Salutation, getActivity());
                            chngepwd.setTypeface(chngepwd.getTypeface(), Typeface.BOLD);
                            ((MainHomeFragmentActivity) getActivity()).profileEdited();
                            try {


                                for (int j = 0; j < code.size(); j++) {
                                    //  Systems.out.println("couuuu"+String.format(Locale.UK, String.valueOf(f_countrycode))+"___"+String.format(Locale.UK, String.valueOf(code.get(j).toString().trim())).trim().replaceAll("\\s","")+"__***"+String.format(Locale.UK, String.valueOf(f_countrycode)).equalsIgnoreCase(String.format(Locale.UK, String.valueOf(code.get(j).toString()))));
                                    if (String.format(Locale.UK, String.valueOf(f_countrycode)).equalsIgnoreCase(String.format(Locale.UK, code.get(j).trim()).trim().replaceAll("\\s", ""))) {
                                        //findPosition
                                        positionFirst = j;
                                        mobilecodespn.setSelection(positionFirst);
                                        Systems.out.println("spinner_value " + positionFirst);
                                        break;
                                    }
                                    // Setting Default as Kuwait if not get Country
                                    else {
                                        positionFirst = 93;
                                    }
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                            if (f_login_from.equals("0") || f_login_from.equals("1")) {
                                chngepwd.setVisibility(View.VISIBLE);
                                linechange.setVisibility(View.VISIBLE);
                            } else if (f_login_from.equals("3")) {
                                chngepwd.setVisibility(View.VISIBLE);
                                linechange.setVisibility(View.VISIBLE);
                            } else {
                                chngepwd.setVisibility(View.VISIBLE);
                                linechange.setVisibility(View.VISIBLE);
                            }
                            mHandler.sendEmptyMessage(0);
                        }
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                //Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                CToast.ShowToast(getActivity(), result);
                            }
                        });
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * getActivity() Class helps to call the Edit Profile API and process the response to update the UI.
     */
    private class EditProfile implements APIResult {
        public EditProfile(final JSONObject data) {
            // TODO Auto-generated constructor stub
            // new APIService_HTTP_JSON_WO(getActivity(), getActivity(), data, false, TaxiUtil.APIBase_Path + "lang=" + SessionSave.getSession("Lang", getActivity()) + "&" + "type=edit_passenger_profile").execute();
            new APIService_Retrofit_JSON(getActivity(), this, data, false).execute("type=edit_passenger_profile_v1");
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    final JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {
                        //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");

                        dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "");


                        SessionSave.saveSession("Email", "" + EmailEdt.getText().toString().trim(), getActivity());
                        SessionSave.saveSession("Phone", "" + MobileEdt.getText().toString().trim(), getActivity());
                        SessionSave.saveSession("FName", "" + FirstnameEdt.getText().toString().trim(), getActivity());
                        SessionSave.saveSession(PASS_NAME, FirstnameEdt.getText().toString().trim(), getActivity());
                        SessionSave.saveSession("Lname", "" + LastnameEdt.getText().toString().trim(), getActivity());
                        SessionSave.saveSession("CountyCode", f_countrycode, getActivity());
                        SessionSave.saveSession("Salutation", Salutation, getActivity());
                        SessionSave.saveSession("ProfileImage", json.getString("profile_image"), getActivity());
                        ((MainHomeFragmentActivity) getActivity()).profileEdited();
                        Drawable drawable = ProfileImg.getDrawable();
                        downImage = ImageUtils.drawableToBitmap(drawable);
                        encodedImage = "";
                    } else
                        //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), json.getString("message"), "" + NC.getResources().getString(R.string.ok), "");

                        dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), "" + json.getString("message"),
                                "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }, "");

                } catch (final Exception e) {
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        CToast.ShowToast(getActivity(), result);
                    }
                });
            }
        }
    }

    /**
     * getActivity() class helps to call the change the password.
     */
    class Chngepwd implements APIResult {
        private Dialog dialog;

        public Chngepwd(final Dialog dialog, final String oldpwd, final String newpwd) {
            try {
                this.dialog = dialog;
                JSONObject j = new JSONObject();
                j.put("id", SessionSave.getSession(PASS_ID, getActivity()));


                j.put("new_password", newpwd);
                j.put("confirm_password", newpwd);
                j.put("old_password", SessionSave.getSession("encrypt_password", getActivity()));
                new APIService_Retrofit_JSON(getActivity(), this, j, false).execute("type=chg_password_passenger");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        @Override
        public void getResult(final boolean isSuccess, final String result) {
            try {
                if (isSuccess) {
                    final JSONObject obj = new JSONObject(result);
                    final String msg = obj.getString("message");
                    final String status = obj.getString("status");
                    SessionSave.saveSession("encrypt_password", "" + convertMD5_Value, getActivity());
                    if (status.equals("1"))
                        dialog.cancel();
                    //alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), msg, "" + NC.getResources().getString(R.string.ok), "");
                    dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), msg,
                            "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, "");

                } else
                    // alert_view(getActivity(), "" + NC.getResources().getString(R.string.message), result, "" + NC.getResources().getString(R.string.ok), "");
                    dialog1 = Utility.alert_view_dialog(getActivity(), "" + NC.getResources().getString(R.string.message), result,
                            "" + NC.getResources().getString(R.string.ok), "", true, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }, "");

            } catch (final JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}