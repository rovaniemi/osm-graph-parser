package osmparser;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Osmparser {

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser("highway");
        File[] mapFiles = MapFileDiscoverer.discover("map/", "map-");
        parser.startParsing(mapFiles);
    }
}
