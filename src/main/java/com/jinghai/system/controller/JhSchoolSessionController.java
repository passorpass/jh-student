package com.jinghai.system.controller;


import com.jinghai.common.util.Result;
import com.jinghai.system.domain.entity.JhSchoolSession;
import com.jinghai.system.service.JhSchoolSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ss")
public class JhSchoolSessionController {

    @Resource
    private JhSchoolSessionService schoolSessionService;


    @GetMapping("/gss")
    public Result getListData(){
        List<JhSchoolSession> jhSchoolSessions = schoolSessionService.list();
        return Result.ok(jhSchoolSessions);
    }
}
