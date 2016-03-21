package com.pylapp.smoothclicker;

import android.content.Context;

import com.pylapp.smoothclicker.tools.ShakeToClean;

import org.junit.Test;

/**
 * Class to test the ShakeToClean
 *
 * @author pylapp
 * @version 1.0.0
 * @since 21/03/2016
 */
public class TestShakeToClean {

    /**
     *
     */
    @Test (expected = IllegalArgumentException.class)
    public void constructor(){
        Context nullContext = null;
        new ShakeToClean(nullContext);
    }

}
