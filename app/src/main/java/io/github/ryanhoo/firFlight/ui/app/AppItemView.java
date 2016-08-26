package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.data.model.App;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.ui.common.adapter.IAdapterView;
import io.github.ryanhoo.firFlight.ui.common.widget.CharacterDrawable;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:46 PM
 * Desc: AppItemView
 */

public class AppItemView extends RelativeLayout implements IAdapterView<AppEntity> {

    Context mContext;
    @Bind(R.id.image_view_icon)
    ImageView imageView;
    @Bind(R.id.text_view_name)
    TextView textViewName;
    @Bind(R.id.text_view_local_version)
    TextView textViewLocalVersion;
    @Bind(R.id.text_view_version)
    TextView textViewVersion;
    @Bind(R.id.text_view_bundle_id)
    TextView textViewBundleId;
    @Bind(R.id.button_action)
    Button buttonAction;
    @Bind(R.id.layout_ios_badge)
    View layoutIOSBadge;

    AppInfo appInfo;

    public AppItemView(Context context) {
        super(context);

        mContext = context;
        View.inflate(context, R.layout.item_app, this);
        ButterKnife.bind(this);

        textViewLocalVersion.setPaintFlags(textViewLocalVersion.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void bind(AppEntity app, int position) {
        appInfo = new AppInfo(mContext, app);

        Glide.with(mContext)
                .load("http://o1wh05aeh.qnssl.com/image/view/app_icons/"+app.appIcon)
                .placeholder(CharacterDrawable.create(mContext, app.appName.charAt(0), false, R.dimen.ff_padding_large))
                .into(imageView);
        textViewName.setText(app.appName);
        textViewVersion.setText(String.format("%s(%s)",
                app.appVersion,
                app.appBuildVersion
        ));
        textViewBundleId.setText(app.appIdentifier);

        // Hide old version when app is not installed or already up-to-date
        textViewLocalVersion.setVisibility(
                (appInfo.isUpToDate || !appInfo.isInstalled) ? View.GONE : View.VISIBLE);
        if (appInfo.isInstalled && !appInfo.isUpToDate) {
            textViewLocalVersion.setText(String.format("%s(%s)",
                    appInfo.localVersionName, appInfo.localVersionCode));
        }
        boolean isAndroidApp = app.isAndroidApp();
        buttonAction.setVisibility(isAndroidApp ? View.VISIBLE : View.GONE);
        layoutIOSBadge.setVisibility(isAndroidApp ? View.GONE : View.VISIBLE);
    }
}
