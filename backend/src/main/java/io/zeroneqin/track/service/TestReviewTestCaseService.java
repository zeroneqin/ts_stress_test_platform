package io.zeroneqin.track.service;

import io.zeroneqin.base.domain.*;
import io.zeroneqin.base.mapper.TestCaseMapper;
import io.zeroneqin.base.mapper.TestCaseReviewMapper;
import io.zeroneqin.base.mapper.TestCaseReviewTestCaseMapper;
import io.zeroneqin.base.mapper.TestCaseReviewUsersMapper;
import io.zeroneqin.base.mapper.ext.ExtTestReviewCaseMapper;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.utils.ServiceUtils;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.member.QueryMemberRequest;
import io.zeroneqin.service.UserService;
import io.zeroneqin.track.dto.TestReviewCaseDTO;
import io.zeroneqin.track.request.testplancase.TestReviewCaseBatchRequest;
import io.zeroneqin.track.request.testreview.DeleteRelevanceRequest;
import io.zeroneqin.track.request.testreview.QueryCaseReviewRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestReviewTestCaseService {

    @Resource
    ExtTestReviewCaseMapper extTestReviewCaseMapper;
    @Resource
    UserService userService;
    @Resource
    TestCaseReviewTestCaseMapper testCaseReviewTestCaseMapper;
    @Resource
    TestCaseReviewUsersMapper testCaseReviewUsersMapper;
    @Resource
    TestCaseReviewMapper testCaseReviewMapper;
    @Resource
    TestCaseReviewService testCaseReviewService;
    @Resource
    TestCaseMapper testCaseMapper;

    public List<TestReviewCaseDTO> list(QueryCaseReviewRequest request) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        List<TestReviewCaseDTO> list = extTestReviewCaseMapper.list(request);
        QueryMemberRequest queryMemberRequest = new QueryMemberRequest();
        queryMemberRequest.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        Map<String, String> userMap = userService.getMemberList(queryMemberRequest)
                .stream().collect(Collectors.toMap(User::getId, User::getName));
        list.forEach(item -> {
            String reviewId = item.getReviewId();
            List<String> userIds = getReviewUserIds(reviewId);
            item.setReviewerName(getReviewName(userIds, userMap));
        });
        return list;
    }

    private List<String> getReviewUserIds(String reviewId) {
        TestCaseReviewUsersExample testCaseReviewUsersExample = new TestCaseReviewUsersExample();
        testCaseReviewUsersExample.createCriteria().andReviewIdEqualTo(reviewId);
        List<TestCaseReviewUsers> testCaseReviewUsers = testCaseReviewUsersMapper.selectByExample(testCaseReviewUsersExample);
        return testCaseReviewUsers.stream().map(TestCaseReviewUsers::getUserId).collect(Collectors.toList());
    }

    private String getReviewName(List<String> userIds, Map<String, String> userMap) {
        List<String> userNames = new ArrayList<>();
        if (userIds.size() > 0) {
            for (String id : userIds) {
                String n = userMap.get(id);
                if (StringUtils.isNotBlank(n)) {
                    userNames.add(n);
                }
            }
        }
        return StringUtils.join(userNames, "???");
    }

    public int deleteTestCase(DeleteRelevanceRequest request) {
        checkReviewer(request.getReviewId());
        return testCaseReviewTestCaseMapper.deleteByPrimaryKey(request.getId());
    }

    private void checkReviewer(String reviewId) {
        List<String> userIds = testCaseReviewService.getTestCaseReviewerIds(reviewId);
        String currentId = SessionUtils.getUser().getId();
        TestCaseReview caseReview = testCaseReviewMapper.selectByPrimaryKey(reviewId);
        String creator = "";
        if (caseReview != null) {
            creator = caseReview.getCreator();
        }
        if (!userIds.contains(currentId) && !StringUtils.equals(creator, currentId)) {
            MSException.throwException("??????????????????????????????????????????");
        }
    }

    public void deleteTestCaseBath(TestReviewCaseBatchRequest request) {
        checkReviewer(request.getReviewId());
        TestCaseReviewTestCaseExample example = new TestCaseReviewTestCaseExample();
        example.createCriteria().andIdIn(request.getIds());
        testCaseReviewTestCaseMapper.deleteByExample(example);
    }

    public void editTestCase(TestCaseReviewTestCase testCaseReviewTestCase) {
        String currentUserId = SessionUtils.getUser().getId();
        String reviewId = testCaseReviewTestCase.getReviewId();
        TestCaseReviewUsersExample testCaseReviewUsersExample = new TestCaseReviewUsersExample();
        testCaseReviewUsersExample.createCriteria().andReviewIdEqualTo(reviewId);
        List<TestCaseReviewUsers> testCaseReviewUsers = testCaseReviewUsersMapper.selectByExample(testCaseReviewUsersExample);
        List<String> reviewIds = testCaseReviewUsers.stream().map(TestCaseReviewUsers::getUserId).collect(Collectors.toList());
        if (!reviewIds.contains(currentUserId)) {
            MSException.throwException("??????????????????????????????");
        }

        // ????????????????????????????????????
        testCaseReviewTestCase.setStatus(testCaseReviewTestCase.getStatus());
        testCaseReviewTestCase.setReviewer(SessionUtils.getUser().getId());
        testCaseReviewTestCase.setUpdateTime(System.currentTimeMillis());
        testCaseReviewTestCaseMapper.updateByPrimaryKeySelective(testCaseReviewTestCase);

        // ????????????????????????
        String caseId = testCaseReviewTestCase.getCaseId();
        TestCaseWithBLOBs testCase = new TestCaseWithBLOBs();
        testCase.setId(caseId);
        testCase.setReviewStatus(testCaseReviewTestCase.getStatus());
        testCaseMapper.updateByPrimaryKeySelective(testCase);
    }

    public List<TestReviewCaseDTO> getTestCaseReviewDTOList(QueryCaseReviewRequest request) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        return extTestReviewCaseMapper.list(request);
    }

    public TestReviewCaseDTO get(String reviewId) {
        return extTestReviewCaseMapper.get(reviewId);
    }
}
