package com.sysu.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sysu.guli.service.edu.entity.Chapter;
import com.sysu.guli.service.edu.entity.Video;
import com.sysu.guli.service.edu.mapper.ChapterMapper;
import com.sysu.guli.service.edu.mapper.VideoMapper;
import com.sysu.guli.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    VideoMapper videoMapper;
    @Override
    public List<Chapter> nestedListByCourseId(String courseId) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.course_id", courseId);
        queryWrapper.orderByAsc("t1.sort").orderByAsc("t2.sort");
        List<Chapter> chapters = baseMapper.nestedListByCourseId(queryWrapper);


        return chapters;
    }

    @Override
    public void removeChapterAndVideo(String chapterId) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getChapterId ,chapterId);
        List<Video> videos = videoMapper.selectList(queryWrapper);
        if (videos != null && videos.size()>0) {
            videoMapper.delete(queryWrapper);
        }
        baseMapper.deleteById(chapterId);
    }
}
