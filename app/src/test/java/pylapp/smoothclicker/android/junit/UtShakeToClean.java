package pylapp.smoothclicker.android.junit;

import android.content.Context;

import pylapp.smoothclicker.android.AbstractTest;
import pylapp.smoothclicker.android.tools.ShakeToClean;

import org.junit.Test;

/**
 * Class to test the ShakeToClean
 *
 * @author pylapp
 * @version 1.0.0
 * @since 21/03/2016
 * @see UtShakeToClean
 */
public class UtShakeToClean extends AbstractTest {


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
