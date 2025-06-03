package com.p3.ads.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

@UtilityClass
@Slf4j
public class ImageUtils {

    public static Image loadImage(String imagePath,
                                  float fitWidth,
                                  float fitHeight) {
        try {
            if (imagePath == null || imagePath.isBlank()) {
                log.warn("No image path provided for logo.");
                return null;
            }

            byte[] imageBytes = Objects.requireNonNull(
                    ImageUtils.class.getClassLoader().getResourceAsStream(imagePath)
            ).readAllBytes();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            ImageData imageData = ImageDataFactory.create(ImageIO.read(inputStream), null);
            return new Image(imageData).scaleToFit(fitWidth, fitHeight);

        } catch (IOException | NullPointerException e) {
            log.error("Failed to load image '{}': {}", imagePath, e.getMessage(), e);
            return null;
        }
    }

}
