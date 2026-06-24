$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptDir

Write-Host "================================" -ForegroundColor Cyan
Write-Host "  XenoGames - 游戏通关记录" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan

# 检查 MySQL
$mysql = Get-Service MySQL80 -ErrorAction SilentlyContinue
if ($mysql.Status -ne "Running") {
    Write-Host "[MySQL] 尝试启动..." -ForegroundColor Yellow
    Start-Service MySQL80 -ErrorAction SilentlyContinue
}

# 启动后端
Write-Host "[启动] 后端..." -ForegroundColor Green
Start-Process cmd -ArgumentList "/c cd /d `"$scriptDir\backend`" && D:\apache-maven\bin\mvn spring-boot:run" -WindowStyle Normal

# 启动前端
Write-Host "[启动] 前端..." -ForegroundColor Green
Start-Process cmd -ArgumentList "/c cd /d `"$scriptDir\frontend`" && npm run dev" -WindowStyle Normal

# 等 10 秒
Write-Host "[等待] 服务启动中..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# 打开浏览器
Start-Process "http://localhost:5173"

Write-Host ""
Write-Host "后端: http://localhost:18080" -ForegroundColor Gray
Write-Host "前端: http://localhost:5173" -ForegroundColor Gray
Write-Host ""
Write-Host "按任意键退出（不影响服务运行）..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
