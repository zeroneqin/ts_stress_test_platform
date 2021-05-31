package io.zeroneqin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.Schedule;
import io.zeroneqin.controller.request.QueryScheduleRequest;
import io.zeroneqin.dto.ScheduleDao;
import io.zeroneqin.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("schedule")
@RestController
public class ScheduleController {
    @Resource
    private ScheduleService scheduleService;

    @PostMapping("/list/{goPage}/{pageSize}")
    public List<ScheduleDao> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody QueryScheduleRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return scheduleService.list(request);
    }

    @GetMapping("/findOne/{testId}/{group}")
    public Schedule schedule(@PathVariable String testId,@PathVariable String group) {
        Schedule schedule = scheduleService.getScheduleByResource(testId,group);
        return schedule;
    }
}
