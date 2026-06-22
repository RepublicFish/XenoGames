package com.xenogames.service;

import com.xenogames.common.exception.BusinessException;
import com.xenogames.dto.TagDTO;
import com.xenogames.entity.Tag;
import com.xenogames.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 获取所有标签
     */
    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 新增标签
     */
    @Transactional
    public TagDTO createTag(TagDTO dto) {
        if (tagRepository.existsByName(dto.getName())) {
            throw new BusinessException(400, "标签名称已存在");
        }
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setColor(dto.getColor());
        tag = tagRepository.save(tag);
        return toDTO(tag);
    }

    /**
     * 删除标签
     */
    @Transactional
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new BusinessException(404, "标签不存在");
        }
        tagRepository.deleteById(id);
    }

    private TagDTO toDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName(), tag.getColor());
    }
}
