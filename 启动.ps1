# XenoGames 启动脚本 (PowerShell)
Write-Host "Starting XenoGames..." -ForegroundColor Cyan

# 启动后端（后台运行）
$jarPath = Join-Path $PSScriptRoot "XenoGames.jar"
$process = Start-Process java -ArgumentList "-jar", "`"$jarPath`"" -WindowStyle Hidden -PassThru

# 等待就绪
Write-Host "Waiting for server..." -ForegroundColor Yellow
do {
    Start-Sleep -Seconds 1
    try {
        $null = Invoke-WebRequest -Uri "http://localhost:8080/api/games" -TimeoutSec 1 -ErrorAction SilentlyContinue
        $ready = $true
    } catch {
        $ready = $false
    }
} while (-not $ready)

Write-Host "Server ready!" -ForegroundColor Green

# App 模式打开
Start-Process "msedge" -ArgumentList "--app=http://localhost:8080", "--new-window", "--window-size=1280,800"

Write-Host "XenoGames 已启动！关闭此窗口不会停止应用。" -ForegroundColor Green
Write-Host "要停止应用，请运行: Stop-Process -Name java" -ForegroundColor Gray
