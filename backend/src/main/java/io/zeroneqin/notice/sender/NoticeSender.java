package io.zeroneqin.notice.sender;

import io.zeroneqin.notice.domain.MessageDetail;
import org.springframework.scheduling.annotation.Async;

public interface NoticeSender {
    @Async
    void send(MessageDetail messageDetail, NoticeModel noticeModel);
}
