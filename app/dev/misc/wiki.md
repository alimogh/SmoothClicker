# Smooth Clicker
_A free open-source easy to use material-designed autoclicker for Android rooted devices._

This app can trigger software clicks / touches thanks to system Shell commands executed in the system as SU.
The app is open-source, easy to use and to configure and can make several clicks with delayed starts and pauses between each click.
You can also repeat clicks sequences and make infinite clicks loops.
The app possesses a dedicated Android service you can reach and trigger to make some click processes instead of using the GUI.

_Why this app? Because I wanted to build my own autoclicker to make some tricks on games or on apps (like <a href="https://play.google.com/store/apps/details?id=com.mlt.woy&hl=fr">Woy !</a>, <a href="http://orteil.dashnet.org/cookieclicker/">Cookie Clicker</a> or <a href="https://play.google.com/store/apps/details?id=com.supercell.clashofclans&">Clash of Clans</a>)._

The app can be found in Google Play <a href="https://play.google.com/store/apps/details?id=pylapp.smoothclicker.android">here</a>.

The project contains the _Java 8_ sources, the _JavaDoc_ as HTML pages, the unit tests with _JUnit_ and the instrumented tests with _Espresso_ and _UIAutomator_.

***
## Features
* still need to have a rooted Android device...
* intro screen to introduce the app
* define a sequence of clicks to make
* several points can be selected
* a sequence of clicks can be repeated, endlessly if needed
* a delay can be defined before each sequence of clicks
* a pause can me made between each click
* device may vibrate on start and on each click
* device may display notifications when the process is on going, on clicks and when the countdown is running
* the configuration can be reset to defaults values
* a shake to clean feature can reset the configuration
* support for portrait / landscape modes, for tablets and handsets
* standalone mode with an empty activity loading points and config files before finishing and starting the click process
* supported languages: english, french, klingon, spanish, portuguese, german, russian, korean, romanian, polish, finnish, italian and catalan


***
## Screen shots
<table>
<tr>
<td>
<img src="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/ui_v1.10.0_en_framed/v1.10.0_en_intro_1_framed.png" alt="Introduction screen" title="Welcome to Smooth Clicker guys!" width="200">
</td>
<td>
<img src="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/ui_v1.10.0_en_framed/v1.10.0_en_clicker_activity_2_framed.png" alt="Set up the sequence of clicks you want to process" title="Set up the sequence of clicks you want to process" width="200">
</td>
<td>
<img src="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/ui_v1.10.0_en_framed/v1.10.0_en_multipoint_3_framed.png" alt="Select some points everywhere" title="Select some points everywhere" width="200">
</td>
</tr>
<tr>
<td>
<img src="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/ui_v1.10.0_en_framed/v1.10.0_en_multipoint_2_framed.png" alt="You can make long sequence of clicks to trigger" title="You can make long sequence of clicks to trigger" width="200">
</td>
<td>
<img src="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/ui_v1.10.0_en_framed/v1.10.0_en_notifications_on_going_framed.png" alt="The app can display notifications about what its state" title="The app can display notifications about what it is doing" width="200">
</td>
<td>
<img src="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/ui_v1.10.0_en_framed/v1.10.0_en_settings_framed.png" alt="The app uses thir party libs !" title="The app uses third party libs !" width="200">
</td>
</tr>
</table>


***
## How to use Smooth Clicker?

_<em>Case 1</em> : You can simply use the app itself and select manually the points_

<br/>or<br/>

_<em>Case 2</em> : You can start the app is standalone mode which will use JSON files (<a href="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/smoothclicker_points.json">smoothclicker_points.json</a> and <a href="https://github.com/pylapp/SmoothClicker/blob/master/app/dev/misc/smoothclicker_config.json">smoothclicker_config.json</a>) in Download folder to set up and start the click process_<br/>

```shell
    am start -n pylapp.smoothclicker.android/pylapp.smoothclicker.android.views.NinjaActivity
```

<br/>or<br/>

_<em>Case 3</em> : You can start a dedicated Android service :_

First, create the good intent:<br/>
```java
Intent intentServiceSmoothClicker = new Intent("pylapp.smoothclicker.android.clickers.ServiceClicker.START");
```

Then define the configuration to set up:<br/>
```java
intentServiceSmoothClicker.putExtra("0x000011", true); // Start delayed ?
intentServiceSmoothClicker.putExtra("0x000012", 10);   // How much delay for the start ?
intentServiceSmoothClicker.putExtra("0x000013", 2);    // The amount of time to wait between clicks
intentServiceSmoothClicker.putExtra("0x000021", 5);    // The number of repeat to do
intentServiceSmoothClicker.putExtra("0x000022", false);// Endless repeat ?
intentServiceSmoothClicker.putExtra("0x000031", false);// Vibrate on start ?
intentServiceSmoothClicker.putExtra("0x000032", true);// Vibrate on each click ?
intentServiceSmoothClicker.putExtra("0x000041", true);// Make notifications ?
```

Today the points to click on are in one list like:<br/>
```java
ArrayList<Integer> points = new ArrayList<Integer>();
points.add(252); // x0
points.add(674); // y0
//etc
intentServiceSmoothClicker.putIntegerArrayListExtra("0x000051",points); // The list of points
```

Finally, starts the service: <br/>
```java
startService(intentServiceSmoothClicker);
```


***
## What's new?
* _v1.0.0 : Astonishing Ant_
    * first version of the app
* _v1.2.0_
    * vibrations on clicks, credits for third-party contents
* _v1.3.0 : Blazing Buffalo_
    * splash screen
* _v1.4.0 : Crazy Crane_
    * material designed components, exit dialog
* _v1.5.0 : Dumb dodo_
    * notifications about the clicks and the state of the app, back button
* _v1.5.1_
    * endless repeat, french support
* _v1.6.0 : Elastic Elephant_
    * selection of a point everywhere on the screen with a transparent view
* _v1.6.1_
    * selection of several points
* _v1.6.2_
    * shake to clean, settings view, more debug and more Javadoc HTML doc
* _v1.6.3_
    * notifications with countdowns for delayed starts
* _v1.7.0 : Freaky Fawn_
   * Klingon support
   * Service reachable from the outside
   * Some bugs fixes
* _v1.7.2_
    * MIT License
    * more debug 
* _v1.8.0 : Galactic Gorilla_
    * a lot of bugs have been fixed
    * improve the GUI
* _v1.8.1_
    * more fixed bugs
* _v1.8.2_
    * more refactor
    * optimization
* _v1.9.0 : Holy Hedgehog_
    * still need to use a rooted Android device...
    * possible to restore all the default values
    * possible to delete all the selected points
    * an helping toast is displayed on the clicking screen when the user is inactive
    * a snackbar is displayed to show to the user its defined action, instead of a toast
    * the user can undo its action thanks to a dismiss button in the snackbar
    * now an intro screen is displayed at the very first start of the app
    * new switch button for the delayed start
    * improve the credits view
    * support for spanish
    * more bug fixes, optimization and clean
* _v1.9.1_
    * support for german, portuguese, russian and korean languages
    * entry in settings screen to redirect the user on the market's page
* _v1.9.2_
    * fixed some bugs
    * update verbatims
* _v1.9.3_
    * fixed bug which may produce a force close (NullPointerException on main activity's SwitchButton)
    * fixed bug which may make a white icons bar for full screen views
    * support for romanian, polish and finnish languages
* _v1.10.0 : Incredible Indri_
    * support for landscape mode for handsets and tablets
    * more support for 6", 7" and 10" screens devices
    * standalone mode with an empty activity loading points and config file before starting the click process
    * support for italian and catalan
    * fixed some bugs
    * improved the credits view and other UI components

***
## Licence
Under MIT License
