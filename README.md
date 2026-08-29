# Nobook

<p align="center">
  <img src="images/nobook_github_cover.png" height="200" alt="Nobook cover">
</p>

<div align="center">A lightweight Android app for a cleaner Facebook browsing experience.</div>

## Features

- **Local ad blocking** — hides clearly labelled sponsored posts without blocking ordinary posts, links, or media.
- **Feed controls** — hide suggested posts, Reels, Stories, groups, or People You May Know when you want a quieter feed.
- **Media downloads** — save supported Facebook images and videos to your Downloads folder.
- **Copy to clipboard** — copy supported media from the media viewer.
- **Mobile and desktop layouts** — choose the layout that works best for your device.
- **Reading preferences** — use pinch-to-zoom, sticky navigation, immersive mode, and AMOLED black mode.
- **Facebook link support** — open Facebook links from other apps, including `facebook.com`, `www.facebook.com`, and `m.facebook.com`.
- **Performance improvements** — reduce background WebView work and pause off-screen video processing to improve battery life while browsing long feeds.

## Privacy and ad blocking

Nobook runs its filtering inside the Facebook WebView on your device. It does not proxy your traffic or send browsing data to a separate ad-blocking service. The ad blocker is designed to act only on content Facebook identifies as sponsored.

Facebook changes its page structure regularly, so filtering can occasionally miss an ad or require an update. If a normal post is hidden, please report the device model, app version, language, and whether mobile or desktop layout was enabled.

## Installation

[<img src="images/get-it-on-github.png" alt="Get it on GitHub" height="90">](https://github.com/ycngmn/Nobook/releases/latest)

Download the latest APK from the project's GitHub Releases page. Android may ask you to allow installation from the source you used to download the APK.

## Building from source

1. Clone the repository:

   ```bash
   git clone https://github.com/mhamzas/Nobook.git
   cd Nobook
   ```

2. Open the project in Android Studio.
3. Allow Gradle to sync and install any requested Android SDK components.
4. Run the `app` configuration on an emulator or Android device.

To build a local debug APK from a terminal with JDK 17 and the Android SDK installed:

```bash
sh ./gradlew assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Continuous integration

GitHub Actions runs Android CI for pull requests and pushes to `main`. The workflow uses JDK 17, runs unit tests, builds a debug APK, and uploads it as the `nobook-debug-apk` artifact.

Versioned release builds run when a `v*.*.*` tag is pushed. Release signing requires the repository's configured GitHub Actions signing secrets.

## Contributing

Contributions are welcome:

1. Fork the repository.
2. Create a branch for your feature or fix.
3. Test mobile and desktop layouts where relevant.
4. Submit a pull request with a clear description and testing notes.

Please avoid including Facebook credentials, cookies, or personal account data in issues, pull requests, or test fixtures.

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for the complete history of user-facing changes.

## Acknowledgements

- [compose-webview-multiplatform](https://github.com/KevinnZou/compose-webview-multiplatform)
