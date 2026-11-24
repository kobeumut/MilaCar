package me.aap.fermata.ui.activity;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.FLAG_SHOW_UI;
import static android.media.AudioManager.STREAM_MUSIC;
import static android.os.Build.VERSION.SDK_INT;
import static android.view.InputDevice.SOURCE_CLASS_POINTER;
import static android.view.MotionEvent.ACTION_SCROLL;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MINUTES;
import static me.aap.fermata.util.Utils.createDownloader;
import static me.aap.utils.async.Completed.completed;
import static me.aap.utils.async.Completed.completedNull;
import static me.aap.utils.async.Completed.completedVoid;
import static me.aap.utils.async.Completed.failed;
import static me.aap.utils.misc.MiscUtils.isPackageInstalled;
import static me.aap.utils.pref.PreferenceStore.Pref.sa;
import static me.aap.utils.ui.UiUtils.showAlert;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.FileProvider;

import com.google.android.play.core.splitcompat.SplitCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.aap.fermata.BuildConfig;
import me.aap.fermata.FermataApplication;
import me.aap.fermata.R;
import me.aap.fermata.addon.AddonInfo;
import me.aap.fermata.addon.AddonManager;
import me.aap.fermata.media.service.FermataMediaServiceConnection;
import me.aap.utils.app.App;
import me.aap.utils.async.FutureSupplier;
import me.aap.utils.collection.NaturalOrderComparator;
import me.aap.utils.function.Supplier;
import me.aap.utils.log.Log;
import me.aap.utils.net.http.HttpConnection;
import me.aap.utils.net.http.HttpFileDownloader;
import me.aap.utils.pref.PreferenceStore;
import me.aap.utils.pref.PreferenceStore.Pref;
import me.aap.utils.text.TextUtils;
import me.aap.utils.ui.UiUtils;
import me.aap.utils.ui.activity.AppActivity;
import me.aap.utils.ui.activity.SplitCompatActivityBase;

public class MainActivity extends SplitCompatActivityBase
		implements FermataActivity, AddonManager.Listener {
	private static FermataMediaServiceConnection service;
	private static MainActivity activeInstance;

	@Nullable
	public static MainActivity getActiveInstance() {
		return activeInstance;
	}

	@Override
	protected FutureSupplier<MainActivityDelegate> createDelegate(AppActivity a) {
		FermataMediaServiceConnection s = service;

		if ((s != null) && s.isConnected()) {
			return completed(new MainActivityDelegate(a, service.createBinder()));
		}

		return FermataMediaServiceConnection.connect(a).map(c -> {
			assert service == null;
			service = c;
			return new MainActivityDelegate(a, service.createBinder());
		}).onFailure(err -> showAlert(getContext(), String.valueOf(err)));
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(MainActivityDelegate.attachBaseContext(base));
	}

	@Override
	public void finish() {
		FermataMediaServiceConnection s = service;
		service = null;
		if (s != null) s.disconnect();
		super.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MainActivityDelegate.setTheme(this,
				isCarActivity() || FermataApplication.get().isMirroringMode());
		AddonManager.get().addBroadcastListener(this);
		getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
				getActivityDelegate().onSuccess(MainActivityDelegate::onBackPressed);
			}
		});
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		AddonManager.get().removeBroadcastListener(this);
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		activeInstance = this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		activeInstance = null;
	}

	@Override
	public boolean isCarActivity() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public FutureSupplier<MainActivityDelegate> getActivityDelegate() {
		return (FutureSupplier<MainActivityDelegate>) super.getActivityDelegate();
	}

	@Override
	public void onAddonChanged(AddonManager mgr, AddonInfo info, boolean installed) {
		SplitCompat.installActivity(this);
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (((event.getSource() & SOURCE_CLASS_POINTER) != 0) && (event.getAction() == ACTION_SCROLL)) {
			AudioManager amgr = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
			if (amgr == null) return false;
			float v = event.getAxisValue(MotionEvent.AXIS_VSCROLL);
			amgr.adjustStreamVolume(STREAM_MUSIC, (v > 0) ? ADJUST_RAISE : ADJUST_LOWER, FLAG_SHOW_UI);
			return true;
		}

		return super.onGenericMotionEvent(event);
	}
}
