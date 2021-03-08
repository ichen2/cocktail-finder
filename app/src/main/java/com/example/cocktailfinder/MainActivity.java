package com.example.cocktailfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    CocktailFinderViewModel mCocktailFinderViewModel;
    RecyclerView mCocktailRecyclerView;
    EditText mInputCharEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCocktailRecyclerView = (RecyclerView) findViewById(R.id.cocktail_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCocktailRecyclerView.setLayoutManager(layoutManager);
        CocktailFinderAdapter adapter = new CocktailFinderAdapter(new CocktailOnClickListener() {
            @Override
            public void onClick(String name, String image) {
                Intent intent = new Intent(MainActivity.this, CocktailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });

        mCocktailFinderViewModel = new ViewModelProvider(this).get(CocktailFinderViewModel.class);
        mCocktailFinderViewModel.mCocktailList.observe(this, new Observer<Cocktails>() {
            @Override
            public void onChanged(Cocktails cocktails) {
                Log.d("cocktailListObserver", "Got data!");
                if(cocktails.drinks.size() > 0) {
                    Log.d("onChanged", cocktails.drinks.size() + " cocktails");
                    adapter.setCocktails(cocktails);
                    mCocktailRecyclerView.setAdapter(adapter);
                    displayRecyclerView();
                    hideKeyboard(MainActivity.this);
                    mInputCharEditText.setText("");
                }
            }
        });
        mInputCharEditText = (EditText) findViewById(R.id.first_char_query);
        Button searchButton = (Button) findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mCocktailFinderViewModel.getCocktails(mInputCharEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Invalid input. Please enter a single letter.", Toast.LENGTH_LONG).show();
                    mInputCharEditText.setText("");
                }
            }
        });
    }
    void displayRecyclerView() {
        if(mCocktailRecyclerView.getVisibility() != View.VISIBLE) {
            mCocktailRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
