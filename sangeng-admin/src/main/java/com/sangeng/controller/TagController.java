package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @DeleteMapping("{ids}")
    public ResponseResult deleteTag(@PathVariable List<Long> ids) {
        return tagService.deleteTagByIds(ids);
    }

    @GetMapping("{id}")
    public ResponseResult getTag(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult ReviseTag(@RequestBody Tag tag) {
        return tagService.reviseTag(tag);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }
}
