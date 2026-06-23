@echo off
title XenoGames
cd /d "%~dp0"

echo ==============================
echo   XenoGames - 游戏通关记录
echo ==============================

:: 检查 JAR
if not exist "XenoGames.jar" (
    echo [错误] 找不到 XenoGames.jar
    pause && exit /b 1
)

:: 检查 MySQL
sc query MySQL80 2>nul | find "RUNNING" >nul
if errorlevel 1 (
    echo [MySQL] 尝试启动...
    net start MySQL80 >nul 2>&1
)

echo [启动] 请稍候，服务器启动后会自动打开浏览器...
echo ==============================
echo.
java -jar XenoGames.jar
pause
