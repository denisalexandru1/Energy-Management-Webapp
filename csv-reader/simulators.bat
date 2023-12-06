@echo off

REM List of device IDs
set device_ids=da7ea9c9-3e8f-4d0a-a662-1470c3d8532b

REM Loop through each device ID and run the Java application
for %%i in (%device_ids%) do (
    echo Simulating device ID: %%i
    start cmd /k java -jar target/csv-reader-1.0.jar %%i > output_%%i.log 2>&1
)