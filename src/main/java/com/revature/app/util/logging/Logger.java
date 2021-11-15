package com.revature.app.util.logging;

import com.revature.app.util.strings.Color;
import com.revature.app.util.types.Labeled;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class Logger {

    public enum Printer implements Labeled {
        FILE("FILE"), CONSOLE("CONSOLE");

        private final String label;

        Printer(String label) {
            this.label = label;
        }

        @Override
        public String label() {
            return this.label;
        }
    }

    public enum Level {
        INFO, WARN, ERROR, FATAL;
    }
    private static Logger logger;
    private static final String LOG_FILE = "src/main/resources/log/app.log";

    private final Printer[] printers;
    private final Level maxLogLevel;

    private Logger(Level maxLogLevel, Printer[] printers) {
        if (printers == null || printers.length == 0) {
            this.printers = new Printer[]{};
        } else {
            this.printers = printers;
        }
        if (maxLogLevel == null) {
            this.maxLogLevel = Level.FATAL;
        } else {
            this.maxLogLevel = maxLogLevel;
        }
    }

    public static Logger getLogger(Printer... printers) {
        return Logger.getLogger(Level.FATAL, printers);
    }

    public static Logger getLogger(Level maxLogLevel, Printer... printers) {
        if (Logger.logger == null) {
            Logger.logger = new Logger(maxLogLevel, printers);

        }
        return logger;
    }

    public void log(Level logLevel, String msg, Object... args) {
        if (logLevel.ordinal() <= this.maxLogLevel.ordinal()) {
            String formattedMsg = String.format(logLevel.name(), msg, Arrays.toString(args));
            for (Printer p : this.printers) {
                switch (p) {
                    case CONSOLE:
                        System.out.println(Color.ANSI.YELLOW + formattedMsg + Color.ANSI.RESET);
                    case FILE:
                     try (Writer logWriter = new FileWriter(Logger.LOG_FILE, true)) {
                         logWriter.write(formattedMsg +"\n");
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     break;

                }
            }
        }
    }
}
