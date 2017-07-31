package nl.linkit.itnext.pingpong.managers;

import android.util.Log;

import java.util.Arrays;

import nl.linkit.itnext.pingpong.BuildConfig;

/**
 * Author: efe.cem.kocabas
 * Date: 24/03/2017.
 */

public class LogManagerImpl implements LogManager {

    private static final String METHOD_LABEL = " Method: ";
    private static final String MESSAGE_LABEL = " Message: ";
    private static final String STACKTRACE_LABEL = " Stacktrace: ";

    @Override
    public void logError(Throwable throwable) {

        StackTraceElement[] stackTrace = throwable.getStackTrace();
        StackTraceElement element = stackTrace[0];
        String methodName = element.getMethodName();
        String className = element.getClassName();

        String logString = METHOD_LABEL +
                methodName +
                MESSAGE_LABEL +
                throwable.getMessage() +
                STACKTRACE_LABEL +
                Arrays.toString(throwable.getStackTrace());

        logError(className, logString);
    }

    @Override
    public void logError(String tag, String methodName, String errorMessage) {

        String logString = METHOD_LABEL +
                methodName +
                MESSAGE_LABEL +
                errorMessage;

        logError(tag, logString);
    }

    @Override
    public void logInfo(String tag, String methodName, String message) {

        String logString = METHOD_LABEL +
                methodName +
                MESSAGE_LABEL +
                message;

        logInfo(tag, logString);
    }

    private void logError(String tag, String message) {

        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }

    private void logInfo(String tag, String message) {

        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

}
