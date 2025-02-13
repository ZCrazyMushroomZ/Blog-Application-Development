package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2024-01-13 07:40:32
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTagByIds(List<Long> ids);

    ResponseResult getTagById(Long id);

    ResponseResult reviseTag(Tag tag);

    ResponseResult listAllTag();
}
