package _11_test_260127.memberProject.memberProject.upgradeMemberProgram.service;

import _11_test_260127.memberProject.memberProject.upgradeMemberProgram.dao.MemberDAO;
import _11_test_260127.memberProject.memberProject.upgradeMemberProgram.dto.MemberDTO;

import java.util.List;

// 260127_데이터베이스버전_전환_작업_순서1
public class _3_MemberService {
//    private static final String FILE_NAME = "members.txt";
//    private Map<String, _3_MemberBase> members = new HashMap<>();

    // 260127_데이터베이스버전_전환_작업_순서1-2
    // 미구현,
    // 구현함,
    // 260127_데이터베이스버전_전환_작업_순서10
    private MemberDAO dao = new MemberDAO();
    private MemberDTO loggedInMember = null;

    public MemberDTO getLoggedInMember() {
        return loggedInMember;
    }

    // 260127_리팩토링_코드정리_순서3
    // 모델 클래스 정리.
    // model 패키지(폴더), 파일 이동했다.

    // 260127_리팩토링_코드정리_순서4
    // 기존 기능들을 가져오기.
    // 회원가입

    // 260127_데이터베이스버전_전환_작업_순서11
    public String join(String name, String email, String password, int age) {
        // 260127_데이터베이스버전_전환_작업_순서11-2
        if (dao.getMember(email) != null ){
            return "이미 가입된 이메일입니다.";
        }
        // 변경 후, 데이터베이스 작성.
        MemberDTO newMember = new MemberDTO(name, email, password, age);
        // db에 저장하기.
        int result = dao.insertMember(newMember);

        return result >0 ? "success" : "fail";

    }


    // 260127_데이터베이스버전_전환_작업_순서12
    // 로그인
    public String login(String email, String password){
        MemberDTO member = dao.getMember(email);

        if (member == null) {
            return "존재하지 않는 이메일입니다.";
        }
        if (!member.getPassword().equals(password)) {
            return "패스워드가 틀렸습니다.";
        }

        this.loggedInMember = member; // 로그인 성공
        return "success";
    }

    // 260127_데이터베이스버전_전환_작업_순서13
    // 로그아웃
    public void logout() {
        this.loggedInMember = null;
    }

    // 260127_데이터베이스버전_전환_작업_순서14
    // 정보 수정 후 저장
    public void updateMember() {
        if (loggedInMember != null) {
            dao.updateMember(loggedInMember);
        }
    }


    // 260127_데이터베이스버전_전환_작업_순서15
    // 5. 회원 탈퇴
    public void deleteMember() {
        if (loggedInMember != null) {
            dao.deleteMember(loggedInMember.getEmail());
            logout(); // 탈퇴 후 로그아웃 처리
        }
    }

    // 260127_데이터베이스버전_전환_작업_순서16
    // 6. 목록 조회 (Map 대신 List 반환으로 변경 권장)
    public List<MemberDTO> getMemberList() {
        return dao.selectAllMembers();
    }

    // 260127_데이터베이스버전_전환_작업_순서17
    // 7. 검색 기능
    public List<MemberDTO> searchMembers(String keyword) {
        return dao.searchMembers(keyword);
    }



}
