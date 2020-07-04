public class DequeTest {
//
//    @Test
//    public void testDeque_addFirst_removeFirst() {
//        Deque<Integer> deque = new Deque<>();
//        Assertions.assertEquals(0, deque.size());
//        Assertions.assertEquals(4, deque.capacity());
//        for (int i = 0; i < 4; i++) {
//            deque.addFirst(i);
//            Assertions.assertEquals(i + 1, deque.size());
//            Assertions.assertEquals(4, deque.capacity());
//        }
//        for (int i = 4; i < 8; i++) {
//            deque.addFirst(i);
//            Assertions.assertEquals(i + 1, deque.size());
//            Assertions.assertEquals(8, deque.capacity());
//        }
//        for (int i = 8; i < 16; i++) {
//            deque.addFirst(i);
//            Assertions.assertEquals(i + 1, deque.size());
//            Assertions.assertEquals(16, deque.capacity());
//        }
//
//        int size = deque.size();
//        int capacity = 16;
//        for (int i = 0; i < size; i++) {
//            Integer n = deque.removeFirst();
//            Assertions.assertEquals(15 - i, n);
//            if (i == 11 || i == 13) {
//                capacity = capacity >> 1;
//            }
//            Assertions.assertEquals(capacity, deque.capacity());
//        }
//    }
//
//    @Test
//    public void testDeque_addFirst_addLast() {
//        Deque<Integer> deque = new Deque<>();
//        Deque<Integer> dqFirst = new Deque<>();
//        Deque<Integer> dqLast = new Deque<>();
//        for (int i = 0; i < 16; ++i) {
//            if ((i & 0x1) == 0) {
//                dqFirst.addFirst(i); // 14 12 ... 0
//            }
//        }
//        for (int i = 0; i < 16; i++) {
//            if ((i & 0x1) == 1) {
//                dqLast.addLast(i);  // 15 13 ... 1
//            }
//        }
//        for (int i = 0; i < 16; i++) {
//            if ((i & 0x1) == 0) {
//                deque.addLast(dqFirst.removeLast()); // 0 2
//            } else {
//                deque.addLast(dqLast.removeFirst()); // 3 1
//            }
//        }
//
//        for (int i = 0; i < 15; i++) {
//            Integer n = deque.removeFirst();
//            Assertions.assertEquals(i, n);
//        }
//    }
}
