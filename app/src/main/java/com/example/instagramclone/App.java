package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("CH4jiBN295NWzBrmDA51cT06DjFLmBj9K4vXWIE5")
                // if defined
                .clientKey("Tersax1pcXj93Hjg4pojDhdl680d2SIoIHJHS5rW")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
