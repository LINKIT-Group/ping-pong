package nl.linkit.itnext.pingpong.managers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Developer: efe.kocabas
 * Date: 20/07/2017.
 */

public class VoiceManagerImpl implements VoiceManager {

    private Activity activity;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private VoiceManagerListener voiceManagerListener;
    private int lastListeningType = 0;
    private Intent speechIntent;

    public interface VoiceManagerListener {

        void onResults(ArrayList<String> results, int type);

        void onSpeechCompleted(int requestCode);
    }

    public VoiceManagerImpl(Activity activity) {

        this.activity = activity;
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
        this.speechRecognizer.setRecognitionListener(recognitionListener);

        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);


    }

    @Override
    public void setVoiceManagerListener(VoiceManagerListener voiceManagerListener) {

        this.voiceManagerListener = voiceManagerListener;
    }


    @Override
    public void speak(int requestCode, int speechResourceId, Object... formatArgs) {

        String speech = activity.getString(speechResourceId, formatArgs);
        speak(requestCode, speech);
    }

    @Override
    public void speak(int requestCode, int speechResourceId) {

        String speech = activity.getString(speechResourceId);
        speak(requestCode, speech);
    }

    private void speak(int requestCode, String speech) {

        stopListening();
        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(requestCode));


        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }

        this.textToSpeech = new TextToSpeech(activity, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.ENGLISH);
                textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);
                textToSpeech.speak(speech, TextToSpeech.QUEUE_FLUSH, params);
            }
        });

    }

    @Override
    public void listen(int type) {

        lastListeningType = type;

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = () -> speechRecognizer.startListening(speechIntent);
        mainHandler.post(myRunnable);
    }

    private void stopListening() {

        speechRecognizer.stopListening();
    }

    @Override
    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }

        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }


    RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

            Log.i("VOICE", "VoiceManager onBeginofSpeech");

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

            Log.i("VOICE", "VoiceManager onEndOfSpeech");

        }

        @Override
        public void onError(int errorCode) {

            Log.i("VOICE", "VoiceManager onError" + errorCode);

            if ((errorCode == SpeechRecognizer.ERROR_NO_MATCH) || (errorCode == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)) {

                listen(lastListeningType);
            }
        }

        @Override
        public void onResults(Bundle bundle) {

            Log.i("VOICE", "VoiceManager onResults");

            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            voiceManagerListener.onResults(results, lastListeningType);
        }

        @Override
        public void onPartialResults(Bundle bundle) {

            Log.i("VOICE", "VoiceManager onpartial");
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };


    UtteranceProgressListener utteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String s) {

        }

        @Override
        public void onDone(String s) {

            int requestCode = Integer.parseInt(s);
            if (voiceManagerListener != null) {
                voiceManagerListener.onSpeechCompleted(requestCode);
            }
        }

        @Override
        public void onError(String s) {

        }
    };
}
