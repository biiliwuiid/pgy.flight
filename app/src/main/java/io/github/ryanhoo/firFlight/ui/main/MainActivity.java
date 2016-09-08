package io.github.ryanhoo.firFlight.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.ryanhoo.firFlight.R;
import io.github.ryanhoo.firFlight.ui.about.AboutActivity;
import io.github.ryanhoo.firFlight.ui.app.AppsFragment;
import io.github.ryanhoo.firFlight.ui.base.BaseActivity;
import io.github.ryanhoo.firFlight.ui.setting.SettingsActivity;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/8/16
 * Time: 9:28 PM
 * Desc: MainActivityV2
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = supportActionBar(toolbar);
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        }

        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_more_vert_black_24dp));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container, AppsFragment.getInstance()).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                onOpenSettings();
                break;
            case R.id.menu_item_about:
                onOpenAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onOpenSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void onOpenAbout() {
        startActivity(new Intent(this, AboutActivity.class));
    }
}
