package com.deadline.knunotice.notice;

import com.deadline.knunotice.major.MajorResponseDTO;
import com.deadline.knunotice.member.Member;
import com.deadline.knunotice.member.MemberAuthentication;
import com.deadline.knunotice.member.MemberToNotice;
import com.deadline.knunotice.member.MemberToNoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final MemberToNoticeRepository mtnRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository, MemberToNoticeRepository mtnRepository) {
        this.noticeRepository = noticeRepository;
        this.mtnRepository = mtnRepository;
    }

    @Override
    public Page<NoticeAndETC> findAll(Pageable pageable) {
        // TODO 생성일 기준 정렬 필요
        return noticeRepository.findNoticeDto(null, pageable);
    }

    @Override
    public Page<NoticeAndETC> searchAll(String keyword, Pageable pageable) {
        return noticeRepository.findNoticeDtoForKeyword(null, keyword, pageable);
    }

    @Override
    public Page<NoticeAndETC> findAllVersionSQL(Long id, String keyword, Pageable pageable) {

        Page<NoticeAndETC> notices;
        if(keyword == null) {
            notices = noticeRepository.findNoticeDto(id, pageable);
        } else {
            notices = noticeRepository.findNoticeDtoForKeyword(id, keyword, pageable);
        }

        return notices;
    }

    @Override
    public Page<NoticeAndETC> findAllVersionSQLAndMajor(Long id, String keyword, ArrayList<MajorResponseDTO> majors, Pageable pageable) {
        Page<NoticeAndETC> notices;
        if(keyword == null) {
            notices = noticeRepository.findNoticeDto(id, pageable);
        } else {
            notices = noticeRepository.findNoticeDtoForKeyword(id, keyword, pageable);
        }
        return notices;
    }

    @Override
    public Page<NoticeAndETC> findAllForMajors(ArrayList<MajorResponseDTO> majors, Pageable pageable) {
        // 문자열 정리
        List<String> m = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        for(MajorResponseDTO major: majors) {
            query.append("'").append(major.getId()).append("'").append(",");
            m.add(major.getId().toString());
        }
        System.out.println(m);
        query = new StringBuilder(query.substring(0, query.length() - 1));
        System.out.println(query);

        return noticeRepository.findAllByMajor(m, pageable);
    }

    @Override
    public PageNoticeResponse findBookmarkAll(MemberAuthentication memberAuthentication, Pageable pageable) {
        Page<MemberToNotice> pages = mtnRepository.findMemberToNoticeByMemberAndIsBookmark(memberAuthentication.getMember(), true, pageable);

        List<NoticeResponseDTO> notices = new ArrayList<>();

        List<MemberToNotice> mtns = pages.getContent();

        for(MemberToNotice mtn: mtns) {

            int isPin = 0;
            if(mtn.isPin()) {
                isPin = 1;
            }

            int isBookmark = 0;
            if(mtn.isBookmark()) {
                isBookmark = 1;
            }

            notices.add(new NoticeResponseDTO(
                    mtn.getNotice().getId(),
                    mtn.getNotice().getTitle(),
                    mtn.getNotice().getUrl(),
                    mtn.getNotice().getCreatedDate(),
                    isPin,
                    isBookmark,
                    mtn.getNotice().getCreatedAt(),
                    mtn.getNotice().getUpdatedAt(),
                    mtn.getNotice().getMajor().getName()
            ));

        }

        PageNoticeResponse pageNoticeResponse = new PageNoticeResponse();
        pageNoticeResponse.setContent(notices);
        pageNoticeResponse.setNumber(pages.getNumber());
        pageNoticeResponse.setTotalPages(pages.getTotalPages());

        return pageNoticeResponse;
    }

    @Override
    public Integer recordPinOrBookmark(Member member, Long noticeId, Integer type) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow();

        MemberToNotice mtn = mtnRepository.findMemberToNoticeByMemberAndNotice(member, notice).orElse(null);

        if(mtn == null) {
            mtn = MemberToNotice.builder()
                    .member(member)
                    .notice(notice)
                    .isPin(false)
                    .isBookmark(false)
                    .build();
        }

        if(type == 0) {
            mtn.setPin(!mtn.isPin());
        } else {
            mtn.setBookmark(!mtn.isBookmark());
        }

        mtnRepository.save(mtn);
        if(type == 0) {
            if(mtn.isPin()){
                return 1;
            } else {
                return 0;
            }
        } else {
            if(mtn.isBookmark()){
                return 1;
            } else {
                return 0;
            }
        }

    }

}
