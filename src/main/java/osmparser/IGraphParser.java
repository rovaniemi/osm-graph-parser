package osmparser;

import java.io.File;

public interface IGraphParser {
    void parseXml(File file, Graph outputGraph);
}
