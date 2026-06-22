package com.gamevault.dto;

import com.gamevault.entity.GameStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GameCreateDTO {

    @NotBlank(message = "游戏名称不能为空")
    @Size(max = 255, message = "游戏名称最多255个字符")
    private String gameTitle;

    @NotBlank(message = "平台不能为空")
    private String platform;

    @NotNull(message = "游玩状态不能为空")
    private GameStatus status;

    private String gameplayExperience;

    private String storyExperience;

    @Min(value = 1, message = "评分最低为1")
    @Max(value = 10, message = "评分最高为10")
    private Integer rating;

    private String coverImageUrl;

    private List<Long> tagIds;

    private LocalDate startDate;

    private LocalDate completionDate;
}
