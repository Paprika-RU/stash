import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class TextEditor extends JFrame {
    private JTextArea textArea; // Поле для отображения и редактирования текста
    private JMenuBar menuBar; // Панель меню
    private JMenu fileMenu; // Меню "Файл"
    private JMenuItem openMenuItem; // Пункт меню "Открыть"
    private JMenuItem saveMenuItem; // Пункт меню "Сохранить"

    public TextEditor() {
        setTitle("Текстовый редактор");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void initComponents() {
        textArea = new JTextArea();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Файл");

        openMenuItem = new JMenuItem("Открыть");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveMenuItem = new JMenuItem("Сохранить");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовые файлы", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileReader fileReader = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                    stringBuffer.append("\n");
                }
                textArea.setText(stringBuffer.toString());
                bufferedReader.close();
            } catch (IOException ex) {
                showMessage("Ошибка", "Ошибка при открытии файла.");
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовые файлы", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(selectedFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(textArea.getText());
                bufferedWriter.close();
            } catch (IOException ex) {
                showMessage("Ошибка", "Ошибка при сохранении файла.");
            }
        }
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor();
            }
        });
    }
}
