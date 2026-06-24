@echo off
title XenoGames 启动器
cd /d "%~dp0"

echo ================================
echo   XenoGames - 游戏通关记录
echo ================================
echo.

:: 检查 MySQL（不要 admin 也能检测）
sc query MySQL80 2>nul | find "RUNNING" >nul
if errorlevel 1 (
    echo [MySQL] 未运行，尝试启动（可能需要管理员权限）...
    net start MySQL80 2>nul
    if errorlevel 1 (
        echo [警告] MySQL 启动失败，请以管理员身份运行此脚本
        echo         或以管理员身份手动执行: net start MySQL80
    )
)

:: 启动后端（使用绝对 Maven 路径）
echo [启动] 后端服务...
start "XenoGames-后端" cmd /c "cd /d %~dp0backend && D:\apache-maven\bin\mvn spring-boot:run"

:: 启动前端
echo [启动] 前端服务...
start "XenoGames-前端" cmd /c "cd /d %~dp0frontend && npm run dev"

:: 等待启动
echo [等待] 服务启动中，请稍候（约10秒）...
timeout /t 10 /nobreak >nul

:: 打开浏览器
start http://localhost:5173

echo.
echo ================================
echo   后端: http://localhost:18080
echo   前端: http://localhost:5173
echo ================================
echo.
echo 请勿关闭弹出的两个命令行窗口！
echo.
pause
