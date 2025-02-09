package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constans.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.vo.CommentVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.CommentMapper;
import com.sangeng.service.UserService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sangeng.service.CommentService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-10-12 11:20:52
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论

        //对articleId进行判断
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<Comment>();
        lambdaQueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);

        //根评论的rootId为-1
        lambdaQueryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_STATUS_ROOTID_NORMAL);

        //评论类型
        lambdaQueryWrapper.eq(Comment::getType, commentType);

        //分页查询
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<CommentVo> commentList = toCommentVoList(page.getRecords());

//        commentList.stream()
//                .filter(commentVo -> commentVo.getRootId() == commentVo.getId())
//                .collect(Collectors.toList());
//
//        toCommentVoList(commentList);
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
//        comment.setCreateBy(SecurityUtils.getUserId());
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 遍历vo集合
        for (CommentVo commentVo: commentVos) {
            //通过createBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentId不为-1 才进行查询
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        //stream 方法
//        commentVos.stream()
//                .map(commentVo -> commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName()))
//                .filter(commentVo -> commentVo.getToCommentId() != -1)
//                .map(commentVo -> commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName()))
//                .collect(Collectors.toList());

        return commentVos;
    }
}
