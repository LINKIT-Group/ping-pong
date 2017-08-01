package nl.linkit.itnext.pingpong.managers;

/**
 * Author: efe.cem.kocabas
 * Date: 24/03/2017.
 */

public interface LogManager {

    void logError(Throwable throwable);

    void logError(String tag, String methodName, String errorMessage);

    void logInfo(String tag, String methodName, String message);
}
