package osmparser;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
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
            System.out.println(ex.getMessage());
            new HelpFormatter().printHelp(PROGRAM_NAME, options);
            System.exit(EXIT_FAIL);
        }

        boolean verbose = cmd.hasOption("verbose");

        String[] files = cmd.getOptionValues("files");
        if (verbose) {
            System.out.println("Parsing files: " + Arrays.asList(files));
        }

        try {
            new Osmparser(files, "graph.json").start();
        } catch (RuntimeException ex) {
            if (!(ex.getCause() instanceof IOException)) {
                throw ex;
            }
            System.out.println("Error while reading file: " + ex.getCause().getMessage());
        }
    }

    private static Options createOptions() {
        Options options = new Options();

        Option files = Option.builder("f")
            .longOpt("files")
            .desc("the XML map files to be parsed")
            .hasArgs()
            .required()
            .build();
        options.addOption(files);

        Option verbose = Option.builder("v")
            .longOpt("verbose")
            .desc("print verbose output")
            .build();
        options.addOption(verbose);

        return options;
    }
}
