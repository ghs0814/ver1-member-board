package com.example.memberboard.member.controller;

import java.net.URI;
import java.util.List;

import com.example.memberboard.member.dto.MemberCreateRequest;
import com.example.memberboard.member.dto.MemberResponse;
import com.example.memberboard.member.dto.MemberUpdateRequest;
import com.example.memberboard.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(
            @Valid @RequestBody MemberCreateRequest request
    ) {
        MemberResponse response = memberService.createMember(request);

        URI location = URI.create("/api/members/" + response.getId());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(
            @PathVariable("id") Long id
    ) {
        MemberResponse response = memberService.getMember(id);

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers() {

        List<MemberResponse> responses = memberService.getMembers();

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable("id") Long id,
            @Valid @RequestBody MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.updateMember(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> withdrawMember(
            @PathVariable("id") Long id
    ) {
        memberService.withdrawMember(id);

        return ResponseEntity.noContent().build();
    }
}