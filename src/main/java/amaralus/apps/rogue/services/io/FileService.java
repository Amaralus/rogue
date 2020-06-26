package amaralus.apps.rogue.services.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    public List<String> loadFileAsLinesFromResources(String path) {
        try {
            return loadFileLines(getResourcePath(path));
        } catch (Exception e) {
            throw new LoadFileException(e);
        }
    }

    private List<String> loadFileLines(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        }
    }

    private Path getResourcePath(String path) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(path).toURI());
    }
}
