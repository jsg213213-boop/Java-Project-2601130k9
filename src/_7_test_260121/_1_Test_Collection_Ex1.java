package _7_test_260121;

import java.util.ArrayList;

public class _1_Test_Collection_Ex1 {
    public static void main(String[] args) {
        // 1 ArrayList
        //1) ArrayList 기본 생성 방법. 
        // ArrayList<여기는 담을 데이터의 타입지정. > list = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        // <> : 다이어모든 연산자, "제너릭"라고 부름. 어떤 타입으로 배열의 구성요소를 지정할지. 정함. 
        //2) 추가: list.add(추가할 데이터, 단 위의 데이터 타입에 맞는 조건)
        list.add("사과");
        list.add("바나나");
        list.add("딸기");
        // 3) 조회, list.get(해당 인덱스 번호) -> 그 인덱서 번호 위치의 값을 불러옴.
        System.out.println("ArrayList<String> list의 list.get(0): " +list.get(0)); // "사과"
        System.out.println("ArrayList<String> list의 list.get(1): " +list.get(1)); // "바나나"
        System.out.println("ArrayList<String> list의 list.get(2): " +list.get(2)); // "딸기"
        // 4) 수정,list.set(수정할 인덱스 번호, 수정할 데이터);
        list.set(0,"사과2 수정");
        System.out.println("list.set(0,\"사과2 수정\"); 수정 후 , list.get(0): " +list.get(0)); //
        // 5) 삭제, list.remove(삭제할 인덱스 번호,);
        list.remove(2);
//        System.out.println("list.remove(2) 삭제 후 , list.get(2): " +list.get(2)); //

        // 6) 전체 삭제
        list.clear();
//        System.out.println("list.clear() 전체 삭제 후 , list.get(0): " +list.get(0)); //

        // 7) 리스트가 비어 있는지 여부 확인.
        boolean isEmpty = list.isEmpty();
        System.out.println("list의 비어 있는지 여부 확인. :" + isEmpty);

        // 전체 출력 방법. 향상된 for 문 이용.
        list.add("사과");
        list.add("바나나");
        list.add("딸기");
        System.out.println("향상된 for 문 이용 전체 출력  ");
        for ( String fruit: list) {
            System.out.println("list 요소 확인 : " + fruit);
        }
    }
}
