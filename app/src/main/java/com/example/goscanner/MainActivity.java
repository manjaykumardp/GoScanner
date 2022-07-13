package com.example.goscanner;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.scanbtn);
        btn2 = findViewById(R.id.genbtn);
  // permission for camera
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
            }
        }
            btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Scancode();
            }
        });
         btn2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this,GenerateActivity.class);
                 startActivity(i);
                 overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
             }
         });
    }

    private void Scancode() {
        Toast.makeText(this,"Scan Code",Toast.LENGTH_SHORT).show();
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result==null)
        {
            super.onActivityResult(requestCode,resultCode,data);
        }else{
            String r = result.getContents();
            if(r==null){

            }else{
                showBox(r);
            }
        }
    }




    private void showBox(String r) {
        AlertDialog.Builder bl = new AlertDialog.Builder(this);
        bl.setTitle("Scanned Result");
        bl.setMessage(r);
        bl.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });
        bl.create().show();

    }
}