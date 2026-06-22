package com.gamevault.dto;

import com.gamevault.entity.GameStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameVO {

    private Long id;
    private String gameTitle;
    private String platform;
    private GameStatus status;
    private String gameplayExperience;
    private String storyExperience;
    private Integer rating;
    private String coverImageUrl;
    private List<TagDTO> tags;
    private List<ScreenshotVO> screenshots;
    private LocalDate startDate;
    private LocalDate completionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
