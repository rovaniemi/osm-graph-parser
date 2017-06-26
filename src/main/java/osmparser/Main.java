package osmparser;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static String PROGRAM_NAME = "osmparser";
    private static int EXIT_FAIL = 1;

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException ex) {
            error(ex.getMessage());
            new HelpFormatter().printHelp(PROGRAM_NAME, options);
            System.exit(EXIT_FAIL);
        }

        boolean notQuiet = cmd.hasOption("quiet");

        String[] files = cmd.getOptionValues("files");
        if (notQuiet) {
            info("Parsing files: " + Arrays.asList(files));
        }

        String[] includeWayProps = cmd.getOptionValues("includeWays");
        String[] excludeWayProps = cmd.getOptionValues("excludeWays");

        WayTag[] includedTags = parseWayTagsFromOptions(includeWayProps);
        WayTag[] excludedTags = parseWayTagsFromOptions(excludeWayProps);

        if (notQuiet) {
            info("Included tags: " + Arrays.asList(includedTags));
            info("Excluded tags: " + Arrays.asList(excludedTags));
        }

        String output = cmd.getOptionValue("output");

        if(notQuiet && output != null) {
            info("Output file name: " + output);
        } else if(notQuiet){
            info("Output file name: graph.json");
        }

        try {
            createOsmparser(files, output, includedTags, excludedTags).start();
        } catch (RuntimeException ex) {
            if (!(ex.getCause() instanceof IOException)) {
                throw ex;
            }
            System.out.println("Error while reading file: " + ex.getCause().getMessage());
        }
    }

    private static WayTag[] parseWayTagsFromOptions(String[] optionValues) {
        if (optionValues == null || optionValues.length == 0) {
            return new WayTag[0];
        }

        WayTag[] tags = new WayTag[optionValues.length];
        for (int i = 0; i < tags.length; i++) {
            // Split key=value pairs, skipping escaped \=
            String[] tokens = optionValues[i].split("(?<!\\\\)=", 2);
            WayTag tag = tokens.length == 1
                ? new WayTag(tokens[0])
                : new WayTag(tokens[0], tokens[1]);
            tags[i] = tag;
        }

        return tags;
    }

    private static Osmparser createOsmparser(String[] files, String output, WayTag[] includeWays, WayTag[] excludeWays) {
        StreamingXmlGraphParser graphParser = new StreamingXmlGraphParser(includeWays, excludeWays);
        if(output == null) return new Osmparser(files, "graph.json", graphParser);
        return new Osmparser(files, output, graphParser);
    }

    private static Options createOptions() {
        Options options = new Options();

        Option files = Option.builder("f")
            .longOpt("files")
            .desc("the osm (xml) map files to be parsed")
            .hasArgs()
            .required()
            .build();
        options.addOption(files);

        Option verbose = Option.builder("q")
            .longOpt("quiet")
            .desc("suppress console output")
            .build();
        options.addOption(verbose);

        Option includeWays = Option.builder("i")
            .longOpt("includeWays")
            .desc("way tags to include")
            .hasArgs()
            .required()
            .build();
        options.addOption(includeWays);

        Option excludeWays = Option.builder("e")
            .longOpt("excludeWays")
            .desc("way tags to exclude (overrides includeWay)")
            .hasArgs()
            .build();
        options.addOption(excludeWays);

        Option output = Option.builder("o")
            .longOpt("output")
            .desc("output file name")
            .hasArg()
            .build();
        options.addOption(output);

        return options;
    }

    // TODO: use a proper logger library
    private static void info(String message) {
        System.out.println(message);
    }

    private static void info(String fmt, Object... args) {
        System.out.printf(fmt + "%n", args);
    }

    private static void error(String message) {
        System.out.println(message);
    }
}
