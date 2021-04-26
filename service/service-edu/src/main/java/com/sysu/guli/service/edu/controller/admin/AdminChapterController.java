package com.sysu.guli.service.edu.controller.admin;


import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Chapter;
import com.sysu.guli.service.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/admin/edu/chapter")

public class AdminChapterController {

    @Autowired
    ChapterService chapterService;

    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(@PathVariable String courseId){
        List<Chapter> chapters = chapterService.nestedListByCourseId(courseId);

        return R.ok().data("items", chapters);
    }
    @PostMapping("save")
    public R save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok().setMessage("新增章节成功");
    }

    @DeleteMapping("delete/{id}")
    public R remove( @PathVariable String id) {
        chapterService.removeChapterAndVideo(id);
        return R.ok().setMessage("删除章节成功");
    }

    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("item", chapter);
    }

    @PutMapping("update")
    public R update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok().setMessage("更新章节成功");
    }
}

