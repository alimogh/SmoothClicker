package com.pylapp.smoothclicker.junit;

import android.content.Context;

import com.pylapp.smoothclicker.AbstractTest;
import com.pylapp.smoothclicker.tools.ShakeToClean;

import org.junit.Test;

/**
 * Class to test the ShakeToClean
 *
 * @author pylapp
 * @version 1.0.0
 * @since 21/03/2016
 * @see UnitTestShakeToClean
 */
public class UnitTestShakeToClean extends AbstractTest {


    /**
     * Tests the constructor
     */
    @Test (expected = IllegalArgumentException.class)
    public void constructor(){
        l(this, "@Test constructor");
        Context nullContext = null;
        new ShakeToClean(nullContext);
    }

}
