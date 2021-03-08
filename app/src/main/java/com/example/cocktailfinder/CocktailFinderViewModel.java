package com.example.cocktailfinder;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class CocktailFinderViewModel extends ViewModel {
    private MutableLiveData<Cocktails> mCocktailListLiveData = new MutableLiveData<Cocktails>();
    public LiveData<Cocktails> mCocktailList = mCocktailListLiveData;
    private CocktailFinderRepo mCocktailFinderRepo = new CocktailFinderRepo();

    public boolean getCocktails(String input) {
        // validate the input
        if(!isValid(input)) {
            return false;
        }
        char firstChar = Character.toLowerCase(input.charAt(0));
        // make request using that input
        // update live data using callback
        mCocktailFinderRepo.requestCocktails(firstChar, new Callback<Cocktails>() {
            @Override
            public void onResponse(Call<Cocktails> call, Response<Cocktails> response) {
                if(response.isSuccessful() || response.body() == null) {
                    Log.e("cocktailApiRequest", "request succeeded!");
                    mCocktailListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cocktails> call, Throwable t) {
                Log.e("cocktailApiRequest", "request failed");
                t.printStackTrace();
            }
        });
        return true;
    }
    public boolean isValid(String input) {
        if(input == null || input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            return false;
        }
        return true;
    }

}
interface CocktailApi {
    @GET("search.php")
    Call<Cocktails> getCocktails(@Query("f") char firstChar);
}
class Cocktails {
    JsonArray drinks;
}
class CocktailFinderRepo {
    private final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void requestCocktails(char firstChar, Callback<Cocktails> callback) {
        Log.d("test", "requesting cocktails");
        CocktailApi api = retrofit.create(CocktailApi.class);
        Call<Cocktails> call = api.getCocktails(firstChar);
        call.enqueue(callback);
    }
}