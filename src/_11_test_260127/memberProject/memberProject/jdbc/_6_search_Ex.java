package _11_test_260127.memberProject.memberProject.jdbc;

import _11_test_260127.memberProject.memberProject.model._3_MemberBase;
import _11_test_260127.memberProject.memberProject.model._3_NormalMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class _6_search_Ex {
    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println("연습용으로 members 테이블에 데이터 전체 조회 예제 확인. ");
        //드라이버 로딩.

        // 반복1
        // 순서1, 드라이버 로드, 자바 -> 오라클로 쿼리 를 전달하기 위한 도구 모음집을 메모리에 로드
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // 전체 데이터 조회 해보기.
        List<_3_MemberBase> members = searchMembers("홍");
        // 콘솔에 출력해보기.
        System.out.println("검색 결과 확인 : ");
        for(_3_MemberBase member :members) {
            System.out.println("이름 : " + member.getName());
            System.out.println("이메일 : " + member.getEmail());
            System.out.println("나이 : " + member.getAge());
            System.out.println("패스워드 : " + member.getPassword());
        }

    }

    // 검색 기능.
    public static List<_3_MemberBase> searchMembers(String keyword) {
        List<_3_MemberBase> list = new ArrayList<>();

        // LIKE 연산자와 OR를 사용하여 이메일 또는 이름에 키워드가 포함된 경우를 모두 찾습니다.
        String sql = "SELECT * FROM members WHERE email LIKE ? OR name LIKE ?";

        try (//1, 고정2
             Connection conn = getConnection(
                     "jdbc:oracle:thin:@localhost:1521:xe",
                     "scott",
                     "tiger");// 회원 추가 기능에서, 같은 코드 재사용.

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // %기호는 '앞뒤로 무엇이든 와도 좋다'는 뜻의 와일드카드입니다.
            // 예: "길동" 검색 -> "홍길동", "고길동" 모두 검색됨
            String searchKeyword = "%" + keyword + "%";

            pstmt.setString(1, searchKeyword); // 첫 번째 ? (email)
            pstmt.setString(2, searchKeyword); // 두 번째 ? (name)

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    _3_MemberBase dto = new _3_NormalMember();
                    dto.setEmail(rs.getString("email"));
                    dto.setName(rs.getString("name"));
                    dto.setPassword(rs.getString("password"));
                    dto.setAge(rs.getInt("age"));

                    list.add(dto); // 리스트에 추가
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list; // 검색된 회원 목록 반환 (없으면 빈 리스트)
    }
}
