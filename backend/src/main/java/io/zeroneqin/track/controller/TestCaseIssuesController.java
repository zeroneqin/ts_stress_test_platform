package io.zeroneqin.track.controller;

import io.zeroneqin.base.domain.Issues;
import io.zeroneqin.track.issue.PlatformUser;
import io.zeroneqin.track.issue.ZentaoBuild;
import io.zeroneqin.track.service.IssuesService;
import io.zeroneqin.track.request.testcase.IssuesRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("issues")
@RestController
public class TestCaseIssuesController {

    @Resource
    private IssuesService issuesService;

    @PostMapping("/add")
    public void addIssues(@RequestBody IssuesRequest issuesRequest) {
        issuesService.addIssues(issuesRequest);
    }

    @GetMapping("/get/{id}")
    public List<Issues> getIssues(@PathVariable String id) {
        return issuesService.getIssues(id);
    }

    @GetMapping("/auth/{platform}")
    public void testAuth(@PathVariable String platform) {
        issuesService.testAuth(platform);
    }

    @GetMapping("/close/{id}")
    public void closeLocalIssue(@PathVariable String id) {
        issuesService.closeLocalIssue(id);
    }

    @GetMapping("/delete/{id}")
    public void deleteIssue(@PathVariable String id) {
        issuesService.deleteIssue(id);
    }

    @GetMapping("/tapd/user/{caseId}")
    public List<PlatformUser> getTapdUsers(@PathVariable String caseId) {
        return issuesService.getTapdProjectUsers(caseId);
    }

    @GetMapping("/zentao/user/{caseId}")
    public List<PlatformUser> getZentaoUsers(@PathVariable String caseId) {
        return issuesService.getZentaoUsers(caseId);
    }

    @GetMapping("/zentao/builds/{caseId}")
    public List<ZentaoBuild> getZentaoBuilds(@PathVariable String caseId) {
        return issuesService.getZentaoBuilds(caseId);
    }


}
