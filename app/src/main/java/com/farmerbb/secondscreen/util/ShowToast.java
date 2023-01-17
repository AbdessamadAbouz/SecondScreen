/* Copyright 2015 Braden Farmer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.farmerbb.secondscreen.util;

import android.content.Context;
import android.widget.Toast;

// Shows a toast message inside ProfileLoadService and TurnOffService, where the standard toast
// methods won't work.
public final class ShowToast implements Runnable {
    private final Context context;
    private final int text;
    private final int length;

    public ShowToast(Context context, int text, int length) {
        this.context = context;
        this.text = text;
        this.length = length;
    }

    public void run() {
        if (length == Toast.LENGTH_LONG) {
            U.showToastLong(context, text);
        } else {
            U.showToast(context, text);
        }
    }
}