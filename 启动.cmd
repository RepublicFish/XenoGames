@echo off

:: 检查 Java
java -version >nul 2>&1
if errorlevel 1 (
    echo [错误] 未找到 Java
    pause
    exit /b 1
)

:: 检查 MySQL
sc query MySQL80 2>nul | find "RUNNING" >nul
if errorlevel 1 (
    net start MySQL80 >nul 2>&1
)

:: 切换到脚本所在目录
cd /d "%~dp0"

:: 用 java（不用 javaw）以显示控制台和错误
start "XenoGames服务器" java -jar "%~dp0XenoGames.jar"

:: 等待启动
echo 正在启动，请稍候...
timeout /t 6 /nobreak >nul

:: 打开浏览器
start http://localhost:8080

echo 已启动！关闭此窗口不影响应用运行。
pause
