/**
 * The Node class of Queue
 */

public class QueueNode {

    private char value;
    private int frequency;

    public void inc_frequeny(){
        this.frequency++;
    }

    public QueueNode() {
        this.value = ' ';
        this.frequency = 0;
    }


    public QueueNode(char ch) {
        this.value = ch;
        this.frequency = 1;
    }

    public QueueNode(int frequency) {
        this.value = ' ';
        this.frequency = frequency;
    }


    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
