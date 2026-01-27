package _11_test_260127.memberProject.memberProject.upgradeMemberProgram.dao;

import _11_test_260127.memberProject.memberProject.upgradeMemberProgram.dto.MemberDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// 260127_데이터베이스버전_전환_작업_순서2
public class MemberDAO {

    // 260127_데이터베이스버전_전환_작업_순서3
    // 공통 DB 연결 위한 , 준비물.
    // 반복3, 각 메서드 안에서, 이 메서드를 호출하므로,  코드 길이 단축.
    private Connection getConnection() throws Exception {
        // 반복1
        Class.forName("oracle.jdbc.driver.OracleDriver");
        // 반복2
        Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "scott",
                "tiger");
        return conn;
    }

    // 260127_데이터베이스버전_전환_작업_순서4
    // 회원가입.
    // insert 예시코드 이용.

    // 260127_데이터베이스버전_전환_작업_순서4-2
    public int insertMember(MemberDTO member) {
        // 연습용, 직접 email, password, name, age
//    public static void insertMember(String name, String email, String password, int age) {
        // 순서2, 작업할 sql 문장을 만들어야 함.
        String sql = "INSERT INTO members(name, email, password, age) VALUES(?, ?, ?, ?)";

        // try-with-resources 문법: 자동으로 close 해줌
        // 순서3, 연결하기 위한 도구(Connection conn)를 이용해야하고,
        // 재사용 준비물 :1) url 주소 (현재는 로컬의 도커로 접근) 2)유저 3) 패스워드

        // 260127_데이터베이스버전_전환_작업_순서4-3
        try (Connection conn = getConnection();

             // 순서4, sql를 전달하는 도구 : PreparedStatement pstmt,
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // ?(위치홀더) 순서대로 값 세팅
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.setInt(4, member.getAge());

            // 순서6, 실행하기.
            int result = pstmt.executeUpdate(); // 성공한 행의 개수 리턴
            System.out.println(result + "행이 추가되었습니다.");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 260127_데이터베이스버전_전환_작업_순서5
    // 전체목록 조회
    public List<MemberDTO> selectAllMembers() {
        // 260127_데이터베이스버전_전환_작업_순서5-2
        List<MemberDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY name ASC"; // 이름순 정렬

        try (//1, 고정2
             // 260127_데이터베이스버전_전환_작업_순서5-3
             Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 데이터가 여러 줄이므로 while문 사용
            while (rs.next()) {
                // 260127_데이터베이스버전_전환_작업_순서5-4
                MemberDTO dto = new MemberDTO();
                // DB에서 꺼내서 DTO 객체에 담기
                dto.setName(rs.getString("name"));
                dto.setEmail(rs.getString("email"));
                dto.setPassword(rs.getString("password"));
                dto.setAge(rs.getInt("age"));

                // 리스트에 추가
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list; // 완성된 리스트 반환
    }

    // 260127_데이터베이스버전_전환_작업_순서6
    // 내정보조회
    // 260127_데이터베이스버전_전환_작업_순서6-2
    // 260127_데이터베이스버전_전환_작업_순서11-3, 기존 메서드 변경하기. 반환을 멤버의 객체로 받기.
    public MemberDTO getMember(String email) {
        //순서3, 조회할 sql 구문 작성,
        String sql = "SELECT * FROM members WHERE email = ?";

        // 260127_데이터베이스버전_전환_작업_순서11-4
        MemberDTO dto = null ; //못찾으면 null 반환

        // 회원 추가 기능에서, 같은 코드 재사용.
        // 순서4, try with resources 구문에, 연결 객체 도구를 이용하고,
        try (
                //1, 고정2
                // 260127_데이터베이스버전_전환_작업_순서6-3
                Connection conn = getConnection();

                //2
                // 순서5, sql 를 전달하는 기능의 클래스를 이용하고,
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            // 순서6, 전달할 sql 에서, 검색할 이메일 정보를 입력,
            pstmt.setString(1, email);
            // 순서7, 오라클 서버에 쿼리 문을 전달해서, 해당 결과 테이블의 내용을 받아오고,
            ResultSet rs = pstmt.executeQuery();

            // 순서8, 받아온 내용이 테이블 형식으로 되어 있고,
            //  ResultSet rs  : 테이블 구조 형식.
            if (rs.next()) { // 포인터를 다음 행으로 이동, 데이터가 있으면 true
                // 순서9, 받아온 테이블에서, 각컬럼의 값들을 하나씩 읽어오기.

                String name = rs.getString("name");
                String email2 = rs.getString("email");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                // 260127_데이터베이스버전_전환_작업_순서11-5
                // 임시 MemberDTO에 담기.
                dto = new MemberDTO();
                dto.setName(name);
                dto.setEmail(email2);
                dto.setPassword(password);
                dto.setAge(age);

                System.out.println("검색 결과 -> 이름: " + name + ", 나이: " + age);
                System.out.println("이메일 : " + email2);
                System.out.println("password : " + password);
                // 참고로 try catch finally  는 사용할 도구를 반납해야하는데,
                // 현재는 try with resources 문법을 사용해서, 자동 반납을 한다. (현재 이설정)
            } else {
                System.out.println("해당 이메일의 회원이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 260127_데이터베이스버전_전환_작업_순서11-6
        return dto;
    }

    // 260127_데이터베이스버전_전환_작업_순서7
    // 회원 수정 (Update)
    // 260127_데이터베이스버전_전환_작업_순서7-2
    public  void updateMember(MemberDTO member) {
//    public static void updateMember(String name, String email, String password, int age) {
        // 이메일(PK 역할)을 기준으로 이름, 비밀번호, 나이를 수정한다고 가정
        // 반복2
        String sql = "UPDATE members SET name = ?, password = ?, age = ? WHERE email = ?";

        // 반복3
        try (//1, 고정2
             // 260127_데이터베이스버전_전환_작업_순서7-3
             Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 반복4, 여기는 변경됨
            // SQL문의 ? 순서에 맞춰 값 설정
            // 260127_데이터베이스버전_전환_작업_순서7-4
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getPassword());
            pstmt.setInt(3, member.getAge());
            pstmt.setString(4, member.getEmail()); // WHERE절의 이메일

            // 반복5,여기는 변경됨
            int result = pstmt.executeUpdate(); // 변경된 행의 개수 반환

            // 반복6,여기는 변경됨
            if (result > 0) {
                System.out.println("회원 정보 수정 완료!");
            } else {
                System.out.println("수정할 회원을 찾지 못했습니다. (이메일 확인 필요)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 회원 삭제 (Delete)
    // 260127_데이터베이스버전_전환_작업_순서8
    public  void deleteMember(String email) {
        // 반복2
        String sql = " DELETE FROM members WHERE email = ?";

        try (//1, 고정2
             // 260127_데이터베이스버전_전환_작업_순서8-2
             Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 반복4, 여기는 변경됨
            pstmt.setString(1, email);

            // 반복5,여기는 변경됨
            int result = pstmt.executeUpdate();
            //반복6,여기는 변경됨
            if (result > 0) {
                System.out.println("회원 삭제 완료: " + email);
            } else {
                System.out.println("삭제할 회원이 존재하지 않습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 회원 검색.
    // 260127_데이터베이스버전_전환_작업_순서9
    public List<MemberDTO> searchMembers(String keyword) {
        // 260127_데이터베이스버전_전환_작업_순서9-2
        List<MemberDTO> list = new ArrayList<>();

        // LIKE 연산자와 OR를 사용하여 이메일 또는 이름에 키워드가 포함된 경우를 모두 찾습니다.
        String sql = "SELECT * FROM members WHERE email LIKE ? OR name LIKE ?";

        try (//1, 고정2
             // 260127_데이터베이스버전_전환_작업_순서9-3
             Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // %기호는 '앞뒤로 무엇이든 와도 좋다'는 뜻의 와일드카드입니다.
            // 예: "길동" 검색 -> "홍길동", "고길동" 모두 검색됨
            String searchKeyword = "%" + keyword + "%";

            pstmt.setString(1, searchKeyword); // 첫 번째 ? (email)
            pstmt.setString(2, searchKeyword); // 두 번째 ? (name)

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MemberDTO dto = new MemberDTO();
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
