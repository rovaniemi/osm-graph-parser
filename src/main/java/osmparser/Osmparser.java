package osmparser;

import java.util.ArrayList;

public class Osmparser {

    public static void main(String[] args) {
        Parser parser = new Parser(new ArrayList<String>(){{
                add("highway");
            }});
        parser.startParsing();
    }
}
