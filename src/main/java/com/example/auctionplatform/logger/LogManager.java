package com.example.auctionplatform.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private static final String LOG_FILE_USER = "Log/user.log";
    private static final String LOG_FILE_ORDER = "Log/order.log";
    private static final String LOG_FILE_OTHER_ERROR = "Log/error.log";
    private static final String LOG_FILE_OTHER_WARNING = "Log/warning.log";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void Log(LogLevel level, String path,String msg) {
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        String logMessage = String.format("[%s] [%s] %s\n", timestamp, level, msg);

        // Print to console
        System.out.println(logMessage);
        // Append to log file
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
    public static void LogUser(LogLevel level,String msg) {
        Log(level, LOG_FILE_USER,msg);
    }
    public static void LogOrder(LogLevel level, String msg) {
        Log(level, LOG_FILE_ORDER,msg);
    }
    public static void LogOtherError(String msg) {
        Log(LogLevel.ERROR, LOG_FILE_OTHER_ERROR,msg);
    }
    public static void LogOtherWarning(String msg) {
        Log(LogLevel.WARN, LOG_FILE_OTHER_WARNING,msg);
    }
}
