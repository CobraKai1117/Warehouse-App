package com.zybooks.warehouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddProduct extends AppCompatActivity {

    EditText productName,SKUCode;
    ImageView productImage;
    Button cancelButton,addButton,addImage;
    final int REQUEST_CODE_GALLERY = 999;
    String productNameValue,SKUCodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        cancelButton = findViewById(R.id.cancelButton);
        addButton = findViewById(R.id.addProductButton);
        addImage = findViewById(R.id.selectImageButton);
        productName = findViewById(R.id.productNameValue);
        SKUCode = findViewById(R.id.SKUCodeValue);
        productImage = findViewById(R.id.addProductImage);



        // ProductDatabaseManager productDB = new ProductDatabaseManager(AddProduct.this,"ProductDB",null,1);

       //  productDB.queryData("CREATE TABLE IF NOT EXISTS PRODUCT (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, SKU VARCHAR, image BLOG)");

        ProductDatabaseManager productDatabase = new ProductDatabaseManager(this);

         addImage.setOnClickListener(new View.OnClickListener()
          {
             @Override
              public void onClick(View view)
                {  productImage = findViewById(R.id.addProductImage);
                    productImage.setRotation(90); // Rotates Preview Image to portrait instead of landscape view

                    ActivityCompat.requestPermissions(
                        AddProduct.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                }



          });

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                productNameValue = productName.getText().toString().trim();
                SKUCodeValue = SKUCode.getText().toString().trim();
                productImage = findViewById(R.id.addProductImage);

                if((productNameValue != null && !productNameValue.isEmpty() && (SKUCodeValue!=null && !SKUCodeValue.isEmpty())))
                {
                    try {
                        productDatabase.insertData(
                                productNameValue,
                                SKUCodeValue,
                                0,
                                imageViewToByte(productImage)
                        );

                        Toast.makeText(getApplicationContext(), "Product added successfully!", Toast.LENGTH_SHORT).show();
                        productNameValue = "";
                        SKUCodeValue = "";
                        productName.getText().clear();
                        SKUCode.getText().clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent mainInventoryPageI = new Intent(getApplicationContext(),mainInventoryPage.class);
                startActivity(mainInventoryPageI); // Go back to inventory page and close current activity
                finish();
            }
        });




    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true); // Rotate Image by 90 degrees convert from landscape to portrait
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP,50,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[]grantResults) {



        if(requestCode == REQUEST_CODE_GALLERY) // If user agrees, the app can access their phone gallery, otherwise it cannot
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent selectImageI = new Intent(Intent.ACTION_PICK);
                selectImageI.setType("image/*");
                startActivityForResult(selectImageI,REQUEST_CODE_GALLERY);
            }

            else
            {
                Toast.makeText(getApplicationContext(),"You don't have permission to access file location!",Toast.LENGTH_SHORT).show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) // Takes image file converts it to Bitmap, compresses the image and returns the value to productImage
    {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null)
        {
            Uri uri = data.getData();

            try
            {

                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                productImage.setImageBitmap(bitmap);


            }

            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }




}