package com.deadline.knunotice.member;

public interface MemberService {

    Member findMemberByUsername(String username);

    Member login(String username, String password);

    Member signUp(String email);

    Member findMemberByEmail(String email);

}
