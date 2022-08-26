package com.deadline.knunotice.entity.Notice;

import com.deadline.knunotice.notice.Notice;
import com.deadline.knunotice.notice.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoticesRepositoryTest {

    @Autowired
    private NoticeRepository noticesRepository;

    @Test
    void 공지글_저장_불러오기(){
        //given
        String college = "IT";
        String major = "컴퓨터공학";
        Notice notices = Notice.builder().college(college).major(major).build();
        noticesRepository.save(notices);

        //when
        List<Notice> noticesList = noticesRepository.findAll();

        //then
        Notice notice = noticesList.get(0);
        assertThat(notice.getCollege()).isEqualTo(college);
        assertThat(notice.getMajor()).isEqualTo(major);
    }
}
