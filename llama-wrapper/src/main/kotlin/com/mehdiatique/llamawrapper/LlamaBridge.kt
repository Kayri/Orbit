package com.mehdiatique.llamawrapper

object LlamaBridge {
    init {
        System.loadLibrary("llama_jni")
        LlamaBridge.initLlama("path_to_your_model.gguf")
    }

    external fun initLlama(modelPath: String)
    external fun runPrompt(prompt: String): String
}
