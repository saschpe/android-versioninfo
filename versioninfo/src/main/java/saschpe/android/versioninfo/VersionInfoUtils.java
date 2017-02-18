/*
 * Copyright 2016 Sascha Peilicke
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

package saschpe.android.versioninfo;

import android.content.Context;

import java.util.GregorianCalendar;

public class VersionInfoUtils {
    private final static GregorianCalendar calendar;

    static {
        calendar = new GregorianCalendar();  // Needed for the 4-digit year
    }

    private VersionInfoUtils() {
        // No instance
    }

    /**
     * Returns the formatted version. To be used outside the dialog fragment, for instance
     * in activity or preference titles.
     *
     * @param context The almighty context
     * @param version The app's version
     * @return Version string
     */
    public static String getFormattedVersion(Context context, String version) {
        return getFormattedVersion(context, context.getPackageName(), version);
    }

    /**
     * Returns the formatted version. To be used outside the dialog fragment, for instance
     * in activity or preference titles.
     *
     * @param context The almighty context
     * @param packageName The app's package name
     * @param version The app's version
     * @return Version string
     */
    public static String getFormattedVersion(Context context, String packageName, String version) {
        int versionInfoStringId = context.getResources().getIdentifier("version_template", "string", packageName);
        return context.getString(versionInfoStringId, version);
    }

    /**
     * Returns the formatted copyright. To be used outside the dialog fragment, for instance
     * in activity or preference titles.
     *
     * @param context The almighty context
     * @param owner The app onwer.
     * @return Copyright string
     */
    public static String getFormattedCopyright(Context context, String owner) {
        return getFormattedCopyright(context, context.getPackageName(), owner);
    }

    /**
     * Returns the formatted copyright. To be used outside the dialog fragment, for instance
     * in activity or preference titles.
     *
     * @param context The almighty context
     * @param packageName The app's package name
     * @param owner The app onwer.
     * @return Copyright string
     */
    public static String getFormattedCopyright(Context context, String packageName, String owner) {
        int copyrightStringId = context.getResources().getIdentifier("copyright_template", "string", packageName);
        return context.getString(copyrightStringId, calendar, owner);
    }
}
