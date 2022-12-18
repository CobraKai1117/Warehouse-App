package com.zybooks.warehouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class mainInventoryPage extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    String phoneNumberValue;

    ImageView imageView;

    GridView inventoryGrid;
    ArrayList<WarehouseProducts> productList;
    ProductListAdapter adapter = null;


    Button selectImage,addProduct,updateProduct;
    ProductDatabaseManager inventoryDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_main_page);

        inventoryGrid = (GridView) findViewById(R.id.inventoryGridView);
        productList = new ArrayList<>();
        adapter = new ProductListAdapter(this, R.layout.warehouse_items, productList);
        inventoryGrid.setAdapter(adapter);
        inventoryDatabase = new ProductDatabaseManager(this);


        // Get all data from database
        Cursor cursor = inventoryDatabase.getData("SELECT * FROM PRODUCTS");
        productList.clear();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String productName = cursor.getString(1);
            String SKUCode = cursor.getString(2);
            Integer stockOnHand = cursor.getInt(3);
            byte[] productImage = cursor.getBlob(4);


            productList.add(new WarehouseProducts(productName, SKUCode, stockOnHand, productImage));

        }

        adapter.notifyDataSetChanged();


        inventoryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(mainInventoryPage.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int optionChoice) {
                        if (optionChoice == 0)
                        {
                            Cursor c = inventoryDatabase.getData("SELECT ID FROM PRODUCTS");
                            ArrayList<Integer> arrayID = new ArrayList<Integer>();
                            while(c.moveToNext())
                            {
                                arrayID.add(c.getInt(0));
                            }
                            showDialogUpdate(mainInventoryPage.this,arrayID.get(position));

                        }
                        else
                        {
                            Cursor c = inventoryDatabase.getData("SELECT ID FROM PRODUCTS");
                            ArrayList<Integer> arrayID = new ArrayList<Integer>();
                            while(c.moveToNext())
                            {
                                arrayID.add(c.getInt(0));
                            }
                            showDialogDelete(arrayID.get(position));

                        }
                    }

                });
                dialog.show();
            }

        });


        addProduct = (Button) findViewById(R.id.addInventoryItem);
        addProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent addProductI = new Intent(getApplicationContext(),AddProduct.class);
                startActivity(addProductI);
            }
        });



        GridView gridView = findViewById(R.id.inventoryGridView);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {  // If permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {

            } else {  // Asks user for permission to send notifications
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
    }

    void showDialogUpdate(Activity activity,int position) // Displays dialog so user can update item count
    {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_inventory_product);
        dialog.setTitle("Update Inventory Count");
        ImageView productImage = (ImageView) dialog.findViewById(R.id.imageViewProduct);
        EditText editCount = (EditText) dialog.findViewById(R.id.inventoryCount);
        Button updateCount = (Button) dialog.findViewById(R.id.updateButton);

        updateCount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    Integer revisedStock;
                    inventoryDatabase.updateInventoryStock(revisedStock = Integer.parseInt(editCount.getText().toString().trim()),position);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_SHORT).show();
                }

                catch(Exception error)
                {
                    error.printStackTrace();
                }

                updateProductList();

            }
        });

        // Set width and height of dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.70);
        dialog.getWindow().setLayout(width,height);
        dialog.show();
    }


    private void showDialogDelete(int productID)
    {
        AlertDialog.Builder dialogDelete =  new AlertDialog.Builder(mainInventoryPage.this);

        dialogDelete.setTitle("Alert!");
        dialogDelete.setMessage("This will remove the product from the database\n Do you wish to proceed?");

        dialogDelete.setPositiveButton("No",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int choice)
            {
                Toast.makeText(getApplicationContext(), "Deletion cancelled", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialogDelete.setNegativeButton("Yes",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int choice)
            {
                try {
                    inventoryDatabase.deleteData(productID);
                    Toast.makeText(getApplicationContext(), "Product has been successfully deleted from database", Toast.LENGTH_SHORT).show();
                }
                catch(Exception error)
                {
                    error.printStackTrace();
                }

                updateProductList();
            }
        });



        dialogDelete.show();
    }


    void updateProductList()
    {
        // Get all data from database
        Cursor cursor = inventoryDatabase.getData("SELECT * FROM PRODUCTS");
        productList.clear();
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String productName = cursor.getString(1);
            String SKUCode = cursor.getString(2);
            Integer stockOnHand = cursor.getInt(3);
            byte[] productImage = cursor.getBlob(4);


            productList.add(new WarehouseProducts(productName, SKUCode, stockOnHand, productImage));

        }

        adapter.notifyDataSetChanged();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) { // Requests permission from the user to receive text messages
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Notifications are permitted", Toast.LENGTH_LONG).show();

                    SmsManager smsManager = SmsManager.getDefault();


                    AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.phone_number_input, null);
                    EditText numberInput = (EditText) dialogView.findViewById(R.id.inputPhoneNumber);
                    Button numberSubmit = (Button) dialogView.findViewById(R.id.submitNumberButton);
                    numberSubmit.setOnClickListener(new View.OnClickListener() {

                        @Override

                        public void onClick(View view) {

                            phoneNumberValue = numberInput.getText().toString().trim(); // Convert value to string and remove any spaces between characters.

                            boolean allNumbers = phoneNumberValue.matches("[0-9]+");

                            if (allNumbers)
                            {
                                String message = "Low inventory stock on the following items: " +  // Placeholder text message
                                        "\n" + "* Samsung 58 inch TU690T Series LED, SKU: 1456759" + "\n" + "* Surface Laptop Go 2 Platinum, SKU 9950255";
                                smsManager.sendTextMessage(phoneNumberValue, null,message, null, null);
                                dialogBuilder.dismiss();

                            }

                            else{Toast.makeText(view.getContext(),"Please enter a valid number",Toast.LENGTH_LONG).show(); }


                        }

                    });

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();

                } else {
                    Toast.makeText(this, "Notifications are not enabled", Toast.LENGTH_LONG).show();
                }
            break; }

        }
    }

}

