package com.p3.ads.utils.webdriver;


import lombok.experimental.UtilityClass;
import org.openqa.selenium.WebDriver;
import com.p3.ads.utils.browser.ChromeConfig;
import com.p3.ads.utils.browser.EdgeConfig;
import com.p3.ads.utils.browser.FirefoxConfig;

import java.util.Locale;

@UtilityClass
public class WebDriverConfig {

  public static WebDriver getInstance(String browser) {
    browser = browser.toLowerCase(Locale.ROOT);

    switch (browser.toLowerCase()) {
      case "chrome":
        return ChromeConfig.createDriver();
      case "edge":
        return EdgeConfig.createDriver();
      case "firefox":
        return FirefoxConfig.createFirefoxDriver();
      default:
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
  }
}
