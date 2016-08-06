package com.example.edwardlucci.edwardzhihupaper.network;

import com.example.edwardlucci.edwardzhihupaper.AppModule;
import com.example.edwardlucci.edwardzhihupaper.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by edward on 16/8/1.
 */
@Singleton
@Component(modules = {AppModule.class,DataModule.class})
public interface DataComponent {
    void inject(MainActivity mainActivity);
}