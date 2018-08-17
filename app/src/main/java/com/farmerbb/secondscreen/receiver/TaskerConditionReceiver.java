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

package com.farmerbb.secondscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.farmerbb.secondscreen.util.BundleScrubber;
import com.farmerbb.secondscreen.util.PluginBundleManager;
import com.farmerbb.secondscreen.util.U;

// Receiver run by Tasker periodically to check the state of currently active profiles, whenever
// a SecondScreen state is included as a condition in a Tasker profile.
public final class TaskerConditionReceiver extends BroadcastReceiver {
    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
        if(U.isExternalAccessDisabled(context)) return;

        BundleScrubber.scrub(intent);

        final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE);
        BundleScrubber.scrub(bundle);

        if(PluginBundleManager.isBundleValid(bundle)) {
            String filename = bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE);
            SharedPreferences prefCurrent = context.getSharedPreferences("current", Context.MODE_MULTI_PROCESS);

            if("quick_actions".equals(prefCurrent.getString("filename", "0"))) {
                SharedPreferences prefSaved = context.getSharedPreferences("quick_actions", Context.MODE_MULTI_PROCESS);
                if("0".equals(prefSaved.getString("original_filename", "0")))
                    setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNSATISFIED);
                else {
                    if("any_profile".equals(filename))
                        setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED);
                    else {
                        if(prefSaved.getString("original_filename", "0").equals(filename))
                            setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED);
                        else
                            setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNSATISFIED);
                    }
                }
            } else {
                if("0".equals(prefCurrent.getString("filename", "0")))
                    setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNSATISFIED);
                else {
                    if("any_profile".equals(filename))
                        setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED);
                    else {
                        if(prefCurrent.getString("filename", "0").equals(filename))
                            setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED);
                        else
                            setResultCode(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNSATISFIED);
                    }
                }
            }
        }
    }
}