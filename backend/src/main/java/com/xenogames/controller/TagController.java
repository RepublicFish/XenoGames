package com.xenogames.controller;

import com.xenogames.common.Result;
import com.xenogames.dto.TagDTO;
import com.xenogames.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 获取所有标签
     */
    @GetMapping
    public Result<List<TagDTO>> getTags() {
        return Result.success(tagService.getAllTags());
    }

    /**
     * 新增标签
     */
    @PostMapping
    public Result<TagDTO> createTag(@RequestBody TagDTO dto) {
        return Result.success(tagService.createTag(dto));
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return Result.success();
    }
}
