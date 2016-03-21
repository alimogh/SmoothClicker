package com.pylapp.smoothclicker;

/**
 * Package containing classes which process unit tests with JUnit.
 * We want to test the API of the app, here the core / the work classes / the non-GUI parts
 * We have two types of tests: local tests (the unit tests) and instrumented tests (more for GUI)
 *
 * About the local tests:
 <pre>
 The unit test has no dependencies or only has simple dependencies on Android,
 we you should run the test on a local development machine. This testing approach is efficient because
 it helps use to avoid the overhead of loading the target app and unit test code onto a physical device or emulator
 every time the test is run. Consequently, the execution time for running your unit test is greatly reduced.
 </pre>
 * About the instrumented tests:
 <pre>
 Instrumented unit tests are unit tests that run on physical devices and emulators,
 instead of the Java Virtual Machine (JVM) on the local machine.
 We should create instrumented unit tests if the tests need access to instrumentation information
 (such as the target app's Context) or if they require the real implementation of an Android framework component
 (such as a Parcelable or SharedPreferences object). Using instrumented unit tests also helps to reduce
 the effort required to write and maintain mock code.
 </pre>
 *
 * Some frameworks are helpful to mock objects : Hamcrest (https://github.com/hamcrest, http://hamcrest.org/)
 * and Mockito (https://github.com/mockito/mockito, http://site.mockito.org/)
 *
 * More about JUnit :
 *  <ul>
         <li>http://junit.org/</li>
         <li>https://github.com/junit-team/junit</li>
 *  </ul>
 *
 *  More about tests :
 *  <ul>
        <li>http://developer.android.com/training/testing/unit-testing/local-unit-tests.html</li>
        <li>http://developer.android.com/training/testing/unit-testing/instrumented-unit-tests.html</li>
 *  </ul>
 */