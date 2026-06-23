@echo off
chcp 65001 >nul
title XenoGames

echo ================================
echo   XenoGames - 游戏通关记录
echo ================================
echo.

:: 检查 Java
java -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未找到 Java，请先安装 JDK 17+
    pause
    exit /b 1
)

:: 检查 MySQL 是否运行
sc query MySQL80 2>nul | find "RUNNING" >nul
if errorlevel 1 (
    echo [提示] MySQL 未运行，尝试启动...
    net start MySQL80 >nul 2>&1
    if errorlevel 1 (
        echo [错误] 无法启动 MySQL，请手动启动后重试
        pause
        exit /b 1
    )
    echo [OK] MySQL 已启动
)

echo [启动] XenoGames 正在启动...

:: 后台启动 Java
start "" javaw -jar "%~dp0XenoGames.jar"

:: 等待后端就绪
echo [等待] 服务启动中...
:waitloop
timeout /t 2 /nobreak >nul
curl -s http://localhost:8080/api/games >nul 2>&1
if errorlevel 1 goto waitloop

:: 以 App 模式打开（无浏览器边框，像原生桌面应用）
start "" msedge --app=http://localhost:8080 --new-window --window-size=1280,800

echo [完成] XenoGames 已启动！
exit /b 0
