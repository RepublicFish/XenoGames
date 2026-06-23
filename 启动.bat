@echo off
title XenoGames 启动器
cd /d "%~dp0"

echo ================================
echo   XenoGames - 游戏通关记录
echo ================================
echo.

:: 检查 Java
java -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未找到 Java
    pause & exit /b 1
)

:: 检查 MySQL
sc query MySQL80 2>nul | find "RUNNING" >nul
if errorlevel 1 (
    net start MySQL80 >nul 2>&1
)

:: 启动后端（新窗口）
start "XenoGames-后端:18080" cmd /k "cd /d %~dp0backend && mvn spring-boot:run"

:: 启动前端（新窗口）
start "XenoGames-前端:5173" cmd /k "cd /d %~dp0frontend && npm run dev"

:: 等待启动
echo 正在启动，请稍候...
timeout /t 8 /nobreak >nul

:: 打开浏览器
start http://localhost:5173

echo.
echo 已启动！继续开发中，请勿关闭弹出的两个窗口。
echo 关闭此窗口不影响应用运行。
pause
