package com.moji.daypack.ui.signin;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import com.moji.daypack.R;
import com.moji.daypack.ui.base.BaseActivity;
import com.moji.daypack.ui.common.alert.FlightDialog;
import com.moji.daypack.ui.common.alert.FlightToast;
import com.moji.daypack.ui.helper.OnTextChangedListener;
import com.moji.daypack.ui.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 4/3/16
 * Time: 12:49 AM
 * Desc: SignInActivity
 */
public class SignInActivity extends BaseActivity implements SignInContract.View {

    private static final String REG_EMAIL = ".+@.+\\..+";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edit_text_api_key)
    EditText editTextApiKey;
    @Bind(R.id.edit_text_user_key)
    EditText editTextUserKey;

    MenuItem menuItemSignIn;

    FlightDialog mProgressDialog;

    SignInContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        ActionBar actionBar = supportActionBar(toolbar);
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_close);
        }

        OnTextChangedListener onTextChangedListener = new OnTextChangedListener() {
            @Override
            public void onTextChanged(Editable s) {
                menuItemSignIn.setEnabled(isFormValid());
            }
        };
        editTextApiKey.addTextChangedListener(onTextChangedListener);
        editTextUserKey.addTextChangedListener(onTextChangedListener);

        mProgressDialog = FlightDialog.defaultLoadingDialog(this);

        mPresenter = new SignInPresenter(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // https://crazygui.wordpress.com/2010/09/05/high-quality-radial-gradient-in-android/
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;

        Window window = getWindow();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{
                ContextCompat.getColor(this, R.color.ff_signin_gradientColor),
                ContextCompat.getColor(this, R.color.ff_signin_background)
        });
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(screenHeight);
        gradientDrawable.setGradientCenter(0, screenHeight * 0.5f);

        window.setBackgroundDrawable(gradientDrawable);
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_in, menu);
        menuItemSignIn = menu.findItem(R.id.menu_item_sign_in).setEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_sign_in) {
            signIn();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFormValid() {
        if (editTextApiKey.length() == 0) {
            return false;
        }
        if (editTextUserKey.length() == 0) {
            return false;
        }
        return true;
    }

    private void signIn() {
        final String apiKey = editTextApiKey.getText().toString();
        final String userKey = editTextUserKey.getText().toString();
        mPresenter.signIn(apiKey, userKey);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_from_top);
    }

    // MVP View

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSignInStarted() {
        menuItemSignIn.setEnabled(false);
        mProgressDialog.show();
    }

    @Override
    public void onSignInCompleted() {
        menuItemSignIn.setEnabled(true);
        editTextApiKey.clearFocus();
        editTextUserKey.clearFocus();
        mProgressDialog.dismiss();

        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSignInError(Throwable e) {
        menuItemSignIn.setEnabled(true);
        mProgressDialog.dismiss();

        String errorMessage = e.getMessage();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() == 401) {
                errorMessage = getString(R.string.ff_network_error_wrong_email_or_password);
            }
        }
        new FlightToast.Builder(SignInActivity.this)
                .message(errorMessage)
                .show();
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
