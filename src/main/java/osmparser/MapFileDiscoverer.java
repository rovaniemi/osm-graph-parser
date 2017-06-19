package osmparser;

import java.io.File;
import java.io.FilenameFilter;

public class MapFileDiscoverer {

    public static File[] discover(String directory, String mapFilePrefix) {
        return new File(directory).listFiles(filterPrefixedOsmFiles(mapFilePrefix));
    }

    private static FilenameFilter filterPrefixedOsmFiles(final String filePrefix) {
        if (filePrefix != null) {
            return new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(filePrefix) && name.endsWith(".osm");
                }
            };
        } else {
            return new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".osm");
                }
            };
        }
    }
}
