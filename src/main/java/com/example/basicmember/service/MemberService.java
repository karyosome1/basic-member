package com.example.basicmember.service;

import com.example.basicmember.dto.MemberRequestDto;
import com.example.basicmember.dto.MemberResponseDto;
import com.example.basicmember.entity.Member;
import com.example.basicmember.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    public final MemberRepository memberRepository;

    // 멤버 생성
    @Transactional
    public MemberResponseDto save(MemberRequestDto dto) {
        Member member = new Member(dto.getName());
        Member savedMember = memberRepository.save(member);

        return new MemberResponseDto(savedMember.getId(), savedMember.getName());
    }

    // 멤버 전체 조회
    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<Member> members = memberRepository.findAll();

        List<MemberResponseDto> dtos = new ArrayList<>();
        for (Member member : members) {
            MemberResponseDto dto = new MemberResponseDto(member.getId(), member.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    // 멤버 단건 조회
    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id 회원없음! 못 줘!")
        );

        return new MemberResponseDto(member.getId(), member.getName());
    }

    // 메모 수정
    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto dto) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id 회원 없음! 못 줘!")
        );

        member.update(dto.getName()); // 영속성 컨텍스트
        return new MemberResponseDto(member.getId(), member.getName());
    }

    // 메모 삭제
    @Transactional
    public void deleteById(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 id 회원 없어서 삭제 못함.");
        }
        memberRepository.deleteById(id);
    }



}
