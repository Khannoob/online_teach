package com.sysu.guli.service.edu.service.impl;

import com.sysu.guli.service.edu.entity.Comment;
import com.sysu.guli.service.edu.mapper.CommentMapper;
import com.sysu.guli.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
