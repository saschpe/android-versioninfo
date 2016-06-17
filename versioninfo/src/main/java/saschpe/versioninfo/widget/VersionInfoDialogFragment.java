package saschpe.versioninfo.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import saschpe.versioninfo.VersionInfoUtils;

/**
 * Dialog fragment to display app name and version.
 */
public class VersionInfoDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_VERSION = "version";
    private static final String ARG_OWNER = "owner";
    private static final String ARG_IMAGE_ID = "image";

    /**
     * Factory method to create a new VersionInfoDialogFragment instance.
     *
     * @param title The title to display, like @string/app_name
     * @param version The version to display, like @see{BuildConfig.VERSION_NAME}
     * @param copyrightOwner The copyright owner, like "Sascha Peilicke"
     * @param imageId ID of a image resource to display, like e.g."@mipmap/ic_launcher"
     * @return A new instance of VersionInfoDialogFragment.
     */
    public static VersionInfoDialogFragment newInstance(String title, String version, String copyrightOwner, int imageId) {
        VersionInfoDialogFragment fragment = new VersionInfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_VERSION, version);
        args.putString(ARG_OWNER, copyrightOwner);
        args.putInt(ARG_IMAGE_ID, imageId);
        fragment.setArguments(args);
        return fragment;
    }

    private String title;
    private String version;
    private String owner;
    private int imageId;
    private String packageName;

    public VersionInfoDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString(ARG_TITLE);
            version = args.getString(ARG_VERSION);
            owner = args.getString(ARG_OWNER);
            imageId = args.getInt(ARG_IMAGE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            packageName = getContext().getPackageName();
        } else {
            packageName = getActivity().getPackageName();
        }

        // R class is not generated for libraries, thus we have to go the long road...
        // See https://sites.google.com/a/android.com/tools/recent/dealingwithdependenciesinandroidprojects
        int fragmentId = getResources().getIdentifier("fragment_version_info", "layout", packageName);
        View view = inflater.inflate(fragmentId, container, false);

        int imageViewId = getResources().getIdentifier("image", "id", packageName);
        ImageView imageView = (ImageView) view.findViewById(imageViewId);
        imageView.setImageResource(imageId);
        imageView.setContentDescription(title);

        int titleViewId = getResources().getIdentifier("title", "id", packageName);
        TextView titleView = (TextView) view.findViewById(titleViewId);
        titleView.setText(title);

        int versionViewId = getResources().getIdentifier("version", "id", packageName);
        TextView versionView = (TextView) view.findViewById(versionViewId);
        versionView.setText(getFormattedVersion());

        int copyrightViewId = getResources().getIdentifier("copyright", "id", packageName);
        TextView copyrightView = (TextView) view.findViewById(copyrightViewId);
        copyrightView.setText(getFormattedCopyright());

        return view;
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     * */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    /**
     * Returns the formatted version. To be used outside the dialog fragment, for instance
     * in activity or preference titles.
     * <p>
     * Only works if the fragment is attached to an {@link android.app.Activity}.
     * Otherwise use {@link VersionInfoUtils#getFormattedVersion(Context, String, String)}.
     *
     * @return Version string
     */
    public String getFormattedVersion() {
        if (isAdded()) {
            return VersionInfoUtils.getFormattedVersion(getActivity(), packageName, version);
        }
        return null;
    }

    /**
     * Returns the formatted copyright. To be used outside the dialog fragment, for instance
     * in activity or preference titles.
     * <p>
     * Only works if the fragment is attached to an {@link android.app.Activity}.
     * Otherwise use {@link VersionInfoUtils#getFormattedCopyright(Context, String, String)}.
     *
     * @return Copyright string
     */
    public String getFormattedCopyright() {
        if (isAdded()) {
            return VersionInfoUtils.getFormattedCopyright(getActivity(), packageName, owner);
        }
        return null;
    }
}