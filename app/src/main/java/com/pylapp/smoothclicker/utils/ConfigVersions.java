/*
    Copyright 2016 Pierre-Yves Lapersonne (aka. "pylapp",  pylapp(dot)pylapp(at)gmail(dot)com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
// ✿✿✿✿ ʕ •ᴥ•ʔ/ ︻デ═一

package com.pylapp.smoothclicker.utils;

/**
 * Just a configuration class for versions
 *
 * A version of an app should be found by three things : the version code, the version name and the version tag.
 * The version code is incremented each time a new version is released.
 * The version name is the "user-friendly" version, i.e. with a schema like XX.YY.ZZ.
 * The XX is incremented for very important upgrades, major updates.
 * The YY is incremented or common upgrades, medium.
 * The ZZ is incremented for minor upgrades, i.e. updates for refactoring or cleaning facts.
 * The version tag is a code name, easy to remember. It should be changed each time the XX or YY part is modified.
 *
 * @author pylapp
 * @version 1.1.0
 * @since 16/03/2016
 */
public final class ConfigVersions {


    /*
     * Version tags
     */
    public static final String VERSION_TAG_1_0_0 = "Astonishing Ant";
    public static final String VERSION_TAG_1_3_0 = "Blazing Buffalo";
    public static final String VERSION_TAG_1_4_0 = "Crazy Crane";
    public static final String VERSION_TAG_1_5_0 = "Dumb Dodo";
    public static final String VERSION_TAG_1_6_0 = "Elastic Elephant";
    // public static final String VERSION_TAG_1_7_0 = "Freaky Fawn"; // For the future ;)
    public static final String VERSION_TAG_CURRENT = VERSION_TAG_1_6_0;

}
