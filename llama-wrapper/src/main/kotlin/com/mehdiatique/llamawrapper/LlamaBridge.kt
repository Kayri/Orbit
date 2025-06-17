package com.mehdiatique.llamawrapper

object LlamaBridge {
    init {
        System.loadLibrary("llama_jni")
    }

    external fun runPrompt(prompt: String): String
}
