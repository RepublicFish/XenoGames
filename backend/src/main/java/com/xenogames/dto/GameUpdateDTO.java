package com.xenogames.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GameUpdateDTO extends GameCreateDTO {
    // 继承 GameCreateDTO 全部字段和校验注解
    // 独立成类是为了未来可能新增 "更新专属" 字段（如 updateReason 等）
}
