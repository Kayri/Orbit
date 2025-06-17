# cpp/ggml/ggml-cpu/CMakeLists.txt
add_library(ggml-cpu
        amx/amx.cpp
        amx/mmq.cpp
        ggml-cpu.cpp
        quants.c
        repack.cpp
        traits.cpp
)
target_include_directories(ggml-cpu PUBLIC ${CMAKE_CURRENT_SOURCE_DIR} ${CMAKE_CURRENT_SOURCE_DIR}/..)
target_link_libraries(ggml-cpu PRIVATE ggml)