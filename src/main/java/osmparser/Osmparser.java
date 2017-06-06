package osmparser;

import java.io.IOException;
import java.util.ArrayList;

public class Osmparser {

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(new ArrayList<String>(){{
                add("highway");
            }});
        parser.startParsing();
    }
}
