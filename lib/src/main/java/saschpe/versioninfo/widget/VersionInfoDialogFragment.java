package saschpe.versioninfo.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.GregorianCalendar;

/**
 * Dialog fragment to display app name and version
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
    static public VersionInfoDialogFragment newInstance(String title, String version, String copyrightOwner, int imageId) {
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
        // R class is not generated for libraries, thus we have to go the long road...
        // See https://sites.google.com/a/android.com/tools/recent/dealingwithdependenciesinandroidprojects

        String packageName = inflater.getContext().getPackageName();

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
        int versionInfoStringId = getResources().getIdentifier("version_template", "string", packageName);
        versionView.setText(String.format(getString(versionInfoStringId), version));

        GregorianCalendar cal = new GregorianCalendar();
        int copyrightViewId = getResources().getIdentifier("copyright", "id", packageName);
        TextView copyrightView = (TextView) view.findViewById(copyrightViewId);
        int copyrightStringId = getResources().getIdentifier("copyright_template", "string", packageName);
        copyrightView.setText(String.format(getString(copyrightStringId), cal, owner));

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
}