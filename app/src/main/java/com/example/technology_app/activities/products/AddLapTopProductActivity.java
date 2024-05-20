package com.example.technology_app.activities.products;

import static com.example.technology_app.utils.GlobalVariable.storage_permissions_33;
import static com.example.technology_app.utils.GlobalVariable.storage_permissons;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.models.AddProduct.AddProductModel;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.example.technology_app.utils.RealPathUtil;

import java.io.File;
import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLapTopProductActivity extends AppCompatActivity {

    EditText edtName, edtSalePrice, edtLeft;
    Button btnAddProduct, btnChooseImg;
    ImageView imgProduct;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String userId, accessToken;
    private Uri mUri;
    private ProgressDialog mProgressDialog;
    public  static final int MY_REQUEST_CODE = 100;
    public static final String TAG = MainActivity.class.getName();

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data == null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgProduct.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lap_top_product);
        initView();
        mProgressDialog = new ProgressDialog(AddLapTopProductActivity.this);
        mProgressDialog.setMessage("Please wait for upload");
        initControl();
    }

    private void initControl() {
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUri != null){
                    UploadImage(userId, accessToken, edtName.getText().toString(), edtSalePrice.getText().toString(), edtLeft.getText().toString());
                }
            }
        });
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }

    private void UploadImage(String userId, String accessToken, String name, String salePrice, String left) {
        assert userId != null;
        assert accessToken != null;
        RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestBodySalePrice = RequestBody.create(MediaType.parse("multipart/form-data"), salePrice);
        RequestBody requestBodyLeft = RequestBody.create(MediaType.parse("multipart/form-data"), left);
        RequestBody requestBodyCategory = RequestBody.create(MediaType.parse("multipart/form-data"), "662a1b76f2eef23c711b2989");
        RequestBody requestBodyType = RequestBody.create(MediaType.parse("multipart/form-data"), "laptop");


        mProgressDialog.show();
        String imagePath = RealPathUtil.getRealPath(this, mUri);
        String mimeType = "image/jpeg";
        File file = new File(imagePath);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), requestFile);
        api.addProduct(userId, accessToken, requestBodyName, requestBodySalePrice, part, requestBodyCategory, requestBodyType, requestBodyLeft).enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(@NonNull Call<AddProductModel> call, @NonNull Response<AddProductModel> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    AddProductModel addProductModel = response.body();
                    if (addProductModel.getStatus() == 201) {
                        Glide.with(AddLapTopProductActivity.this).load(addProductModel.getMetadata().getProduct().getImages().get(0)).into(imgProduct);
                        Toast.makeText(AddLapTopProductActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LaptopActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddLapTopProductActivity.this, "Upload failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        Log.e("API_ERROR", "Response code: " + response.code());
                        assert response.errorBody() != null;
                        Log.e("API_ERROR", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AddLapTopProductActivity.this, "Response body is null", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<AddProductModel> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Log.e( "TAG", t.toString());
                Toast.makeText( AddLapTopProductActivity.this, "Gọi API thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        Paper.init(this);
        userId = Paper.book().read("userId");
        accessToken = Paper.book().read("accessToken");
        edtName = findViewById(R.id.editProductName);
        edtSalePrice = findViewById(R.id.editSalePrice);
        edtLeft = findViewById(R.id.editLeftProduct);
        imgProduct = findViewById(R.id.imgProduct);
        btnChooseImg = findViewById(R.id.btnChooseImg);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
    }


    public static String[] permissions(){
        String[] p;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            p = storage_permissions_33;
        }else{
            p = storage_permissons;
        }
        return p;
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}