package com.example.edwardlucci.edwardzhihupaper.bean;

import com.example.edwardlucci.edwardzhihupaper.util.RxBus;
import com.example.edwardlucci.edwardzhihupaper.util.RxEvent;

/**
 * Created by edwardlucci on 16/6/9.
 */
public class ChangeContentEvent extends RxEvent {

    public ChangeContentEvent(Other other) {
        this.other = other;
    }

    private Other other;

    public Other getOther() {
        return other;
    }
}
