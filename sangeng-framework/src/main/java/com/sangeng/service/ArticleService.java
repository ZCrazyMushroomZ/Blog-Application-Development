package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ArticleDto;
import com.sangeng.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(ArticleDto article);

    ResponseResult selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ResponseResult getInfo(Long id);

    ResponseResult edit(ArticleDto articleDto);

    ResponseResult deleteByIds(List<Long> ids);
}
