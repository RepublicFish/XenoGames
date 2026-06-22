package com.xenogames.service;

import com.xenogames.common.exception.BusinessException;
import com.xenogames.dto.*;
import com.xenogames.entity.Game;
import com.xenogames.entity.GameStatus;
import com.xenogames.entity.Tag;
import com.xenogames.repository.GameRepository;
import com.xenogames.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final TagRepository tagRepository;

    /**
     * 分页查询游戏列表（支持搜索+筛选+排序）
     */
    public Page<GameVO> getGames(GameQueryDTO query) {
        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(query.getDirection())
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                query.getSort() != null ? query.getSort() : "updatedAt"
        );
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize(), sort);

        GameStatus status = null;
        if (query.getStatus() != null) {
            status = query.getStatus();
        }

        Page<Game> gamePage = gameRepository.findWithFilters(
                query.getKeyword(),
                query.getPlatform(),
                status,
                query.getTagId(),
                pageable
        );

        return gamePage.map(this::toGameVO);
    }

    /**
     * 获取游戏详情（含标签、截图）
     */
    public GameVO getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "游戏不存在"));
        return toGameVO(game);
    }

    /**
     * 新增游戏
     */
    @Transactional
    public GameVO createGame(GameCreateDTO dto) {
        Game game = new Game();
        applyDtoToGame(dto, game);
        game.setTags(resolveTags(dto.getTagIds()));

        game = gameRepository.save(game);
        return toGameVO(game);
    }

    /**
     * 更新游戏
     */
    @Transactional
    public GameVO updateGame(Long id, GameUpdateDTO dto) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "游戏不存在"));

        applyDtoToGame(dto, game);
        game.setTags(resolveTags(dto.getTagIds()));

        game = gameRepository.save(game);
        return toGameVO(game);
    }

    /**
     * 删除游戏
     */
    @Transactional
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new BusinessException(404, "游戏不存在");
        }
        gameRepository.deleteById(id);
    }

    // ===== 私有方法 =====

    /**
     * DTO → Entity 字段映射（用于新增和更新复用）
     */
    private void applyDtoToGame(GameCreateDTO dto, Game game) {
        game.setGameTitle(dto.getGameTitle());
        game.setPlatform(dto.getPlatform());
        game.setStatus(dto.getStatus());
        game.setGameplayExperience(dto.getGameplayExperience());
        game.setStoryExperience(dto.getStoryExperience());
        game.setRating(dto.getRating());
        game.setCoverImageUrl(dto.getCoverImageUrl());
        game.setStartDate(dto.getStartDate());
        game.setCompletionDate(dto.getCompletionDate());
    }

    /**
     * 通过标签 ID 列表查找 Tag 实体列表
     */
    private List<Tag> resolveTags(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        return tagRepository.findAllById(tagIds);
    }

    /**
     * Entity → VO
     */
    private GameVO toGameVO(Game game) {
        GameVO vo = new GameVO();
        vo.setId(game.getId());
        vo.setGameTitle(game.getGameTitle());
        vo.setPlatform(game.getPlatform());
        vo.setStatus(game.getStatus());
        vo.setGameplayExperience(game.getGameplayExperience());
        vo.setStoryExperience(game.getStoryExperience());
        vo.setRating(game.getRating());
        vo.setCoverImageUrl(game.getCoverImageUrl());
        vo.setStartDate(game.getStartDate());
        vo.setCompletionDate(game.getCompletionDate());
        vo.setCreatedAt(game.getCreatedAt());
        vo.setUpdatedAt(game.getUpdatedAt());

        // 转换标签
        if (game.getTags() != null) {
            vo.setTags(game.getTags().stream()
                    .map(tag -> new TagDTO(tag.getId(), tag.getName(), tag.getColor()))
                    .collect(Collectors.toList()));
        }

        // 转换截图
        if (game.getScreenshots() != null) {
            vo.setScreenshots(game.getScreenshots().stream()
                    .map(ss -> new ScreenshotVO(ss.getId(), ss.getImageUrl(), ss.getCaption(), ss.getSortOrder()))
                    .collect(Collectors.toList()));
        }

        return vo;
    }
}
