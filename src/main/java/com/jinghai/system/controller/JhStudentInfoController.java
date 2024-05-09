package com.jinghai.system.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinghai.common.util.Result;
import com.jinghai.system.domain.entity.JhStudentInfo;
import com.jinghai.system.service.JhStudentInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/stu")
@RestController
public class JhStudentInfoController {


    @Resource
    private JhStudentInfoService studentInfoService;

    @GetMapping("/page")
    @RequiresPermissions("user:list")
    public Result getStudentInfopage(@RequestParam(value = "page") int page
            , @RequestParam(value = "pageSize") int pageSize
            , @RequestParam(value = "keyName", required = false) String keyname
            , @RequestParam(value = "beginSchoolDate", required = false) String begin
            , @RequestParam(value = "endSchoolDate", required = false) String end
            , @RequestParam(value = "socialPresence", required = false) String social)
        {

            Page<JhStudentInfo> infoPage = studentInfoService.page(new Page<>(page, pageSize), new LambdaQueryWrapper<JhStudentInfo>()
                    .between((!ObjectUtil.isEmpty(begin) && !ObjectUtil.isEmpty(end)),
                            JhStudentInfo::getStudentBeginSchool, begin, end)
                    .eq(!ObjectUtil.isEmpty(social), JhStudentInfo::getStudentSocialPresence, social)
                    .like(!ObjectUtil.isEmpty(keyname), JhStudentInfo::getStudentName, keyname)
                    .or()
                    .like(!ObjectUtil.isEmpty(keyname), JhStudentInfo::getStudentHasSchool, keyname));

            return Result.ok(infoPage);
    }

}
