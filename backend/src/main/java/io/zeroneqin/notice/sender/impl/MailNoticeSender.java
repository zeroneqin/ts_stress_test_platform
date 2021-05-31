package io.zeroneqin.notice.sender.impl;

import io.zeroneqin.commons.utils.LogUtil;
import io.zeroneqin.notice.domain.MessageDetail;
import io.zeroneqin.notice.sender.AbstractNoticeSender;
import io.zeroneqin.notice.sender.NoticeModel;
import io.zeroneqin.notice.service.MailService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class MailNoticeSender extends AbstractNoticeSender {
    @Resource
    private MailService mailService;

    private void sendMail(MessageDetail messageDetail, String context, NoticeModel noticeModel) throws MessagingException {
        JavaMailSenderImpl javaMailSender = mailService.getMailSender();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(javaMailSender.getUsername());
        //helper.setSubject("MeterSphere " + noticeModel.getSubject());
        helper.setSubject(noticeModel.getSubject());
        List<String> emails = super.getUserEmails(messageDetail.getUserIds());
        String[] users = emails.toArray(new String[0]);
        LogUtil.info("收件人地址: " + emails);
        helper.setText(context, true);
        helper.setTo(users);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void send(MessageDetail messageDetail, NoticeModel noticeModel) {
        String context = super.getHtmlContext(messageDetail, noticeModel);
        try {
            sendMail(messageDetail, context, noticeModel);
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }
}
