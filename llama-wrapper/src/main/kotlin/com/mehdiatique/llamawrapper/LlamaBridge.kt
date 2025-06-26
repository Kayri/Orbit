package com.mehdiatique.llamawrapper

object LlamaBridge {
    init {
        System.loadLibrary("llama_jni")
        initLlama("path_to_your_model.gguf")
    }

    external fun initLlama(modelPath: String)
    external fun releaseLlama()
    external fun resetContext()
    external fun runPrompt(prompt: String): String
    external fun setParams()
}
