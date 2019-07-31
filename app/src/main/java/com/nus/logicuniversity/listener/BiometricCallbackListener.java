package com.nus.logicuniversity.listener;

public interface BiometricCallbackListener {
    void onAuthenticationHelp(int helpCode, CharSequence helpString);
    void onAuthenticationSuccessful();
    void onAuthenticationCancelled();
    void onAuthenticationError(int errorCode, CharSequence errorString);
    void onAuthenticationFailed();
}
