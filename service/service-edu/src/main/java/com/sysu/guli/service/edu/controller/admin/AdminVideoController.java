package com.sysu.guli.service.edu.controller.admin;


import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Video;
import com.sysu.guli.service.edu.mapper.VideoMapper;
import com.sysu.guli.service.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/admin/edu/video")

public class AdminVideoController {
    @Autowired
    VideoService videoService;
    @PostMapping("save")
    public R saveVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok().setMessage("新增小结成功!");
    }
    @DeleteMapping("remove/{id}")
    public R deleteVideo(@PathVariable String id){
        boolean b = videoService.removeById(id);
        if (b){
            return R.ok().setMessage("删除成功");
        }else {
            return R.error().setMessage("删除失败");
        }
    }
    @GetMapping("get/{id}")
    public R getVideo(@PathVariable String id){
        Video video = videoService.getById(id);
        return R.ok().data("item", video);
    }

    @PutMapping("update")
    public R updateVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return R.ok().setMessage("编辑成功!");
    }
}

