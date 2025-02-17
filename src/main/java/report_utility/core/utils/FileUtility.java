package report_utility.core.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

@Slf4j
@UtilityClass
public class FileUtility {
    private static final String PDF_EXTENSION = ".pdf";

    /**
     * Creates an output PDF file with a random filename (default overwrite).
     *
     * @param outputPath The directory where the file should be created.
     * @return The created file.
     * @throws IOException If file creation fails.
     */
    public static File createOutputFile(String outputPath) throws IOException {
        return createOutputFile(outputPath, UUID.randomUUID() + PDF_EXTENSION);
    }

    /**
     * Creates an output PDF file with the specified filename (default overwrite).
     *
     * @param outputPath The directory where the file should be created.
     * @param fileName   The name of the PDF file.
     * @return The created file.
     * @throws IOException If file creation fails.
     */
    public static File createOutputFile(String outputPath, String fileName) throws IOException {
        return createOutputFile(outputPath, fileName, true);  // Default overwrite enabled
    }

    /**
     * Creates an output PDF file with the specified filename and overwrite option.
     *
     * @param outputPath The directory where the file should be created.
     * @param fileName   The name of the PDF file.
     * @param overwrite  Whether to overwrite an existing file.
     * @return The created file.
     * @throws IOException If file creation fails.
     */
    public static File createOutputFile(String outputPath, String fileName, boolean overwrite) throws IOException {
        File outputDir = ensureDirectoryExists(outputPath);

        if (!fileName.endsWith(PDF_EXTENSION)) {
            throw new IllegalArgumentException("Invalid PDF file name: " + fileName);
        }

        Path outputFile = outputDir.toPath().resolve(fileName);

        // Overwrite logic using Files.delete()
        if (Files.exists(outputFile) && overwrite) {
            Files.delete(outputFile);
        }

        return Files.createFile(outputFile).toFile();
    }

    /**
     * Ensures the output directory exists, creating it if necessary.
     *
     * @param outputPath The path of the directory.
     * @return The directory file object.
     * @throws IOException If the directory cannot be created.
     */
    public static File ensureDirectoryExists(String outputPath) throws IOException {
        Path dirPath = Path.of(outputPath);
        if (Files.notExists(dirPath)) {
            Files.createDirectories(dirPath);
        } else if (!Files.isDirectory(dirPath)) {
            throw new IOException("Path exists but is not a directory: " + outputPath);
        }
        return dirPath.toFile();
    }

    private static void deleteTempFiles(String directoryPath) {
        try {
            Path dirPath = Paths.get(directoryPath);
            if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                log.warn("Directory does not exist: {}", directoryPath);
                return;
            }

            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.deleteIfExists(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
            log.info("Deleted all temporary files in {}", directoryPath);
        } catch (IOException e) {
            log.error("Failed to delete files in {}: {}", directoryPath, e.getMessage());
        }
    }

    public static void cleanTemporaryFiles() {
        deleteTempFiles("src/main/resources/HtmlFiles");
        deleteTempFiles("src/main/resources/SnapFiles");
    }
}
