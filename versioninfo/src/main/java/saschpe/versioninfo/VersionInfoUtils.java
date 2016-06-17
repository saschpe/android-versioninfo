package saschpe.versioninfo;

import android.content.Context;

import java.util.GregorianCalendar;

/**
 * Utility class to provide formatted version and copyright strings.
 */
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
