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

package saschpe.android.versioninfo.widget;

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

import saschpe.android.versioninfo.VersionInfoUtils;

/**
 * Dialog fragment to display app name and version.
 */
public final class VersionInfoDialogFragment extends DialogFragment {
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
    public static VersionInfoDialogFragment newInstance(final String title, final String version, final String copyrightOwner, final int imageId) {
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
        ImageView imageView = view.findViewById(imageViewId);
        imageView.setImageResource(imageId);
        imageView.setContentDescription(title);

        int titleViewId = getResources().getIdentifier("title", "id", packageName);
        TextView titleView = view.findViewById(titleViewId);
        titleView.setText(title);

        int versionViewId = getResources().getIdentifier("version", "id", packageName);
        TextView versionView = view.findViewById(versionViewId);
        versionView.setText(getFormattedVersion());

        int copyrightViewId = getResources().getIdentifier("my_copyright", "id", packageName);
        TextView copyrightView = view.findViewById(copyrightViewId);
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