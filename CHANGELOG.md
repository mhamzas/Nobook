# Changelog

All notable Nobook changes are documented here.

## Unreleased

### New

- Added GitHub Actions CI for pull requests, pushes to `main`, and manual runs.
- CI uploads a debug APK artifact after a successful build and test run.
- Added support for opening Facebook links from Android, including `facebook.com`, `www.facebook.com`, and `m.facebook.com`.
- Added support for HTTP and HTTPS Facebook links.
- Added battery and rendering optimizations for long feeds.

### Improved

- Sponsored content filtering now runs locally and targets clearly labelled sponsored posts.
- Ad blocking is less likely to hide ordinary posts that merely mention advertising or sponsorship.
- Ad filtering continues to work as Facebook loads new content dynamically.
- JavaScript helpers are bundled in the APK by default, so startup no longer depends on downloading scripts from GitHub.
- Settings initialization no longer blocks the main thread while reading preferences.
- External link handling now validates parsed hosts instead of relying on a broad URL pattern.
- Downloads now validate data and report failures more accurately, including incomplete MediaStore writes.
- WebView timers and media processing are paused while the app is in the background and resumed when it returns.
- README documentation now explains privacy-friendly ad blocking and CI artifacts.

### Notes

- Facebook frequently changes its page markup. Ad filtering and supported-link behavior should be tested on both mobile and desktop layouts after each release.
- Release builds still require the repository's configured signing secrets in GitHub Actions.
