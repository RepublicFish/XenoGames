package com.xenogames.controller;

import com.xenogames.common.Result;
import com.xenogames.dto.*;
import com.xenogames.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    /**
     * 获取游戏列表（分页 + 搜索 + 筛选）
     */
    @GetMapping
    public Result<Page<GameVO>> getGames(@Valid GameQueryDTO query) {
        return Result.success(gameService.getGames(query));
    }

    /**
     * 获取游戏详情
     */
    @GetMapping("/{id}")
    public Result<GameVO> getGame(@PathVariable Long id) {
        return Result.success(gameService.getGameById(id));
    }

    /**
     * 新增游戏
     */
    @PostMapping
    public Result<GameVO> createGame(@Valid @RequestBody GameCreateDTO dto) {
        return Result.success(gameService.createGame(dto));
    }

    /**
     * 更新游戏
     */
    @PutMapping("/{id}")
    public Result<GameVO> updateGame(@PathVariable Long id, @Valid @RequestBody GameUpdateDTO dto) {
        return Result.success(gameService.updateGame(id, dto));
    }

    /**
     * 删除游戏
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return Result.success();
    }
}
