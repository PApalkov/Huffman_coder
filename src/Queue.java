/**
 This class holds symbols and their frequency in text
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Queue {

    /**
     * Using LinkedList is optimal, because the number of symbols is not large and we need to
     * add new elements rather often
     */
    private List<QueueNode> queue;


    public void add(char value){
        if (!contains(value)){
            queue.add(0, new QueueNode(value));
        } else {
            inc_frequency(value);
        }
    }

    public void inc_frequency(char value){
        int i = 0;
        while (value != queue.get(i).getValue()){
            i++;
        }

        queue.get(i).inc_frequeny();
    }

    public void sort(){
        queue.sort(new Comparator<QueueNode>() {
            @Override
            public int compare(QueueNode o1, QueueNode o2) {
                if (o1.getFrequency() > o2.getFrequency()) return 1;
                else if (o1.getFrequency() < o2.getFrequency()) return -1;
                else return 0;
            }
        });
    }

    public void print(){
        QueueNode tmp;
        for (int i = 0; i < queue.size(); i++) {
            tmp = queue.get(i);
            System.out.println(tmp.getValue() + " - " + tmp.getFrequency());
        }
    }

    public boolean contains(char value){
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getValue() == value){
                return true;
            }
        }
        return false;
    }

    public int size(){
        return queue.size();
    }

    public QueueNode get(int i){
        return queue.get(i);
    }

    public Queue() {
        this.queue = new ArrayList<>();
    }

    public Queue(ArrayList<QueueNode> queue) {
        this.queue = queue;
    }

    public List<QueueNode> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<QueueNode> queue) {
        this.queue = queue;
    }

}
