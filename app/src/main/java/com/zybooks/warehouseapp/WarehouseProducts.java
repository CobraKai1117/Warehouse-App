package com.zybooks.warehouseapp;

public class WarehouseProducts
{
    private String productName;
    private String SKUCode;
    private Integer stockOnHand;
    private byte[] productImage;

    public WarehouseProducts(String productName, String SKUCode, Integer stockOnHand, byte[] productImage)
    {
        this.productName = productName;
        this.SKUCode = SKUCode;
        this.stockOnHand = stockOnHand;
        this.productImage = productImage;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName()
    {
        this.productName = productName;
    }

    public String getSKUCode()
    {
        return SKUCode;
    }

    public void setSKUCode()
    {
        this.SKUCode = SKUCode;
    }

    public Integer getStockOnHand() {return stockOnHand; }

    public void setStockOnHand() { this.stockOnHand = stockOnHand;}

    public byte[] getProductImage()
    {
        return productImage;
    }

    public void setProductImage()
    {
        this.productImage = productImage;
    }





}
