package Manager;

import java.util.*;

import TaskObject.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final HandLinkedList<Task> histList = new HandLinkedList<>();
    private final Map<Integer, HandLinkedList.Node<Task>> histMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (histMap.containsKey(task.getId())) {
            histList.removeNode(histMap.get(task.getId()));
            histMap.remove(task.getId());
        }


        Task copyTask = new Task(task.getTitle(), task.getDescription(), task.getStatus());
        copyTask.setId(task.getId());
        histList.linkLast(copyTask);
        histMap.put(task.getId(), histList.getTail());
    }



    @Override
    public List<Task> getHistList() {
        return new ArrayList<>(histList.getTasks());
    }

    @Override
    public void removeView(int id) {
        if (histMap.containsKey(id)) {
            histList.removeNode(histMap.get(id));
        }
        histMap.remove(id);
    }


    public static class HandLinkedList<Task> {

        private Node<Task> head;
        private Node<Task> tail;
        private int size = 0;

        static class Node<Task> {
            public Task data;
            public Node<Task> prev;
            public Node<Task> next;

            public Node(Node<Task> prev, Task data, Node<Task> next) {
                this.data = data;
                this.next = next;
                this.prev = prev;
            }
        }

        public int getSize() {
            return size;
        }

        public void linkLast(Task task) {
            final Node<Task> oldTail = tail;
            final Node<Task> newTask = new Node<>(oldTail, task, null);
            tail = newTask;
            if (oldTail == null) head = newTask;
            else oldTail.next = newTask;
            size++;
        }

        public Node<Task> getTail() {
            return tail;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            if (head != null) {
                Node<Task> node = head;
                while (node != null) {
                    tasks.add(node.data);
                    node = node.next;
                }
            }
            return tasks;
        }

        public void removeNode(Node<Task> node) {
            if (node.prev == null && node.next == null) {
              tail = null;
              head = null;
              size--;
            } else if (node.prev != null && node.next == null) {
                node.prev.next = null;
                tail = node.prev;
                size--;
            } else if (node.prev == null && node.next != null) {
                node.next.prev = null;
                head = node.next;
                size--;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            node.prev = null;
            node.next = null;
            node.data = null;
        }


    }


}
