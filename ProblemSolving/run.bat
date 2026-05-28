@echo off
:: Upotreba: run.bat [naziv_foldera]
:: Primer:   run.bat 01_FirstUniqueNumber

if "%1"=="" (
    echo Upotreba: run.bat [naziv_foldera]
    echo Primer:   run.bat 01_FirstUniqueNumber
    echo.
    echo Dostupni zadaci:
    for /d %%i in (*) do (
        if not "%%i"=="00_PreStart" echo   %%i
    )
    exit /b
)

set FOLDER=%1
set BASE=%~dp0

if not exist "%BASE%%FOLDER%" (
    echo Greska: folder "%FOLDER%" ne postoji
    exit /b 1
)

if not exist "%BASE%%FOLDER%\Main.java" (
    echo Greska: Main.java ne postoji u "%FOLDER%"
    echo Napisi Main.java sa test casovima pa pokusaj ponovo.
    exit /b 1
)

echo ============================================
echo Kompajliram: %FOLDER%
echo ============================================
javac "%BASE%%FOLDER%\*.java"
if errorlevel 1 (
    echo Kompajliranje neuspesno.
    exit /b 1
)

echo.
echo ============================================
echo Pokrecem: %FOLDER%
echo ============================================
cd "%BASE%%FOLDER%"
java Main
cd "%BASE%"
