package _9_test_260123.ex;

import javax.swing.*;
import java.awt.*;

public class _2_BorderLayout_Ex {
//    BorderLayout의 각 영역(NORTH, SOUTH, EAST, WEST, CENTER)에
//    모두 다른 컴포넌트(버튼, 라벨, 텍스트필드 등)를 배치해보세요.

//
//    크기 , 가로 350, 높이 180
//    기본옵션 사용.
    public static void main(String[] args) {
        // 컨테이너 생성
        JFrame frame = new JFrame("실습2 _2_BorderLayout_Ex ");
        // 배치 관리자 지정,
        frame.setLayout(new BorderLayout());
        // 프레임에 UI 붙이기
        //- 북 : 라벨 , 헤더
        //- 남 : 버튼, 확인
        //- 동 : 텍스트필드 , 입력
        //- 서 : JCheckBox , 선택
        //- 중앙 : JTextArea(4,12) 정도,
        frame.add(new JLabel("헤더"), BorderLayout.NORTH);
        frame.add(new JButton("확인"), BorderLayout.SOUTH);
        frame.add(new JTextField("입력",8), BorderLayout.EAST);
        frame.add(new JCheckBox("선택"), BorderLayout.WEST);
        frame.add(new JTextArea(4,12), BorderLayout.CENTER);
        // 기본 옵션. 반복됨.
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
