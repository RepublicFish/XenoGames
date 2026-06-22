package com.xenogames.dto;

import com.xenogames.entity.GameStatus;
import lombok.Data;

@Data
public class GameQueryDTO {

    private String keyword;
    private String platform;
    private GameStatus status;
    private Long tagId;
    private Integer page = 1;
    private Integer size = 20;
    private String sort = "updatedAt";
    private String direction = "DESC";
}
