#include <jni.h>
#include <string>
#include <android/log.h>
#include "llama.h"

#define LOG_TAG "LlamaJNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static struct llama_context* ctx = nullptr;

extern "C"
JNIEXPORT void JNICALL
Java_com_mehdiatique_llamawrapper_LlamaBridge_initLlama(JNIEnv* env, jobject, jstring modelPath) {
    const char* path = env->GetStringUTFChars(modelPath, 0);

    llama_backend_init();

    llama_model_params model_params = llama_model_default_params();
    llama_model* model = llama_model_load_from_file(path, model_params);

    llama_context_params ctx_params = llama_context_default_params();
    ctx = llama_init_from_model(model, ctx_params);

    LOGI("Model loaded from path: %s", path);

    env->ReleaseStringUTFChars(modelPath, path);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_mehdiatique_llamawrapper_LlamaBridge_runPrompt(JNIEnv *env, jobject, jstring prompt) {
    const char* input = env->GetStringUTFChars(prompt, 0);

    if (!ctx) {
        LOGI("Llama not initialized");
        return env->NewStringUTF("Error: Llama not initialized");
    }

    // TODO: Tokenize and run inference using llama.cpp APIs
    std::string output = "TODO: Run llama on input";

    env->ReleaseStringUTFChars(prompt, input);
    return env->NewStringUTF(output.c_str());
}