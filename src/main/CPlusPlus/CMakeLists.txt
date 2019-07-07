cmake_minimum_required(VERSION 3.0.0)
project(Echo VERSION 0.1.0)

include(CTest)
enable_testing()

add_executable(Echo Echo.cc)
add_executable(EchoAgain EchoAgain.cc)

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)

set_target_properties(Echo PROPERTIES
    CXX_STANDARD 17
    CXX_STANDARD_REQUIRED ON
    CXX_EXTENSIONS ON
)

set_target_properties(EchoAgain PROPERTIES
    CXX_STANDARD 17
    CXX_STANDARD_REQUIRED ON
    CXX_EXTENSIONS ON
)