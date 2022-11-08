package com.taximobility.roomDB;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.taximobility.R;
import com.google.gson.Gson;
import com.taximobility.databinding.ActivityFindLogBinding;
import com.taximobility.util.Systems;

import java.util.List;

public class FindLogActivity extends AppCompatActivity {
    private LoggerViewModel viewModel;
    private EditText mOriginLat, mOriginLng, mDestLat, mDestLng;
    private ActivityFindLogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_log);
//        binding.setModel(CommonData.getStringModel());

        viewModel = ViewModelProviders.of(FindLogActivity.this).get(LoggerViewModel.class);

       /* mOriginLat = findViewById(R.id.edOriginLat);
        mOriginLng = findViewById(R.id.edOriginLng);
        mDestLat = findViewById(R.id.edDestLat);
        mDestLng = findViewById(R.id.edDestLng);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
//        String url = " https://maps.googleapis.com/maps/api/directions/json?origin=11.0311341,77.0170433&destination=11.3410364,77.7171642";



        binding.btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String originLat = binding.edOriginLat.getText().toString().trim();
                final String originLng = binding.edOriginLng.getText().toString().trim();
                final String destLat = binding.edDestLat.getText().toString().trim();
                final String destLng = binding.edDestLng.getText().toString().trim();
                Systems.out.println("hloooooo " + originLat + "__" + originLng + "__" + destLat + "__" + destLng + "__");
                if (!originLat.equals("") && !originLng.equals("") && !destLat.equals("") && !destLng.equals("")) {
                    StringBuilder urlString = new StringBuilder();
                    urlString.append("https://maps.googleapis.com/maps/api/directions/json");
                    urlString.append("?origin=");
                    urlString.append(originLat);
                    urlString.append(",");
                    urlString.append(originLng);
                    urlString.append("&destination=");
                    urlString.append(destLat);
                    urlString.append(",");
                    urlString.append(destLng);

                    getDetailsFromDb(urlString.toString().trim());

                } else {
                    Toast.makeText(FindLogActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String originLat = binding.edOriginLat.getText().toString().trim();
                final String originLng = binding.edOriginLng.getText().toString().trim();
                final String destLat = binding.edDestLat.getText().toString().trim();
                final String destLng = binding.edDestLng.getText().toString().trim();
                Systems.out.println("hloooooo btnDistance" + originLat + "__" + originLng + "__" + destLat + "__" + destLng + "__");
                if (!originLat.equals("") && !originLng.equals("") && !destLat.equals("") && !destLng.equals("")) {
                    StringBuilder urlString = new StringBuilder();
                    urlString.append("https://maps.googleapis.com/maps/api/distancematrix/json");
                    urlString.append("?origins=");
                    urlString.append(originLat);
                    urlString.append(",");
                    urlString.append(originLng);
                    urlString.append("&destinations=");
                    urlString.append(destLat);
                    urlString.append(",");
                    urlString.append(destLng);
                    getDetailsFromDb(urlString.toString().trim());
                } else {
                    Toast.makeText(FindLogActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnGeocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String originLat = binding.edOriginLat.getText().toString().trim();
                final String originLng = binding.edOriginLng.getText().toString().trim();
                Systems.out.println("hloooooo btnGeocode" + originLat + "__" + originLng);
                if (!originLat.equals("") && !originLng.equals("")) {
                    StringBuilder urlString = new StringBuilder();
                    urlString.append("https://maps.googleapis.com/maps/api/geocode/json");
                    urlString.append("?latlng=");
                    urlString.append(originLat);
                    urlString.append(",");
                    urlString.append(originLng);
                    urlString.append("&sensor=false&key=AIzaSyAgarkJ7MxJDuo81Kkv4RXLEhL5IumDXAU");

                    getDetailsFromDb(urlString.toString().trim());
                } else {
                    Toast.makeText(FindLogActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDetailsFromDb(String urlString) {
//                    String url ="https://maps.googleapis.com/maps/api/directions/json?origin=11.0317664,77.0186759&destination=11.031771,77.0186863";
        Systems.out.println("hloooooo " + "urlString " + urlString);


        viewModel.logsByQuery(urlString).observe(FindLogActivity.this, new Observer<List<LoggerModel>>() {
            @Override
            public void onChanged(@Nullable List<LoggerModel> loggerModels) {
                if (loggerModels != null && loggerModels.size() != 0) {
                    binding.textResult.setText(new Gson().toJson(loggerModels.get(0)));
                } else {
                    binding.textResult.setText("No Values");
//                                Toast.makeText(FindLogActivity.this, "No Values", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}