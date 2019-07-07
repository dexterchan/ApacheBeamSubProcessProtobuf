cmake_minimum_required(VERSION 3.0.0)
project(Echo VERSION 0.1.0)

include(CTest)
enable_testing()

add_library(ctest1 SHARED ctest1.cc)
add_library(ctest2 SHARED ctest2.cc)


install(TARGETS ctest1 DESTINATION lib)
install(TARGETS ctest2 DESTINATION lib)

add_executable(Echo Echo.cc)
add_executable(EchoAgain EchoAgain.cc)
add_executable(cprog cprog.cc ) 

target_link_libraries(cprog ctest1 ctest2)

install(TARGETS cprog DESTINATION bin)

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