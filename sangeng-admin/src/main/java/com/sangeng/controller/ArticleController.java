package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ArticleDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    public ResponseResult add(@RequestBody ArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize) {
        return articleService.selectArticlePage(article, pageNum, pageSize);
    }

    @GetMapping("{id}")
    public ResponseResult getInfo(@PathVariable Long id) {
        return articleService.getInfo(id);
    }

    @PutMapping
    //②然后才是修改文章
    public ResponseResult edit(@RequestBody ArticleDto article){
        return articleService.edit(article);
    }

    @DeleteMapping("/{ids}")
    public ResponseResult delete(@PathVariable List<Long> ids){
        //直接使用mybatisplus提供的removeById方法
        return articleService.deleteByIds(ids);
    }


}
