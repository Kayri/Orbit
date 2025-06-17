#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "LlamaJNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

// Placeholder logic – we'll integrate llama.cpp here later
extern "C"
JNIEXPORT jstring JNICALL
Java_com_mehdiatique_llamawrapper_LlamaBridge_runPrompt(JNIEnv *env, jobject /* this */, jstring prompt) {
    const char *input = env->GetStringUTFChars(prompt, nullptr);
    LOGI("Received prompt: %s", input);

    std::string response = "You said: ";
    response += input;

    env->ReleaseStringUTFChars(prompt, input);
    return env->NewStringUTF(response.c_str());
}