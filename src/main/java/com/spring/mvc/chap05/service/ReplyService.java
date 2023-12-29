package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.common.PageMaker;
import com.spring.mvc.chap05.dto.request.ReplyModifyRequestDTO;
import com.spring.mvc.chap05.dto.request.ReplyPostRequestDTO;
import com.spring.mvc.chap05.dto.response.ReplyDetailResponseDTO;
import com.spring.mvc.chap05.dto.response.ReplyListResponseDTO;
import com.spring.mvc.chap05.entity.Reply;
import com.spring.mvc.chap05.repository.ReplyMapper;
import com.spring.mvc.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.mvc.util.LoginUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyMapper replyMapper;


    // 댓글 목록 조회
    public ReplyListResponseDTO getList(long boardNo, Page page) {

        // DB에서 댓글 정보 조회
        List<ReplyDetailResponseDTO> replies = replyMapper.findAll(boardNo, page)
                .stream()
                .map(ReplyDetailResponseDTO::new)
                .collect(Collectors.toList());

        // DB에서 총 댓글 수 조회
        int count = replyMapper.count(boardNo);

        return ReplyListResponseDTO.builder()
                .replies(replies)
                .count(count)
                .pageInfo(new PageMaker(page, count))
                .build();
    }

    // 댓글 등록 서비스
    public ReplyListResponseDTO register(ReplyPostRequestDTO dto, HttpSession session) throws SQLException {
        log.debug("register services execute!!!");

        // dto를 entity로 변환
        Reply reply = dto.toEntity();
        reply.setAccount(getCurrentLoginMemberAccount(session));
        boolean flag = replyMapper.save(reply);

        if (!flag) {
            log.warn("reply register failed!!");
            throw new SQLException("댓글 저장 실패!!!");
        }

        // 등록이 성공하면 새롭게 갱신된 1페이지 댓글내용을 재 조회해서 응답한다
        return getList(dto.getBno(), new Page(1, 5));
    }


    // 댓글 삭제
    @Transactional   // 트랜잭션 처리
    public ReplyListResponseDTO delete(long replyNo) throws Exception {

        Reply reply = replyMapper.findOne(replyNo);
        long boardNo = reply.getBoardNo();

        replyMapper.delete(replyNo);

        return getList(boardNo, new Page(1, 5));

    }

    // 댓글 수정 처리
    @Transactional
    public ReplyListResponseDTO modify(ReplyModifyRequestDTO dto) throws Exception {

        replyMapper.modify(dto.toEntity());

        return getList(dto.getBno(), new Page(1, 5));
    }


}
