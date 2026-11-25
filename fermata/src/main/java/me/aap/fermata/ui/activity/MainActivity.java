package me.aap.fermata.ui.activity;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.FLAG_SHOW_UI;
import static android.media.AudioManager.STREAM_MUSIC;
import static android.os.Build.VERSION.SDK_INT;
import static android.view.InputDevice.SOURCE_CLASS_POINTER;
import static android.view.MotionEvent.ACTION_SCROLL;
import static me.aap.utils.async.Completed.completed;
import static me.aap.utils.pref.PreferenceStore.Pref.sa;
import static me.aap.utils.ui.UiUtils.showAlert;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.view.MotionEvent;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.google.android.play.core.splitcompat.SplitCompat;


import java.io.File;
import java.util.List;

import me.aap.fermata.BuildConfig;
import me.aap.fermata.FermataApplication;
import me.aap.fermata.R;
import me.aap.fermata.addon.AddonInfo;
import me.aap.fermata.addon.AddonManager;
import me.aap.fermata.media.service.FermataMediaServiceConnection;
import me.aap.utils.app.App;
import me.aap.utils.io.FileUtils;
import me.aap.utils.async.FutureSupplier;
import me.aap.utils.function.Supplier;
import me.aap.utils.log.Log;
import me.aap.utils.ui.activity.AppActivity;
import me.aap.utils.ui.activity.SplitCompatActivityBase;

public class MainActivity extends SplitCompatActivityBase
		implements FermataActivity, AddonManager.Listener {
	private static final String GPLAY_PKG = "com.android.vending";
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

	/**
	 * Android Auto lists the full screen apps only if Google Play is recorded as the installer.
	 * The media service is shown with the "Unknown sources" developer option, the full UI is not.
	 */
	public static boolean isInstalledForAndroidAuto(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			String installer = (SDK_INT >= Build.VERSION_CODES.R) ?
					pm.getInstallSourceInfo(ctx.getPackageName()).getInstallingPackageName() :
					pm.getInstallerPackageName(ctx.getPackageName());
			return GPLAY_PKG.equals(installer);
		} catch (Exception ex) {
			Log.e(ex, "Failed to get the installer package");
			return false;
		}
	}

	/**
	 * Since Android 14 Android Auto ignores the installer recorded by the package installer, the full
	 * UI is shown only with root (LSPosed) or with an adapter in the developer mode.
	 */
	public static boolean isInstallerWorkaroundSupported() {
		return SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE;
	}

	/**
	 * Reinstall the installed APK of this application with Google Play as the installer, so that
	 * the full UI appears in Android Auto. Nothing is downloaded - the APK already installed on
	 * the device is passed to the system package installer, which asks the user to confirm.
	 */
	public void installForAndroidAuto() {
		if (!BuildConfig.AUTO) return;

		if (!isInstallerWorkaroundSupported()) {
			showAlert(this, R.string.aa_install_a14);
			return;
		}

		var info = getApplicationInfo();

		if ((info.splitSourceDirs != null) && (info.splitSourceDirs.length != 0)) {
			showAlert(this, R.string.aa_install_split);
			return;
		}

		App.get().execute(() -> {
			File dir = new File(getCacheDir(), "updates");
			//noinspection ResultOfMethodCallIgnored
			dir.mkdirs();
			File apk = new File(dir, "app.apk");
			FileUtils.copy(new File(info.sourceDir), apk);
			return apk;
		}).main().onSuccess(apk -> {
			Uri u = FileProvider.getUriForFile(this, getPackageName() + ".FileProvider", apk);
			Intent i = new Intent(Intent.ACTION_INSTALL_PACKAGE);
			i.setData(u);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
			i.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
			i.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, GPLAY_PKG);
			startActivity(i);
		}).onFailure(err -> {
			Log.e(err, "Failed to reinstall the application");
			showAlert(this, R.string.aa_install_failed, err.getLocalizedMessage());
		});
	}
}
