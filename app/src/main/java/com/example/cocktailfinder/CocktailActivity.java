package com.example.cocktailfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CocktailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.tv_cocktail_activity);
        ImageView imageView = (ImageView) findViewById(R.id.iv_cocktail_activity);
        Button button = (Button) findViewById(R.id.btn_cocktail_activity);
        textView.setText(intent.getStringExtra("name"));
        Glide.with(this).load(intent.getStringExtra("image")).into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}