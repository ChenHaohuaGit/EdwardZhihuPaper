package com.example.edwardlucci.edwardzhihupaper.story;

import com.example.edwardlucci.edwardzhihupaper.FragmentScoped;
import com.example.edwardlucci.edwardzhihupaper.network.DataComponent;

import dagger.Component;

/**
 * Created by edward on 16/8/9.
 */
@FragmentScoped
@Component(dependencies = DataComponent.class,modules = StoryPresenterModule.class)
public interface StoryComponent {
    void inject(StoryActivity storyActivity);
}