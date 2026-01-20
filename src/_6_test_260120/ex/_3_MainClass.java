package _6_test_260120.ex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class _3_MainClass {

    // 260120_실습4_풀이_업그레이드_임시저장파일_추가, 순서1
    // 우리가 저장할 파일의 이름 미리 지정
    private static final String FILE_NAME = "members.txt";

    public static void main(String[] args) {
        //최대 5명까지 저장 가능한 배열 생성.
        _3_MemberBase[] members = new _3_MemberBase[5];
        int count = 0; // 현재 저장된 회원 수 (배열 인덱스 관리용)

        // 260120_실습4_풀이_로그인한_유저_표기추가, 순서1
        // 현재 로그인한 회원을 저장할 변수 (초기갓 null)
        // 로그인 후, 로그인한 유저로 값을 채울 예정.
        _3_MemberBase loggedInMember = null;

        //  콘솔에 입력 내용 불러오는 도구.
        Scanner sc = new Scanner(System.in);

        // 메뉴 반복문 이용해서 그려보기.
        while (true) {
            System.out.println("\n=============회원 관리 시스템 ver 1.0=======");

            // 260120_실습4_풀이_로그인한_유저_표기추가, 순서2
            // 로그인한 유저가 있다면, 여기에 화면에 표시하기.
            if(loggedInMember != null) {
                System.out.println("-------------------------------------------");
                System.out.println("로그인 한 유저 : " + loggedInMember.getEmail());
                System.out.println("-------------------------------------------");
            }

            // 260120_실습4_풀이, 순서1, 메뉴 변경,
            // System.out.println("1. 회원가입 2. 목록조회 3. 종료");
            System.out.println("1. 회원가입 2. 목록조회 3. 로그인 4. 종료");
            System.out.println("메뉴 선택 >>");

            // 숫자를 입력 받을 준비 및 처리.
            int choice;
            try {
                // 콘솔에 입력된 내용은 모두 타입이 문자열입니다.
                // 그래서, 입력 받은 문자열을 숫자 형태로 변경해야함.
                // Integer.parseInt : 문자열 -> 숫자로 타입 변경 해주는 기능.
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
                // 콘솔에 숫자가 아닌 다른 문자등을 입력시에는, 반복문이
                // 종료되지 않고, 계속 동작 하게끔 설정.
                continue;
            }

            switch (choice) {
                case 1: // 회원 가입.
                    // count , 현재 가입된 회원의 숫자,
                    // members.length : 5개 고정 길이, 왜? 배열이라서, 고정.
                    if (count >= members.length) {
                        System.out.println("정원초과, 가입 불가입니다.");
                        break;
                    }
                    System.out.println("이름: ");
                    String name = sc.nextLine();

                    System.out.println("이메일: ");
                    String email = sc.nextLine();

                    // 260120_실습4_풀이, 순서7, 패스워드 정보도 받기.
                    System.out.println("패스워드: ");
                    String password = sc.nextLine();

                    System.out.println("나이: ");
                    // 문자열 -> 숫자 타입으로 변경 :
                    //Integer.parseInt : 문자열 -> 숫자로 타입 변경 해주는 기능.
                    int age = Integer.parseInt(sc.nextLine());

                    // 다형성 활용 : 부모 타입 배열에, 자식 객체 저장.
                    // 260120_실습4_풀이, 순서8, 생성자가 기존 매개변수3개에서, 4개로 수정 해야함.
//                    _3_NormalMember newMember = new _3_NormalMember(name, email, age);
                    _3_NormalMember newMember = new _3_NormalMember(name, email, password, age);
                    // 배열에 저장.
                    members[count] = newMember;
                    // 인터페이스 메서드 호출
                    newMember.join();

                    // 인덱스 증가,. 현재 가입할 인원 증가.
                    count++;
                    break;

                //2, 3번 작업 이어서 진행하기.
                //목록 조회
                case 2:
                    if (count == 0) {
                        System.out.println("가입된 회원이 없습니다. ");
                    } else {
                        System.out.println("\n 총회원수 : " + count + "명입니다.");
                        for (int i = 0; i < count; i++) {
                            members[i].showInfo(); // 다형성 (오버라이딩된 메서드 실행)
                        }
                    }
                    break;
                // 260120_실습4_풀이, 순서8, 로그인 기능 추가 및 작업
                //로그인
                case 3:
                    // 순서대로 확인하기.
                    System.out.println("\n====로그인===== ");
                    // 이메일, 패스워드 정보를 전달 받아서,
                    // 배열에 등록된, 이메일로 유저를 찾고, 패스워도 비교하고, 일치하면, 로그인,
                    // 아니면, 로그인 불가.
                    System.out.println("이메일 : ");
                    String inputEmail = sc.nextLine();

                    System.out.println("패스워드 : ");
                    String inputPassword = sc.nextLine();

                    // 상태 변수, 로그인 성공 여부 체크
                    boolean isLogin = false;

                    // 회원 정보가 들어 있는 배열을 전체 순회,
                    // 등록된 회원 숫자 만큼만 반복, count 라는 변수를 활용.
                    for (int i = 0; i < count; i++) {
                        // 임시 메모리 상에 저장된 회원의 이메일과, 패스워드 확인하는 절차.
                        // 저장된 회원 한명씩 꺼내서, member 에 담아두고,
                        // 입력된 이메일, 패스워드와,,  불러온  이메일, 패스워드 일치 여부 확인?
                        _3_MemberBase member = members[i];
                        // 주의사항,
                        // 문자열 비교시에는 사용하는 메서드
                        // 문자열1.equals(문자열2) => 같으면, true, 다르면, false
                        // member.getEmail() : 문자열1
                        // inputEmail : 문자열2
                        // 숫자 비교
                        // 1 == 2 :  false
                        if(member.getEmail().equals(inputEmail) &&
                        member.getPassword().equals(inputPassword)
                        ) {
                            System.out.println("로그인 성공!! 환영합니다.~" + member.name+ "님");
                            isLogin = true;

                            // 260120_실습4_풀이_로그인한_유저_표기추가, 순서3
                            // 로그인 성공시 해당 로그인한 객체를 변수에 저장.
                            loggedInMember = member;
                            break;
                        } // if 닫기
                    } // for 닫기.

                    // 로그인 실패인 경우,
                    if(!isLogin) {
                        System.out.println("로그인 실패: 정보가 일치하지 않습니다. ");
                    }
                    break;

                // 260120_실습4_풀이, 순서9, 숫자만 변경, 기존 3에서, 4로 변경.
                //종료
                case 4:
                    System.out.println("프로그램을 종료합니다. ");
                    // 스캐너 자원 반납
                    sc.close();
                    return; // 메인 메서드 종료
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");


            } //switch 닫기


        }//while 닫기

    } // main 닫기

// 260120_실습4_풀이_업그레이드_임시저장파일_추가, 순서2
//    1) 저장하는 기능의 메서드 만들기, 정적(static)
    // 준비물 : 1) 메모리상에 저장된 멤버들의 배열 members , 2) 가입된 인원수 count
    public static void saveMembers(_3_MemberBase[] members, int count){
        // BufferedWriter : 버퍼를 사용해 파일 쓰기 속도를 높여줍니다.
        BufferedWriter bw = null;

        // try ~ catch ~ finally, 1) 파일 입출력 2) 네트워크 통신이럴 경우, 반드시 의무적으로
        // try 구문안에 작성해야함.
        try {
            // 스트림 생성.
            // new FileWriter(FILE_NAME) : 우리가 지정한 파일에 쓰겠다. 기록 하겠다.
            // new BufferedWriter (여기 ) : "여기"라는 내용을 좀더 빠르게 쓰겠다.
            bw = new BufferedWriter(new FileWriter(FILE_NAME));

            // members[i] 배열이고, 메모리상에 저장된 멤버들
            // 반복문을 이용해서, 메모리상에 저장된 멤버들을, members.txt 파일에 기록하는 과정.
            for(int i = 0; i < count; i++) {
                _6_test_260120.ex._3_MemberBase m = members[i];
                // 파일에 저장 형식 : 이름,이메일,패스워드,나이 (구분자: 쉼표로 구분, csv)
                // 기존 _3_MemberBase 클래스, 이메일과, 패스워드 만 읽을수 있는데,
                // 여기서, 추가로, 이름, 나이도 같이 조회하게 기능 추가.
                // _3_MemberBase 클래스 이동해서, getter 생성, 이름과, 나이 추가.

                String line = m.getName()+","+m.getEmail()+","+m.getPassword()+","+m.getAge();
                // 파일에 한줄씩 기록 하겠다.
                bw.write(line);
                bw.newLine(); // 줄바꿈 함.
            }
            System.out.println("파일 저장 완료 " + FILE_NAME);

        } catch (IOException e){
            System.out.println("오류가 발생 했습니다. 원인: " + e.getMessage());
        }finally {

        }
    }

// 260120_실습4_풀이_업그레이드_임시저장파일_추가, 순서3
//    2) 불러오는 기능의 메서드 만들기, 정적(static)

}// _3_MainClass 닫기
