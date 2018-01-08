cmake_minimum_required(VERSION 3.8)
project(network-test)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES Server.cpp Client.cpp Client.h Server.h)
add_executable(untitled ${SOURCE_FILES})