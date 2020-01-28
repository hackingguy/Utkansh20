package com.rocketapp.utkansh20;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRScanner extends AppCompatActivity {

    public void setQRcode() {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("https://www.google.com", BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = findViewById(R.id.qrCode);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        setQRcode();
    }
}