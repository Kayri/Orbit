# cpp/ggml/CMakeLists.txt
add_library(common
        common.cpp
        sampling.cpp
)

target_include_directories(common PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})