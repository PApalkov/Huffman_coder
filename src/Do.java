import java.io.File;
import java.util.ArrayList;

public class Do {

    public static void main(String[] args) {
        Tree tree = new Tree();


        //tree.code_file(new File("text.txt"));

        //tree.make_key_file(tree.make_table());
        File coded_file = new File("coded.txt");
        File key_file= new File("key.txt");

        tree.decode_file(coded_file, key_file);

        System.out.println("Got_here");
    }
}
