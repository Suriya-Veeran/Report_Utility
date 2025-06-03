package com.p3.ads.utils.browser;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.p3.ads.constants.WebDriverConfigConstants.*;


@UtilityClass
public class ChromeConfig{

  public ChromeDriver createDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments(HEADLESS_MODE);
    options.addArguments(DISABLE_GPU);
    options.addArguments(WINDOW_SIZE);
    return new ChromeDriver(options);
  }
}
