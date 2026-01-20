package _6_test_260120.ex;

public abstract class _3_MemberBase {
    // 현재 여기 접근 지정은, 상속 받은 자식 클래스는 접근이 가능하지만,
    // 메인 클래스는 상속을 받지 않아서, 접근 불가능함. 그래서, 만약 필요시, 접근 하기 위해서는
    // getter 라는 메서드를 작성 해야함.
    // 260120_실습4_풀이, 순서5, getter 사용하는 부분 참고해야함.
    protected String name;
    protected String email;
    // 260120_실습4_풀이, 순서2, 패스워드 변수 추가
    protected String password;
    protected int age;

    // 260120_실습4_풀이, 순서3, 생성자 수정.
//    public _3_MemberBase(String name, String email, int age) {
    public _3_MemberBase(String name, String email,String password, int age) {
        this.name = name;
        this.email = email;
        // 260120_실습4_풀이, 순서4, 멤버 추가.
        this.password = password;
        this.age = age;
    }

    public abstract void showInfo(); // 정보 출력용 추상 메서드
    // 260120_실습4_풀이, 순서5, 이메일, 패스워드 조회 하는 기능 추가. getter
    // 생성시, 우클릭 -> 생성 -> Getter -> 각각, 하나씩, email, password 선택.
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    // 260120_실습4_풀이_업그레이드_임시저장파일_추가, 순서2-1
    // 생성시, 우클릭 -> 생성 -> Getter -> 각각, 하나씩, name, age 선택.

    public String getName() {
        return name;
    }
    // 260120_실습4_풀이_업그레이드_임시저장파일_추가, 순서2-2
    public int getAge() {
        return age;
    }
}
