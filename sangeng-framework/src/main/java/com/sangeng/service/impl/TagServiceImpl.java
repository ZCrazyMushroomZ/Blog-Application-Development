package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.TagMapper;
import com.sangeng.service.TagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-01-13 07:40:34
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagService tagService;

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setPages(pageSize);
        page(page, queryWrapper);

        //封装返回数据
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        //tag：name 不能为空
        if (!StringUtils.hasText(tag.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTagByIds(List<Long> ids) {
        ids.stream().forEach(id -> tagService.removeById(id));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getId,id);
        List<Tag> tags = list(queryWrapper);
        TagVo tagVo = new TagVo();
        for(Tag tag : tags) {
            tagVo.setId(tag.getId());
            tagVo.setName(tag.getName());
//            tagVo.setRemark(tag.getRemark());
        }
        //TODO why the data is null?
//        TagVo tagVo = BeanCopyUtils.copyBean(tags, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult reviseTag(Tag tag) {
        //根据传过来的tag 找到id
        updateById(tag);
        //修改tag 中的 name 和 remark
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
//        List<Tag> tags = getBaseMapper().selectList(null);
//        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
//        List<TagVo> tagVos1 = distinctByName(tagVos);
//        return ResponseResult.okResult(tagVos1);

        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }


    private List<TagVo> distinctByName(List<TagVo> tagVos) {
        Set<String> set = new HashSet<>();
        List<TagVo> distinctTagVos = new ArrayList<>();

        for (TagVo tagVo : tagVos) {
            if (set.add(tagVo.getName())) {
                // If the name is not in the set, it's unique, so add it to the result list
                distinctTagVos.add(tagVo);
            }
        }
        return distinctTagVos;
    }

}
