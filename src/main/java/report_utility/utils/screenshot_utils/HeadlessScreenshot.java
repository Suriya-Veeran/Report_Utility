package report_utility.utils.screenshot_utils;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import report_utility.utils.webdriver.WebDriverConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

import static report_utility.constants.FormatConstants.PNG_EXTENSION;
import static report_utility.constants.JavaScriptConstants.*;
import static report_utility.constants.PathConstants.SNAP_FILES;
import static report_utility.constants.SpecialCharacterConstants.HYPHEN;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeadlessScreenshot {

    public static Image takeScreenshot(String url, String browserType, String chartType) {

        File screenshotFile;
        String filePath = SNAP_FILES + File.separator + chartType + HYPHEN + UUID.randomUUID() + PNG_EXTENSION;
        WebDriver driver = WebDriverConfig.getInstance(browserType);
        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(CHART_NAME)));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(HIDDEN_SCRIPT);
            js.executeScript(HEIGHT_SCRIPT);
            Thread.sleep(3000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(CANVAS_NAME)));
            js.executeScript(SCROLL_SCRIPT);
            js.executeScript(RESIZE_SCRIPT);
            Thread.sleep(3000);
            screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshotFile.toPath(), Path.of(filePath));
            ImageData imageData = ImageDataFactory.create(filePath);
            return new Image(imageData);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while taking screenshot: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread was interrupted while taking a screenshot", e);
        } finally {
            driver.quit();
        }
    }
}
