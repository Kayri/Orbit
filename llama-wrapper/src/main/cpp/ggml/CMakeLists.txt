# cpp/ggml/CMakeLists.txt
add_library(ggml
        ggml.c
        ggml-alloc.c
        ggml-backend-reg.cpp
        ggml-backend.cpp
        ggml-opt.cpp
        ggml-quants.c
        ggml-threading.cpp
        gguf.cpp
)
add_subdirectory(ggml-cpu)

target_include_directories(ggml PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(ggml PUBLIC ggml-cpu)