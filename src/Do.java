import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Do {

    private static JFrame window;
    private static JPanel buttonPanel;
    private static JTextArea input;
    private static JButton[] buttons;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> initilize());
    }

    private static void initilize(){

        window = new JFrame("Huffman Encoder");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(830, 600));



        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1));
        buttonPanel.setBorder(new EmptyBorder(20,0,20,20));

        buttons = new JButton[3];

        buttons[0] = new JButton(new AbstractAction("Закодировать") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String in = input.getText();
                HuffmanTree tree = new HuffmanTree();
                if (in.length() > 0) {

                    JFileChooser savefile = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
                    savefile.setFileFilter(filter);

                    savefile.setDialogTitle("Coхранить закодированный файл");
                    int ret = savefile.showSaveDialog(null);

                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File coded_file = savefile.getSelectedFile();
                        File text = new File("text.txt");

                        try (FileWriter writer = new FileWriter(text, false);) {

                            writer.write(in);

                            writer.flush();
                            writer.close();
                        } catch (IOException ex) {

                            System.out.println(ex.getMessage());
                        }

                        tree.code_file(text, coded_file);
                    }

                    savefile.setDialogTitle("Coхранить ключевой файл");
                    ret = savefile.showSaveDialog(null);


                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File key_file = savefile.getSelectedFile();
                        tree.make_key_file(key_file);
                    }

                }
                else {
                    JOptionPane.showMessageDialog(window,
                            "Введите текст!",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        buttons[1] = new JButton(new AbstractAction("Декодировать") {
            @Override
            public void actionPerformed(ActionEvent e) {

                input.setText("");

                JFileChooser fileopen = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                fileopen.setFileFilter(filter);

                int ret = fileopen.showDialog(null, "Выбрать закодированый файл");

                File coded_file ;
                if (ret == JFileChooser.APPROVE_OPTION) {
                    coded_file = fileopen.getSelectedFile();

                } else coded_file = null;

                ret = fileopen.showDialog(null, "Выбрать файл с ключом");

                File key_file;
                if (ret == JFileChooser.APPROVE_OPTION) {
                    key_file = fileopen.getSelectedFile();
                } else key_file = null;

                File decoded = new HuffmanTree().decode_file(coded_file, key_file);

                try(FileReader reader = new FileReader(decoded))
                {
                    int c;
                    while((c=reader.read())!=-1){
                        char ch = (char)c;
                        String str = "" + ch;
                        input.append(str);
                    }
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

        buttons[2] = new JButton(new AbstractAction("Очистить") {
            @Override
            public void actionPerformed(ActionEvent e) {
               input.setText("");
            }
        });

        for (int i = 0; i < 3; i++) {
            buttonPanel.add(buttons[i]);
        }

        JPanel text = new JPanel();
        text.setBorder(new EmptyBorder(20, 20, 20, 20));

        input = new JTextArea();
        input.setPreferredSize(new Dimension(620,520));
        input.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 18));
        input.setEditable(true);
        input.setVisible(true);
        input.setBorder(new EmptyBorder(10, 10, 10, 10));

        text.add(input);


        window.add(text, BorderLayout.WEST);
        window.add(buttonPanel, BorderLayout.EAST);

        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);

    }
}
