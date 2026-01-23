package _9_test_260123.memberProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

// 260123_화면_스윙_변경__순서1
// 해당 클래스가, JFrame 관련 그리기 도구를 사용하기 위해서, 상속.
public class _3_MainClass extends JFrame {

    private static final String FILE_NAME = "members.txt";

    // 260123_화면_스윙_변경__순서2
    // 전역으로 사용할 멤버들 지정, 변수 지정.
    private Map<String, _3_MemberBase> members = new HashMap<>();
    // 로그인한 멤버 상태.
    private _3_MemberBase loggedInMember = null;

    // 260123_화면_스윙_변경__순서2-2
    // GUI 화면 구성 요소, 전역,
    private JTextArea displyArea; // 결과 출력을 위한 텍스트 영역.
    private JPanel buttonPanel; // 버튼들이 들어갈 패널
    private JLabel statusLabel; // 현재 로그인 상태 표시

    // 260123_화면_스윙_변경__순서2-3
    // 버튼 정의,
    private JButton btnJoin, btnList, btnLoginLogout, btnEdit, btnSearch, btnExit;
    //private JButton btnList;

    public static void main(String[] args) {
        // 260123_화면_스윙_변경__순서3
        // [GUI 변경] 메인 스레드에서 GUI 실행,
        SwingUtilities.invokeLater(() -> {
            new _3_MainClass();
        });
    }

    // 260123_화면_스윙_변경__순서4
    // _3_MainClass 생성자 정의.
    public _3_MainClass() {

        // 260123_화면_스윙_변경__순서4-2
        // 부모 클래스의 생성자가 호출 후, 자식 클래스의 생성자 호출.
        // 부모 클래스 ( JFrame) , 창의 제목을 설정.
        super("회원 관리 시스템 ver 3.2(GUI버전)");

        // 데이터 로드 , 기존 코드, 파일에서, 회원 정보를 불러오기.
        loadMembers(members);

        // 260123_화면_스윙_변경__순서4-3
        // UI 초기화하는 함수를 호출.
        // 아직 미생성.
        initUI();

        // 260123_화면_스윙_변경__순서4-4
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // 화면 중앙 배치
        setLocationRelativeTo(null);
    } // _3_MainClass()생성자 닫기.


        // 260123_화면_스윙_변경__순서5
        //[GUI 변경] UI 화면 구성 메서드
        // 여기가 메인, 나머지 기능 구현은 기존 꺼 재사용함.
        // 화면작업 메인.
        private void initUI() {
            // 260123_화면_스윙_변경__순서5-2
            setLayout(new BorderLayout()); // 동,서,남,북, 중앙 배치 관리자.

            // 상단
            // 260123_화면_스윙_변경__순서5-3
            // 1. 상단 상태 표시줄
            statusLabel = new JLabel("로그인 상태 : 로그아웃 됨", SwingConstants.CENTER);
            // 글꼴 옵션,
            statusLabel.setFont(new Font("맑은 고딕",Font.BOLD, 14));
            // 경계 옵션,
            statusLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
            // 필수,
            add(statusLabel, BorderLayout.NORTH);


            // 가운데
            // 260123_화면_스윙_변경__순서5-4
            // 2. 중앙 텍스트 영역(콘솔 출력 대체)
            displyArea = new JTextArea();
            displyArea.setEditable(false);// 수정 못하게 방지
            // 글꼴 옵션
            displyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            // JTextArea 내부에 공간이 다차면, 스크롤 기능이 생기게 설정.
            add(new JScrollPane(displyArea), BorderLayout.CENTER);


            //하단,
            // 260123_화면_스윙_변경__순서5-5
            // 3. 하단 버튼 패널
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(2,3,5,5));

            // 버튼 생성, 초기화, (전역에서 선언 후, 이 메서드 안에서 초기화해서 사용함.)
            btnJoin = new JButton("1 회원가입");
            btnList = new JButton("2 목록조회");
            btnLoginLogout = new JButton("3 로그인"); //초깃값, 로그인을 하면, 로그 아웃으로 보일 예정.
            btnEdit= new JButton("4 회원수정");
            btnSearch = new JButton("5 회원검색");
            btnExit = new JButton("6 종료");
            // 삭제 기능은 완성 후, 실습으로 제시.
//            btnJoin = new JButton("1 회원가입");

            // 260123_화면_스윙_변경__순서5-6, 잠시대기
            // 버튼 이벤트 핸들러(리스너 등록)
            // 내부에 리스너를 처리하는 클래스를 만들어서 재사용을 하기.

            // 260123_화면_스윙_변경__순서5-7
            // 버튼을 패널에 붙이기 작업.
            buttonPanel.add(btnJoin);
            buttonPanel.add(btnList);
            buttonPanel.add(btnLoginLogout);
            buttonPanel.add(btnEdit);
            buttonPanel.add(btnSearch);
            buttonPanel.add(btnExit);

            // 260123_화면_스윙_변경__순서5-8
            // 버튼 패널, 프레임 하단에 배치
            add(buttonPanel, BorderLayout.SOUTH);

            // 260123_화면_스윙_변경__순서5-9
            // 버튼들의 초기 상태 결정. 임시 메서드 설정,
//            updateButtonState();
        }

    // 260123_화면_스윙_변경__순서6
        //[GUI 변경] 버튼 클릭 이벤트 처리하는 내부 클래스 정의,
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 버튼이 많아요, 각 버튼의 모양에 따라서, 각 기능을 붙이기.
            // 기존: 콘솔에서, 입력된 내용을 가져와서 사용. 가져오는 방법 다 동일.
            // 변경: 화면에서, 입력된 내용을 가져와서 사용. 가져오는 방법 다름.

            // 입력 대상이 무엇인지 분별하기. 클릭 하는 버튼 요소를 분별하기.
            // Object: 모든 클래스의 부모다, 최종 클래스, 끝판대장.
            Object source = e.getSource();

            // 클릭 한 요소를 일단, 다 받을수 있는 Object 받아두고,
            // 그리고, 각 요소가 무엇인지 정확히 분기.
            if (source == btnJoin) {
                // 아직 기능은 미구현, 메서드 명만 표기.
//                handleJoin();
            } else if (source == btnList) {
                handleList();
            } else if (source == btnLoginLogout) {
//                handleLoginLogout();
            } else if (source == btnEdit) {
//                handleEdit();
            } else if (source == btnSearch) {
//                handleSearch();
            } else if (source == btnExit) {
//                handleExit();
            }
        } //actionPerformed 닫기
    } //ActionHandler 닫기

    // 260123_화면_스윙_변경__순서7
    // 기능 하나씩 구현해보기.
    //로그 출력 헬퍼
    private void printLog(String msg) {
        displyArea.append(msg + "\n");
        // 스크롤 하단 이동 기능.
        displyArea.setCaretPosition(displyArea.getDocument().getLength());
    }

    // 2. 목록조회
    private void handleList() {
        displyArea.setText(""); // 최초에 항상 기존 내용 다지우고, 새로 불러오는 형식.
        printLog("===회원 목록====");
        if(members.isEmpty()) {
            printLog("가입된 회원이 없습니다.");
        } else {
            // 기존에서 사용하던 Map 순회에서, 출력.
            for(_3_MemberBase member : members.values()) {
                String info = String.format("이름 : %s | 이메일 : %s | 나이 : %d",
                       member.getName(), member.getEmail(), member.getAge() );
                printLog(info);
            }
        }
    }




    public static void saveMembers(Map<String, _3_MemberBase> members){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for(_3_MemberBase m: members.values()) {
                String line = m.getName()+","+m.getEmail()+","+m.getPassword()+","+m.getAge();
                bw.write(line);
                bw.newLine(); // 줄바꿈 함.
            }
            System.out.println("파일 저장 완료 " + FILE_NAME);

        } catch (IOException e){
            System.out.println("오류가 발생 했습니다. 원인: " + e.getMessage());
        }finally {
            if(bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println("오류 발생, 파일 닫기 실패. ");
                }
            }

        }
    }

    public static int loadMembers(Map<String, _3_MemberBase> members){
        File file = new File(FILE_NAME);
        if(!file.exists()) { // 해당 파일이 존재 안하니? true(파일없다)
            return 0;
        }
        int loadCount = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null ) {
                String[] data = line.split(",");
                if( data.length == 4) {
                    String name = data[0];
                    String email = data[1];
                    String password = data[2];
                    int age = Integer.parseInt(data[3]);
                    members.put(email,new _3_NormalMember(name,email,password,age));
                    loadCount++;

                }
            }
            System.out.println("파일 불러오기 완료 :" + loadCount + "명의 회원 정보를 불러옴.");
        }catch (IOException e){
            System.out.println("파일 불러오기 실패 원인 : " + e.getMessage());
        }finally {
            if( br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("파일 닫기 실패");
                }
            }
        } // finally 닫기
        return loadCount;
    }

}// _3_MainClass 닫기
