@echo off

REM List of device IDs
set device_ids=33f0d541-9fec-4b3b-8ab0-2e48d2353016

REM Loop through each device ID and run the Java application
for %%i in (%device_ids%) do (
    echo Simulating device ID: %%i
    start cmd /k java -jar target/csv-reader-1.0.jar %%i > output_%%i.log 2>&1
)