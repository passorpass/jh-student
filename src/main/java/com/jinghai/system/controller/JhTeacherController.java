package com.jinghai.system.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinghai.common.util.Result;
import com.jinghai.system.domain.entity.JhTeacher;
import com.jinghai.system.service.JhTeacherService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/teach")
public class JhTeacherController {

    @Resource
    private JhTeacherService teacherService;


    @RequiresRoles("admin")
    @GetMapping("/page")
    public Result getTeachList(@RequestParam(value = "page") int page
            , @RequestParam(value = "pageSize") int pageSize
            , @RequestParam(value = "keyName", required = false) String keyname
            , @RequestParam(value = "btime", required = false) String btime
            , @RequestParam(value = "etime", required = false) String etime
            , @RequestParam(value = "status", required = false) Integer status){
        Page<JhTeacher> teacherPage = teacherService.page(new Page<>(page, pageSize),
                new LambdaQueryWrapper<JhTeacher>().like(!ObjectUtil.isEmpty(keyname),
                        JhTeacher::getTeacherName, keyname).or().like(!ObjectUtil.isEmpty(keyname),
                        JhTeacher::getDoWorkSchool, keyname).or().like(!ObjectUtil.isEmpty(keyname),
                        JhTeacher::getTeacherIdcar, keyname).eq(!ObjectUtil.isEmpty(status),
                        JhTeacher::getTeacherStatus, status).between((!ObjectUtil.isEmpty(btime) && !ObjectUtil.isEmpty(etime)), JhTeacher::getEntrySchoolTime, btime, etime));
        return Result.ok(teacherPage);
    }
}
