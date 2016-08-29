package io.github.ryanhoo.firFlight.ui.app;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import io.github.ryanhoo.firFlight.data.model.IAppBasic;
import io.github.ryanhoo.firFlight.ui.base.BasePresenter;
import io.github.ryanhoo.firFlight.ui.base.BaseView;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 8/21/16
 * Time: 10:17 PM
 * Desc: AppContract
 */

interface AppContract {

    interface View extends BaseView<Presenter> {

        Context getContext();

        // Load apps

        void onAppsLoaded(List<IAppBasic> apps);

        void onLoadAppStarted();

        void onLoadAppCompleted();

        // Download app

        void addTask(String appId, AppDownloadTask task);

        void removeTask(String appId);

        void updateAppInfo(String appId, int position);

        void installApk(Uri apkUri);
    }

    interface Presenter extends BasePresenter {

        void loadApps();

        void requestInstallUrl(final AppItemView itemView, final int position);
    }
}
