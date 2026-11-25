## MilaCar

MilaCar is a free, open source audio and video player for **Android Auto**, focused on playing
media files organized in folders and playlists – from the phone storage or from network shares
(SMB/SFTP) – right on the car screen.

> **Built on Fermata.** MilaCar is a fork of
> [Fermata Media Player](https://github.com/AndreyPavlenko/Fermata) by Andrey Pavlenko and uses
> Fermata's infrastructure: the media library, playback engines, Android Auto integration,
> screen mirroring and addons. All the credit for the core of the application goes to the
> Fermata project. MilaCar adds a security review, privacy hardening and a car focused UI on top
> of it. Like Fermata, MilaCar is distributed under the [GNU GPL v3](LICENSE).

|                    |                                                                 |
|--------------------|-----------------------------------------------------------------|
| Version            | 3.0.2 (based on Fermata 2.0.2, upstream commit `45c12a2`)       |
| Application ID     | `com.kobeumut.milacar.auto` (can be installed next to Fermata) |
| Minimum Android    | 9 (API 28)                                                      |
| User guide (Turkish) | [docs/ARAC_KULLANIM_KILAVUZU.md](docs/ARAC_KULLANIM_KILAVUZU.md) |

## Features

All the playback features come from Fermata:

* Play media files organized in folders, from local storage, SMB and SFTP shares
* Remembers the last played track and position for each folder
* Favorites, playlists, bookmarks, CUE and M3U playlists
* IPTV addon with XMLTV EPG and catchup
* Audio effects: equalizer, bass/volume boost, virtualizer – per track or per folder
* Playback speed per track or per folder
* Pluggable media engines: MediaPlayer, ExoPlayer and VLC
* Video player with subtitles and audio stream selection (VLC engine)
* Full application UI on the Android Auto screen, the standard Android Auto media UI and
  screen mirroring

## What is different from Fermata

### Security review

The whole Fermata source tree was reviewed: the Java sources, manifests, Gradle and CI scripts
and the bundled binaries (`aauto.aar`, the Xposed API stubs, the Gradle wrapper and the Silero
VAD model). **No malware was found.** It has no ads, analytics or tracking, no crypto miners,
no hidden endpoints, no encoded payloads and no code downloaded at runtime.

The review did find several non-malicious security issues. MilaCar fixes or removes them:

| Issue in Fermata | MilaCar |
|---|---|
| The built-in HTTP client trusted **any** TLS certificate and did not verify host names, so anyone on the same network could intercept or modify HTTPS traffic. | Server certificates are validated against the system trust store and host names are verified. |
| The exported `FermataContentProvider` let **any installed app** read any file the app can read, including its private preferences with the SMB/SFTP credentials. | Only images and the cached artwork can be read. Private app data is rejected. |
| The self-updater downloaded the upstream APK over that unverified connection and installed it, posing as the Play Store. | Removed, together with `REQUEST_DELETE_PACKAGES` and the companion app uninstall prompt. `REQUEST_INSTALL_PACKAGES` is kept only to reinstall the app's own installed APK for Android Auto (see below) – nothing is downloaded. |
| The browser addon granted **microphone and camera** to any page that requested DRM, and in the car granted them without asking. | Camera is never granted to web pages and the permission is removed. DRM is granted on its own. In the car the microphone is granted only if the user has already allowed it on the phone. |
| The root `rm` fallback did not quote the path, so a crafted file name could inject shell commands. | The path is quoted for the shell. |
| Preferences, including the network share credentials, were included in the Google cloud backup. | Cloud backup is disabled. Use *Settings → Export/Import preferences* to move settings. |
| The release signing key was derived from a well known default password. | Release builds must be signed with your own key (see below). |
| Crash reports could be e-mailed to the Fermata author. | Disabled. |
| Unused `READ_PHONE_STATE` permission. | Removed. |

### Leaner build

These addon modules are left out of the build by default. They are not needed in the car, send
data to third parties or depend on Google Play Services:

`chat` (OpenAI), `poi` (sends the location to a speed camera service), `mlkit`, `opusmt`,
`cast` and `whisper`.

The APK size goes from 158 MB to about 60 MB (arm64). The list can be changed with the
`EXCLUDE_MODULES` Gradle property or with `excludeModules` in `local.properties`. Use `none` to
build every module.

### Car UI

The new **Car (high contrast)** theme is the default for the Android Auto screen:

* pure black background – no glare at night, power saving on OLED head units
* white text with a single amber accent, readable in direct sunlight
* bigger playback buttons (84dp instead of 70dp), easier to hit while driving
* rounder corners and a clearly highlighted active item

You can change it in *Settings → Android Auto → Application theme*.

## Installing and using in the car

See the [Turkish user guide](docs/ARAC_KULLANIM_KILAVUZU.md) for details. In short:

1. Install the APK on the phone, open MilaCar once, grant the permissions and add your music
   folder.
2. In the Android Auto settings, tap **Version** 10 times to enable the developer mode.
3. Open **⋮ → Developer settings** and enable **Unknown sources**. This is enough for
   **MilaCar Media Service**, the standard Android Auto media screen.
4. To get the **full MilaCar UI** (YouTube, browser, video) in the car: Android Auto lists full
   screen apps only when they come from Google Play.
   * **Android 13 and older:** accept the *"Show the full app in Android Auto"* prompt on the
     first start, or use *Settings → Interface → Android Auto*. MilaCar passes its own installed
     APK to the system package installer with Google Play as the installer. Nothing is
     downloaded. If the device's installer ignores it, install once with
     `adb install -r -i com.android.vending milacar-<version>-arm64.apk`.
   * **Android 14 and newer:** installer tricks no longer work (see
     [Fermata discussion #432](https://github.com/AndreyPavlenko/Fermata/discussions/432)). You
     need either root with the LSPosed module (enable MilaCar in LSPosed) or a wireless adapter
     in the developer mode, such as [AAWireless](https://www.aawireless.io/) or
     [aa-proxy-rs](https://github.com/aa-proxy/aa-proxy-rs). Without them, use
     MilaCar Media Service.
5. Reconnect the phone to the car. **MilaCar** shows up in the Android Auto launcher.

## Building

Requirements: Android SDK, JDK 21 (javac from JDK 17.0.7 crashes on this code base).

```bash
git clone https://github.com/kobeumut/MilaCar.git
cd MilaCar
export ANDROID_SDK_ROOT=<path to the Android SDK>
export NO_GS=true
```

Create `local.properties` that points to **your own** signing key:

```properties
sdk.dir=<path to the Android SDK>
storeFile=<path to your keystore>.jks
keyAlias=<alias>
keyPassword=<password>
storePassword=<password>
```

A key can be generated with
`keytool -genkeypair -keystore milacar.jks -alias milacar -keyalg RSA -keysize 3072 -validity 36500`.

Build the APK:

```bash
./gradlew fermata:packageAutoReleaseUniversalApk -PABI=arm64-v8a
find fermata/build/outputs -name 'milacar-*.apk'
```

Use `-PABI=armeabi-v7a` for old 32-bit phones and `-PAPP_ID=<id>` to change the application ID.

## License and credits

* MilaCar is distributed under the [GNU General Public License v3](LICENSE).
* Based on [Fermata Media Player](https://github.com/AndreyPavlenko/Fermata) by Andrey Pavlenko
  and its contributors. If you like the player, please consider supporting the original project:
  [PayPal](https://www.paypal.com/donate/?hosted_button_id=NP5Q3YDSCJ98N).
* Beware of scam: Fermata and MilaCar are free. Sites selling "Fermata Auto" are not affiliated
  with either project.
