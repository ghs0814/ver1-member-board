package com.example.memberboard.member.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.memberboard.common.exception.ConflictException;
import com.example.memberboard.member.domain.Member;
import com.example.memberboard.member.dto.MemberCreateRequest;
import com.example.memberboard.member.dto.MemberResponse;
import com.example.memberboard.member.mapper.MemberMapper;
import com.example.memberboard.member.dto.MemberUpdateRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {

        if (memberMapper.countByEmail(request.getEmail()) > 0) {
            throw new ConflictException(
                    "이미 사용 중인 이메일입니다."
            );
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());

        int insertedRows = memberMapper.insert(member);

        if (insertedRows != 1) {
            throw new IllegalStateException(
                    "회원 등록에 실패했습니다."
            );
        }

        Member savedMember =
                memberMapper.findById(member.getId());

        if (savedMember == null) {
            throw new IllegalStateException(
                    "등록된 회원을 조회할 수 없습니다."
            );
        }

        return MemberResponse.from(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {

        Member member = memberMapper.findById(id);

        if (member == null) {
            throw new NoSuchElementException(
                    "회원을 찾을 수 없습니다. id=" + id
            );
        }

        return MemberResponse.from(member);
    }
    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {

        List<Member> members = memberMapper.findAll();

        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }
    @Transactional
    public MemberResponse updateMember(
            Long id,
            MemberUpdateRequest request
    ) {

        Member member = memberMapper.findById(id);

        if (member == null) {
            throw new NoSuchElementException(
                    "회원을 찾을 수 없습니다. id=" + id
            );
        }

        if (memberMapper.countByEmailExcludingId(
                request.getEmail(),
                id
        ) > 0) {
            throw new ConflictException(
                    "이미 사용 중인 이메일입니다."
            );
        }

        member.setName(request.getName());
        member.setEmail(request.getEmail());

        int updatedRows = memberMapper.update(member);

        if (updatedRows != 1) {
            throw new IllegalStateException(
                    "회원 수정에 실패했습니다."
            );
        }

        Member updatedMember =
                memberMapper.findById(id);

        if (updatedMember == null) {
            throw new IllegalStateException(
                    "수정된 회원을 조회할 수 없습니다."
            );
        }

        return MemberResponse.from(updatedMember);
    }

    @Transactional
    public void withdrawMember(Long id) {

        int updatedRows = memberMapper.withdraw(id);

        if (updatedRows != 1) {
            throw new NoSuchElementException(
                    "탈퇴할 회원을 찾을 수 없습니다. id=" + id
            );
        }
    }
}