package io.zeroneqin.notice.service;

import com.alibaba.nacos.client.utils.StringUtils;
import io.zeroneqin.commons.constants.NoticeConstants;
import io.zeroneqin.notice.domain.MessageDetail;
import io.zeroneqin.notice.sender.NoticeModel;
import io.zeroneqin.notice.sender.NoticeSender;
import io.zeroneqin.notice.sender.impl.DingNoticeSender;
import io.zeroneqin.notice.sender.impl.MailNoticeSender;
import io.zeroneqin.notice.sender.impl.WeComNoticeSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class NoticeSendService {
    @Resource
    private MailNoticeSender mailNoticeSender;
    @Resource
    private WeComNoticeSender weComNoticeSender;
    @Resource
    private DingNoticeSender dingNoticeSender;
    @Resource
    private NoticeService noticeService;

    private NoticeSender getNoticeSender(MessageDetail messageDetail) {
        NoticeSender noticeSender = null;
        switch (messageDetail.getType()) {
            case NoticeConstants.Type.EMAIL:
                noticeSender = mailNoticeSender;
                break;
            case NoticeConstants.Type.WECHAT_ROBOT:
                noticeSender = weComNoticeSender;
                break;
            case NoticeConstants.Type.NAIL_ROBOT:
                noticeSender = dingNoticeSender;
                break;
            default:
                break;
        }

        return noticeSender;
    }

    public void send(String taskType, NoticeModel noticeModel) {
        List<MessageDetail> messageDetails;
        switch (taskType) {
            case NoticeConstants.Mode.API:
                messageDetails = noticeService.searchMessageByType(NoticeConstants.TaskType.JENKINS_TASK);
                break;
            case NoticeConstants.Mode.SCHEDULE:
                messageDetails = noticeService.searchMessageByTestId(noticeModel.getTestId());
                break;
            default:
                messageDetails = noticeService.searchMessageByType(taskType);
                break;
        }
        messageDetails.forEach(messageDetail -> {
            if (StringUtils.equals(messageDetail.getEvent(), noticeModel.getEvent())) {
                this.getNoticeSender(messageDetail).send(messageDetail, noticeModel);
            }
        });
    }
}
