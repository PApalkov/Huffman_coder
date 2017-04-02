import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class Tree {
    private TreeNode root;
    private File file;
    Queue queue;

    public void make_queue(){

        try(FileReader reader = new FileReader(file))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

                char ch = (char)c;
                queue.add(ch);

            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        queue.sort();
        queue.print();
        //todo удалить лишнее

    }

    public void make_tree(){
        LinkedList<TreeNode> list = new LinkedList<>();
        for (int i = 0; i < queue.size(); i++) {
            list.add(new TreeNode(queue.get(i)));
        }

        while (list.size() > 1){

            TreeNode min1 = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getValue().getFrequency() < min1.getValue().getFrequency()){
                    min1 = list.get(i);
                }
            }

            list.remove(min1);


            TreeNode min2 = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getValue().getFrequency() < min2.getValue().getFrequency()){
                    min2 = list.get(i);
                }
            }

            list.remove(min2);

            int prior_sum = min1.getValue().getFrequency() + min2.getValue().getFrequency();

            TreeNode sub_root = new TreeNode(new QueueNode(prior_sum), min1, min2);

            int i = 0;
            while ( (i < list.size()) && (list.get(i).getValue().getFrequency() < prior_sum) ){
                i++;
            }

            list.add(i, sub_root);

        }

        root = list.get(0);

    }



    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Tree() {
        this.root = null;
        this.file = new File("text.txt");
        this.queue = new Queue();
    }

    public Tree(TreeNode root, File file, Queue queue) {
        this.root = root;
        this.file = file;
        this.queue = queue;
    }

}
