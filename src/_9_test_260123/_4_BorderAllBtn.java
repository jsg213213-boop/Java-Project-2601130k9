package _9_test_260123;

import javax.swing.*;
import java.awt.*;

public class _4_BorderAllBtn {
    public static void main(String[] args) {
        // 컨테이너 생성
        JFrame frame = new JFrame("BorderLayout 모든영역");
        // 배치 관리자 지정,
        frame.setLayout(new BorderLayout());
        // 프레임에 UI 붙이기
        frame.add(new JButton("NORTH"), BorderLayout.NORTH);
        frame.add(new JButton("SOUTH"), BorderLayout.SOUTH);
        frame.add(new JButton("EAST"), BorderLayout.EAST);
        frame.add(new JButton("WEST"), BorderLayout.WEST);
        frame.add(new JButton("CENTER"), BorderLayout.CENTER);
        // 기본 옵션. 반복됨.
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
