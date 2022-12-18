package com.zybooks.warehouseapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;




import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter
{
    private Context context;
    private int layout;
    private ArrayList<WarehouseProducts> productList;

    public ProductListAdapter(Context context, int layout, ArrayList<WarehouseProducts> productList)
    {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder
    {
        ImageView productImage;
        TextView productName,SKUCode;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View tableRow = view;
        ViewHolder holder = new ViewHolder();

        if(tableRow==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            tableRow = inflater.inflate(layout,null);

            holder.productName = (TextView)  tableRow.findViewById(R.id.productName);
            holder.SKUCode = (TextView) tableRow.findViewById(R.id.SKUCode);
            holder.productImage = (ImageView) tableRow.findViewById(R.id.productImage);
            tableRow.setTag(holder);

        }

        else
        {
           holder = (ViewHolder) tableRow.getTag();
        }

        WarehouseProducts product = productList.get(position);

        holder.productName.setText(product.getProductName());
        holder.SKUCode.setText(product.getSKUCode());

        byte[] productImage = product.getProductImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage,0,productImage.length);
        holder.productImage.setImageBitmap(bitmap);

        return tableRow;
    }


}
