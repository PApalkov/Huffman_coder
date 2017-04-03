
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class Tree {
    private TreeNode root;
    private File file;
    Queue queue;

    /**
     * The method makes queue of symbols with its frequencies of meeting in text.
     * Then we sort it and symbols that we meet rather often are at the end of queue
     * and most seldom are at the beginning
     */
    public void make_queue(){

        try(FileReader reader = new FileReader(file))
        {

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

    }

    /**
     * Tis method makes tree by the queue
     * The visualisation of this method you can read here: https://habrahabr.ru/post/144200/
     */
    public void make_tree(){

        make_queue();

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

    /**
     *If we have the encoding table with the symbols and its codes
     * this method can build a tree by them
     */
    public void make_tree(ArrayList<DecodeTableNode> table){
        root = new TreeNode(null);
        //todo проверить правильность работы

        for (int i = 0; i < table.size(); i++) {
            TreeNode builder = root;
            String path = table.get(i).getCode();
            char value = table.get(i).getValue();

            for (int j = 0; j < path.length(); j++) {
                if (path.charAt(j) == '0'){

                    if (builder.getLeft() == null) {
                        builder.setLeft(new TreeNode(null));
                    }
                    builder = builder.getLeft();
                }
                else {
                    if (builder.getRight() == null) {
                        builder.setRight(new TreeNode(null));
                    }
                    builder = builder.getRight();
                }
            }

            builder.setValue(new QueueNode(value));

        }

    }

    /**
     * Coding file by the encoding table
     */
    public File code_file(){

        ArrayList<DecodeTableNode> table = make_table();

        File coded_file = new File("coded.txt");
        try(FileWriter writer = new FileWriter(coded_file, false); FileReader reader = new FileReader(file))
        {

            int c;
            while((c=reader.read())!=-1){

                char ch = (char)c;
                int i = 0;
                while (table.get(i).getValue() != ch) i++;
                writer.write(table.get(i).getCode());

            }

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        return coded_file;
    }


    public File code_file(File file){
        this.file = file;
        make_tree();
        return code_file();
    }

    /**
     *Decoding file by making tree by encoding table
     */
    public File decode_file(File coded_file, ArrayList<DecodeTableNode> table){

        make_tree(table);

        File decoded_file = new File("decoded.txt");
        try(FileWriter writer = new FileWriter(decoded_file, false); FileReader reader = new FileReader(coded_file))
        {

            int c = 0;
            while(c!=-1){

                TreeNode tmp = root;

                while ((tmp.getValue() == null) && c != -1){
                    c=reader.read();
                    char ch = (char)c;
                    if (ch == '0') tmp = tmp.getLeft();
                    else tmp = tmp.getRight();
                }

                if (tmp.getValue() != null)  writer.write(tmp.getValue().getValue());

            }

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        return decoded_file;
    }

    public File decode_file(File coded_file, File key_file){
        ArrayList<DecodeTableNode> table = make_table(key_file);
        return decode_file(coded_file, table);
    }

    /**
     *Generates a key file with symbols and its codes
     */
    public File make_key_file(ArrayList<DecodeTableNode> table){

        File key_file = new File("key.txt");
        try(FileWriter writer = new FileWriter(key_file, false);)
        {

            for (int i = 0; i < table.size(); i++) {
                String key_line = table.get(i).getValue() + table.get(i).getCode() + "\n";
                writer.write(key_line);
            }

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        return key_file;
    }

    /**
     *Generates encodig table by key file
     */
    public ArrayList<DecodeTableNode> make_table(File key_file){
        ArrayList<DecodeTableNode> table = new ArrayList<>();

        try(FileReader reader = new FileReader(key_file))
        {
            // читаем посимвольно
            int c = 0;
            while(c !=- 1){

                c = reader.read();
                char value = (char)c;;
                String code = "";

                c = reader.read();
                char ch = (char)c;
                while ((ch != '\n') && (c != -1)){
                    code += ch;
                    c = reader.read();
                    ch = (char)c;
                }

                if (c != -1)  table.add(new DecodeTableNode(value, code));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }


        return table;
    }

    /**
     *Generates encodig table by tree
     */
    public ArrayList<DecodeTableNode> make_table(){
        ArrayList<DecodeTableNode> table = new ArrayList<>();
        rec_make_table(root, table, "");

        return table;
    }

    private void rec_make_table(TreeNode tmp, ArrayList table, String path){
        if ((tmp.getLeft() != null) && (tmp.getRight() != null)){

            rec_make_table(tmp.getLeft(), table, path + '0');
            rec_make_table(tmp.getRight(), table, path + '1');


        } else {
            table.add(new DecodeTableNode(tmp.getValue().getValue(), path));
        }
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
