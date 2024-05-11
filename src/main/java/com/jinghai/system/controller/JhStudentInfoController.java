package com.jinghai.system.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinghai.common.util.Result;
import com.jinghai.system.domain.entity.JhStudentInfo;
import com.jinghai.system.service.JhStudentInfoService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/stu")
@RestController
public class JhStudentInfoController {


    //基本学生信息表
    @Resource
    private JhStudentInfoService studentInfoService;


    /**
     * 分页查询和搜索学生信息
     * @param page
     * @param pageSize
     * @param keyname
     * @param begin
     * @param end
     * @param social
     * @return
     */
    @GetMapping("/page")
    @RequiresRoles(value = "admin",logical = Logical.OR)
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


    /**
     * 根据id回写数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RequiresRoles("admin")
    public Result getById(@PathVariable("id")Long id){
        /**
         * 还要关联其他表现阶段只查基本信息
         */
        JhStudentInfo jhStudentInfo = studentInfoService.getById(id);
        if(ObjectUtil.isEmpty(jhStudentInfo)){
            return Result.fail("查询失败，暂无此学生");
        }
        return Result.ok(jhStudentInfo);
    }

}
