package com.sysu.guli.service.edu.service;

import com.sysu.guli.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
public interface ChapterService extends IService<Chapter> {

    List<Chapter> nestedListByCourseId(String courseId);

    void removeChapterAndVideo(String chapterId);
}
