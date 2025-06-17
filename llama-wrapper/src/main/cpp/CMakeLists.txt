cmake_minimum_required(VERSION 3.18.1)

project(llama_jni)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_subdirectory(llama)
add_subdirectory(ggml)

add_library(
        llama_jni SHARED
        llama_jni.cpp
)

target_include_directories(
        llama_jni PRIVATE
        ${CMAKE_CURRENT_SOURCE_DIR}
        ${CMAKE_CURRENT_SOURCE_DIR}/llama
        ${CMAKE_CURRENT_SOURCE_DIR}/ggml
        ${CMAKE_CURRENT_SOURCE_DIR}/common
)

find_library(log-lib log)

target_link_libraries(
        llama_jni
        llama
        ggml
        ${log-lib}
)
