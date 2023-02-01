package com.moovex.driver;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.moovex.R;
import com.moovex.driver.data.apiData.ModelListInfo;
import com.moovex.driver.interfaces.DriverAPIResult;
import com.moovex.driver.interfaces.DriverClickInterface;
import com.moovex.driver.service.DriverAPIService_Retrofit_JSON;
import com.moovex.driver.utils.DriverCToast;
import com.moovex.driver.utils.DriverNC;
import com.moovex.driver.utils.DriverSessionSave;
import com.moovex.driver.utils.DriverSystems;
import com.moovex.driver.interfaces.AlertListener;
import com.moovex.util.Colorchange;
import com.moovex.util.Utility;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.moovex.driver.data.DriverCommonData.getDateForCreateImageFile;

public class DriverAddFleetAct extends AppCompatActivity implements DriverClickInterface {
    EditText vehicle_number, vehicle_manufacturer;
    TextView upload_img, upload_vehicle_img, select_model, vehicle_owner_name;
    Button save_btn;
    private Dialog cameraDialog, dialog1;
    private Uri imageUri;
    AppCompatImageView vehicle_img;

    LinearLayout identification_pic_txt;
    ListView listView;
    ArrayAdapter<String> adapter;
    private Bitmap mBitmap;
    private AlertDialog alertDialog;
    private String file_name = "";
    private String encodedImage = "";
    private final String destinationFileName = "profileImage";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 113;
    private ModellistAdapter modellistAdapter;
    private ArrayList<ModelListInfo> modelListInfos;

    String model_details = "";
    String owner_name = "";
    private String model_id, model_name = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_add_fleet);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && !TextUtils.isEmpty(bundle.getString("model_details"))) {
            model_details = bundle.getString("model_details");
            owner_name = bundle.getString("owner_name");
            Log.e("model_details", model_details);
            try {

                JSONArray modeldetailArray = new JSONArray(model_details);
                modelListInfos = new ArrayList<>();

                for (int i = 0; i < modeldetailArray.length(); i++) {
                    ModelListInfo modelListInfo = new ModelListInfo();
                    modelListInfo._id = modeldetailArray.getJSONObject(i).getString("_id");
                    modelListInfo.model_name = modeldetailArray.getJSONObject(i).getString("model_name");
                    modelListInfos.add(modelListInfo);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        vehicle_number = findViewById(R.id.vehicle_number);
        select_model = findViewById(R.id.select_model);
        vehicle_owner_name = findViewById(R.id.vehicle_owner_name);
        vehicle_manufacturer = findViewById(R.id.vehicle_manufacturer);
//        upload_vehicle_img = findViewById(R.id.upload_vehicle_img);
        identification_pic_txt = findViewById(R.id.identification_pic_txt);
        vehicle_img = findViewById(R.id.img_identification);
        upload_img = findViewById(R.id.upload_img);


        save_btn = findViewById(R.id.save_btn);
        vehicle_owner_name.setText(owner_name);

        select_model.setOnClickListener(view -> openModellist());


        save_btn.setOnClickListener(view -> {
            if (vehicle_number.getText().toString().trim().isEmpty()) {
                DriverCToast.ShowToast(DriverAddFleetAct.this, DriverNC.getResources().getString(R.string.enter_the_num));
            } else if (select_model.getText().toString().trim().isEmpty()) {
                DriverCToast.ShowToast(DriverAddFleetAct.this, DriverNC.getResources().getString(R.string.select_the_model));
            } else if (vehicle_manufacturer.getText().toString().trim().isEmpty()) {
                DriverCToast.ShowToast(DriverAddFleetAct.this, DriverNC.getResources().getString(R.string.enter_vehicle_manufacturer));
            } else if (vehicle_img.getDrawable() == null) {
                DriverCToast.ShowToast(DriverAddFleetAct.this, DriverNC.getResources().getString(R.string.upload_image_file));
            } else {
                String url = "type=saveNewFleet";
                new AddFleet(url);
            }
        });


        vehicle_img.setOnClickListener(view -> {
            try {
                if (ActivityCompat.checkSelfPermission(DriverAddFleetAct.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(DriverAddFleetAct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Utility.actionSheet(DriverAddFleetAct.this, DriverNC.getResources().getString(R.string.str_media), DriverNC.getResources().getString(R.string.yes), "", false, new AlertListener() {
                        @Override
                        public void onSuccess() {
                            ActivityCompat.requestPermissions(DriverAddFleetAct.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                    /*
                    dialog1 = Driver_Utils.alert_view_dialog(DriverAddFleetAct.this, "", DriverNC.getResources().getString(R.string.str_media), DriverNC.getResources().getString(R.string.yes), "", true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            ActivityCompat.requestPermissions(DriverAddFleetAct.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                            dialog.dismiss();
                        }
                    }, (dialogInterface, i) -> dialogInterface.dismiss(), "");

                     */
                } else getCamera();
            } catch (Exception e) {

                // TODO: handle exception
            }
        });
    }

    private void openModellist() {

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DriverAddFleetAct.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_list_dialog, viewGroup, false);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(true);
        TextView title = dialogView.findViewById(R.id.tv_title);
        title.setText("Select Model");
        RecyclerView reason_recycle = dialogView.findViewById(R.id.reason_recycle);
        LinearLayoutManager layoutManagercancel;
        layoutManagercancel = new LinearLayoutManager(DriverAddFleetAct.this);
        reason_recycle.setLayoutManager(layoutManagercancel);
        modellistAdapter = new ModellistAdapter(DriverAddFleetAct.this, modelListInfos);
        reason_recycle.setAdapter(modellistAdapter);
        alertDialog.show();
    }


    private void getCamera() {

        Utility.actionSheet(DriverAddFleetAct.this, "" + DriverNC.getResources().getString(R.string.choose_an_image), "" + DriverNC.getResources().getString(R.string.camera), "" + DriverNC.getResources().getString(R.string.gallery), false, new AlertListener() {
            @Override
            public void onSuccess() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
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
                            imageUri = FileProvider.getUriForFile(DriverAddFleetAct.this, DriverAddFleetAct.this.getPackageName().concat(".files_root"), photoFile);
                        } else {
                            imageUri = Uri.fromFile(photoFile);
                        }

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePictureIntent, 1);
                    }
                }
            }

            @Override
            public void onFailure() {
                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 0);
            }
        });
        /*
        dialog1 = Driver_Utils.alert_view_dialog(this, DriverNC.getResources().getString(R.string.profile_image), DriverNC.getResources().getString(R.string.choose_an_image), DriverNC.getResources().getString(R.string.camera), DriverNC.getResources().getString(R.string.gallery), true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
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
                            imageUri = FileProvider.getUriForFile(DriverAddFleetAct.this,
                                    DriverAddFleetAct.this.getPackageName().concat(".files_root"),
                                    photoFile);
                        } else {
                            imageUri = Uri.fromFile(photoFile);
                        }

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(takePictureIntent, 1);
                    }
                }


            }
        }, (dialog, i) -> {
            final Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, 0);
            dialog.cancel();
        }, "");

         */
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = getDateForCreateImageFile();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);

        return image;
    }


    @Override
    public void onActivityResult(final int requestcode, final int resultcode, final Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        try {
            if (requestcode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else if (resultcode == RESULT_OK) {
                switch (requestcode) {
                    case 0:
                        try {
                            file_name = getRealPathFromURI(data.getDataString());
                            UCrop uCrop = UCrop.of(Uri.fromFile(new File(getRealPathFromURI(data.getDataString()))), Uri.fromFile(new File(DriverAddFleetAct.this.getCacheDir(), destinationFileName))).useSourceImageAspectRatio().withAspectRatio(1, 1).withMaxResultSize(400, 400);
                            UCrop.Options options = new UCrop.Options();
                            options.setToolbarColor(ContextCompat.getColor(DriverAddFleetAct.this, R.color.appbg));
                            options.setStatusBarColor(ContextCompat.getColor(DriverAddFleetAct.this, R.color.header_text));
                            options.setToolbarWidgetColor(ContextCompat.getColor(DriverAddFleetAct.this, R.color.header_text));
                            options.setMaxBitmapSize(1000000000);
                            uCrop.withOptions(options);
                            uCrop.start(DriverAddFleetAct.this);
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            file_name = imageUri.getPath();
                            UCrop.of(imageUri, Uri.fromFile(new File(DriverAddFleetAct.this.getCacheDir(), destinationFileName))).withAspectRatio(1, 1).withMaxResultSize(2000, 2000).start(DriverAddFleetAct.this);
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

    private String getRealPathFromURI(final String contentURI) {

        final Uri contentUri = Uri.parse(contentURI);
        final Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) return contentUri.getPath();
        else {
            cursor.moveToFirst();
            final int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            // ResultActivity.startWithUri(SampleActivity.this, resultUri);
            DriverSystems.out.println("Hellow" + resultUri);
            new DriverAddFleetAct.ImageCompressionAsyncTask().execute(resultUri.toString());
        } else {
            // Toast.makeText(SampleActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void positiveButtonClick(DialogInterface dialog, int id, String s) {

    }

    @Override
    public void negativeButtonClick(DialogInterface dialog, int id, String s) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class ImageCompressionAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private Dialog mDialog;
        private String result;
        private int orientation;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            final View view = View.inflate(DriverAddFleetAct.this, R.layout.driver_progress_bar, null);
            mDialog = new Dialog(DriverAddFleetAct.this, R.style.NewDialog);
            mDialog.setContentView(view);
            mDialog.setCancelable(false);
            mDialog.show();

            ImageView iv = mDialog.findViewById(R.id.giff);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
            Glide.with(DriverAddFleetAct.this).load(R.raw.driver_loading_anim).into(imageViewTarget);
        }

        @Override
        protected Bitmap doInBackground(final String... params) {
            try {
                result = getRealPathFromURI(params[0]);
                final File file = new File(result);
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final byte[] image = stream.toByteArray();
                encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
            } catch (final Exception e) {
                // TODO: handle exception
                runOnUiThread(() -> DriverCToast.ShowToast(DriverAddFleetAct.this, DriverNC.getResources().getString(R.string.image_failed)));
            }
            return mBitmap;
        }

        @Override
        protected void onPostExecute(final Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (DriverAddFleetAct.this != null && mDialog.isShowing()) mDialog.dismiss();
                {
//                    upload_vehicle_img.setText(file_name);
                    identification_pic_txt.setVisibility(View.GONE);
                    vehicle_img.setBackgroundResource(0);
                }
                if (result != null) {
                    identification_pic_txt.setVisibility(View.GONE);
                    vehicle_img.setImageBitmap(result);
//                    upload_vehicle_img.setText(file_name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class AddFleet implements DriverAPIResult {
        String msg = "";

        public AddFleet(String url) {

            try {
                JSONObject j = new JSONObject();
                j.put("driver_id", DriverSessionSave.getSession("Id", DriverAddFleetAct.this));
                j.put("company_id", DriverSessionSave.getSession("fleet_company_id", DriverAddFleetAct.this));
                j.put("vehicle_number", vehicle_number.getText().toString().trim());
                j.put("selected_model", model_id);
                j.put("vehicle_owner_name", vehicle_owner_name.getText().toString().trim());
                j.put("vehicle_manufacturer", vehicle_manufacturer.getText().toString().trim());
                j.put("vehicle_image", encodedImage);
                if (isOnline()) {
                    new DriverAPIService_Retrofit_JSON(DriverAddFleetAct.this, this, j, false).execute(url);
                } else {
                    DriverCToast.ShowToast(DriverAddFleetAct.this, "" + DriverNC.getResources().getString(R.string.check_net_connection));
//                    dialog1 = Driver_Utils.alert_view(DriverAddFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + DriverNC.getResources().getString(R.string.check_net_connection), "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverAddFleetAct.this, "");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

        @Override
        public void getResult(boolean isSuccess, String result) {

            try {
                if (isSuccess) {
                    JSONObject json = new JSONObject(result);
                    if (json.getInt("status") == 1) {

                        Intent intent = new Intent(DriverAddFleetAct.this, DriverMyStatus.class);
                        startActivity(intent);

                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverAddFleetAct.this, msg);
                    } else {
                        msg = json.getString("message");
                        DriverCToast.ShowToast(DriverAddFleetAct.this, msg);
//                        dialog1 = Driver_Utils.alert_view(DriverAddFleetAct.this, "" + DriverNC.getResources().getString(R.string.message), "" + msg, "" + DriverNC.getResources().getString(R.string.ok), "", true, DriverAddFleetAct.this, "");
                    }
                } else {
                    runOnUiThread(() -> DriverCToast.ShowToast(DriverAddFleetAct.this, DriverNC.getString(R.string.server_error)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public boolean isOnline() {

        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (NetworkInfo networkInfo : info)
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
        }
        return false;
    }

    private class ModellistAdapter extends RecyclerView.Adapter<ModellistAdapter.ViewHolder> {

        Context context;
        private final List<ModelListInfo> modelListData;


        public ModellistAdapter(Context context, List<ModelListInfo> data) {
            this.context = context;
            this.modelListData = data;
        }

        @NonNull
        @Override
        public ModellistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(DriverAddFleetAct.this);
            View view;
            view = inflater.inflate(R.layout.model_list, parent, false);
            Colorchange.ChangeColor((ViewGroup) view, DriverAddFleetAct.this);
            return new ModellistAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ModellistAdapter.ViewHolder holder, int position) {
            holder.reasontxt.setText(modelListData.get(position).model_name);
            holder.reasontxt.setOnClickListener(v -> {
                model_id = modelListData.get(position)._id;
                model_name = modelListData.get(position).model_name;
                notifyDataSetChanged();
                cancelModelDialog();

            });
            holder.lay_reason.setOnClickListener(v -> {

                model_id = modelListData.get(position)._id;
                model_name = modelListData.get(position).model_name;
                notifyDataSetChanged();
                cancelModelDialog();

            });
        }

        @Override
        public int getItemCount() {
            return modelListData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView reasontxt;
            AppCompatImageView check1;
            RelativeLayout lay_reason;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                reasontxt = itemView.findViewById(R.id.reason_txt);
                check1 = itemView.findViewById(R.id.check1);
                lay_reason = itemView.findViewById(R.id.lay_reason);
            }

        }
    }

    private void cancelModelDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        select_model.setText(model_name);
    }
}

