package nl.linkit.itnext.pingpong.managers;

/**
 * Developer: efe.kocabas
 * Date: 20/07/2017.
 */

public interface VoiceManager {

    void speak(int requestCode, int speechResourceId, Object... formatArgs);

    void speak(int requestCode, int speechResourceId);

    void listen(int requestCode);

    void shutdown();

    void setVoiceManagerListener(VoiceManagerImpl.VoiceManagerListener listener);
}
