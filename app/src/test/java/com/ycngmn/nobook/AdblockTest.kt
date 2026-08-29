package com.ycngmn.nobook

import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import com.ycngmn.nobook.utils.nbTestContext
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.io.File

/**
 * Optional Facebook smoke tests. They require an authenticated test account and
 * a browser-capable environment, so CI skips them unless explicitly enabled.
 */
class AdblockTest {
    private fun requireFacebookE2eTests() {
        assumeTrue(
            "Set NOBOOK_RUN_FACEBOOK_TESTS=true to run Facebook browser tests",
            System.getenv("NOBOOK_RUN_FACEBOOK_TESTS") == "true"
        )
    }

    @Test
    fun testAdblock() {
        requireFacebookE2eTests()
        Playwright.create().use { playwright ->
            val options = BrowserType.LaunchOptions().setHeadless(true)
            playwright.chromium().launch(options).use { browser ->
                val context = browser.nbTestContext()
                val page = context.newPage()
                page.navigate("https://facebook.com/")
                page.evaluate(File("src/main/res/raw/scripts.js").readText())
                page.evaluate(File("src/main/res/raw/adblock.js").readText())
                page.waitForTimeout(2_000.0)
            }
        }
    }
}
