package simulator.launcher;

import org.apache.commons.cli.*;
import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static Integer _timeLimitDefaultValue = 10;
    private static String _inFile = null;
    private static String _outFile = null;
    private static Mode _mode = null;
    private static Factory<Event> _eventsFactory = null;
    private static int ticks;

    private static void parseArgs(String[] args) {

        // define the valid command line options
        //
        Options cmdLineOptions = buildOptions();

        // parse the command line as provided in args
        //
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(cmdLineOptions, args);
            parseHelpOption(line, cmdLineOptions);
            parseModeOption(line);
            parseInFileOption(line);
            parseOutFileOption(line);
            parseTicksOption(line);

            // if there are some remaining arguments, then something wrong is
            // provided in the command line!
            //
            String[] remaining = line.getArgs();
            if (remaining.length > 0) {
                String error = "Illegal arguments:";
                for (String o : remaining)
                    error += (" " + o);
                throw new ParseException(error);
            }

        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(1);
        }

    }

    private static Options buildOptions() {
        Options cmdLineOptions = new Options();

        cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
        cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10).").build());
        cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
        cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Selects between GUI or CONSOLE.").build());
        cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());

        return cmdLineOptions;
    }

    private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
        if (line.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
            System.exit(0);
        }
    }

    private static void parseModeOption(CommandLine line) throws ParseException {
        if (line.hasOption("m")) {
            _mode = Mode.valueOf(line.getOptionValue("m").toUpperCase());
            if (_mode != Mode.CONSOLE && _mode != Mode.GUI) {
                throw new ParseException("Mode: " + _mode + " is not a supported mode.");
            }
        }
        if (_mode == null) {
            _mode = Mode.GUI;
        }
    }

    private static void parseTicksOption(CommandLine line) {
        if (line.hasOption("t")) {
            ticks = Integer.parseInt(line.getOptionValue("t"));
        } else {
            ticks = _timeLimitDefaultValue;
        }
    }

    private static void parseInFileOption(CommandLine line) throws ParseException {
        _inFile = line.getOptionValue("i");
        if (_inFile == null && _mode == Mode.CONSOLE) { //only in console its obligatory to provide an events file
            throw new ParseException("An events file is missing");
        }
    }

    private static void parseOutFileOption(CommandLine line) throws ParseException {
        if (_mode == Mode.CONSOLE) { //in gui mode this is ignored
            _outFile = line.getOptionValue("o");
        }
    }

    private static void initFactories() {
        List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
        lsbs.add(new RoundRobinStrategyBuilder());
        lsbs.add(new MostCrowdedStrategyBuilder());
        Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
        List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
        dqbs.add(new MoveFirstStrategyBuilder());
        dqbs.add(new MoveAllStrategyBuilder());
        Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
        List<Builder<Event>> ebs = new ArrayList<>();
        ebs.add(new SetContClassEventBuilder());
        ebs.add(new SetWeatherEventBuilder());
        ebs.add(new NewVehicleEventBuilder());
        ebs.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
        ebs.add(new NewCityRoadEventBuilder());
        ebs.add(new NewInterCityRoadEventBuilder());
        _eventsFactory = new BuilderBasedFactory<>(ebs);

    }

    private static void startBatchMode() throws IOException {
        FileInputStream in = new FileInputStream(new File(_inFile));
        TrafficSimulator ts = new TrafficSimulator();
        Controller c = new Controller(ts, _eventsFactory);
        FileOutputStream ou = null;
        if (_outFile != null)
            ou = new FileOutputStream(_outFile);
        c.loadEvents(in);
        c.run(ticks, ou);

    }

    private static void startGUIMode() throws IOException {
        TrafficSimulator ts = new TrafficSimulator();
        Controller c = new Controller(ts, _eventsFactory);
        if (_inFile != null) {
           FileInputStream in = new FileInputStream(new File(_inFile));
       }
        SwingUtilities.invokeLater(() -> new MainWindow(c));
    }

    private static void start(String[] args) throws IOException {
        initFactories();
        parseArgs(args);
        if (_mode == Mode.CONSOLE) {
            startBatchMode();
        }
        else {
            startGUIMode();
        }
    }

    public static void main(String[] args) {
        try {
            start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private enum Mode {GUI, CONSOLE}

}
