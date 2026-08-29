# Nobook
<p align="center">
  <img src='images/nobook_github_cover.png' height='200' alt="nobook_cover">
</p>

<div align="center">Nobook is a lightweight Android application to browse facebook.</div>

## • Features

*  Hides clearly labelled sponsored ads without blocking ordinary posts or media.
*  Blocks suggested posts.
*  Downloads media.
* And more.

## • Installation

[<img src='images/get-it-on-github.png' alt='Get it on GitHub' height = "90">](https://github.com/ycngmn/Nobook/releases/latest)

## • Setup

1.  **Clone the repository**
    * In Android Studio:
        * File > New > Project from Version Control
        * Paste `https://github.com/ycngmn/Nobook.git` and clone.
    * Or via terminal:
    ```bash
    git clone https://github.com/ycngmn/Nobook.git
    cd Nobook
    ``` 
2.  **Open in Android Studio.** (only if cloned via terminal)
    * Select Open an Existing Project and choose the cloned folder.
3. **Sync the project** to download dependencies.
4. **Run the app** in a device or emulator.

## • Continuous integration

Pull requests and pushes to `main` run the Android CI workflow. It uses JDK 17, runs unit tests, builds a debug APK, and uploads the APK as a workflow artifact. Versioned releases are built by the release workflow when a `v*.*.*` tag is pushed.


## • Ad blocking

Nobook's ad blocker runs locally inside the Facebook WebView. It only hides content that Facebook marks as sponsored, does not proxy traffic, and leaves regular posts, links, and media untouched. Facebook can change its markup at any time, so please report false positives or missed ads with the device, language, and layout details.

## • Contributing

Contributions to the project are welcome. Please follow these guidelines:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Submit a pull request with a clear description of your changes.

## Acknowledgement :
* [@KevinnZou/compose-webview-multiplatform](https://github.com/KevinnZou/compose-webview-multiplatform)  