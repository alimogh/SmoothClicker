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
 * The configuration status
 *
 * @author pylapp
 * @version 1.0.0
 * @since 02/03/2016
 */
public enum ConfigStatus {
    /**
     * The config is well defined and ready to use
     */
    READY,
    /**
     * The delay has not been defined
     */
    DELAY_NOT_DEFINED,
    /**
     * The time to wait before each click has not been defined
     */
    TIME_GAP_NOT_DEFINED,
    /**
     * The amount of rep eat to do has not been defined
     */
    REPEAT_NOT_DEFINED
}