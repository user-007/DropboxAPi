import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropboxUploaderGUI extends JFrame {
    private final JTextField filePathField;
    private final JTextField dropboxFolderField;
    private final JTextArea statusArea;
    private final JButton browseButton;
    private final JButton uploadButton;
    private final DropboxUploaderService dropboxUploaderService;

    public DropboxUploaderGUI() {
        super("Dropbox Uploader");
        dropboxUploaderService = new DropboxUploaderService();


        filePathField = new JTextField(30);
        dropboxFolderField = new JTextField("/DFD Group/Поръчки/5020 Client 13062025", 30);
        statusArea = new JTextArea(10, 30);
        statusArea.setEditable(false);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        browseButton = new JButton("Browse");
        uploadButton = new JButton("Upload");


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Select file:"));
        inputPanel.add(filePathField);
        inputPanel.add(new JLabel("Dropbox folder:"));
        inputPanel.add(dropboxFolderField);
        inputPanel.add(browseButton);
        inputPanel.add(uploadButton);


        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(statusScrollPane, BorderLayout.CENTER);


        setupListeners();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupListeners() {
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        uploadButton.addActionListener(e -> {
            String localFilePath = filePathField.getText();
            String dropboxFolder = dropboxFolderField.getText();

            if (localFilePath.isEmpty()) {
                showError("Please select a file to upload.");
                return;
            }

            uploadButton.setEnabled(false);
            statusArea.setText("Uploading...\n");

            // Загрузка файла в фоновом потоке
            SwingWorker<Void, String> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        String message = dropboxUploaderService.uploadFile(localFilePath, dropboxFolder);
                        publish(message);
                    } catch (Exception ex) {
                        publish("❌ Error during upload: " + ex.getMessage());
                    }
                    return null;
                }

                @Override
                protected void process(java.util.List<String> chunks) {
                    chunks.forEach(statusArea::append);
                }

                @Override
                protected void done() {
                    uploadButton.setEnabled(true);
                }
            };

            worker.execute();
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Точка запуска приложения
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DropboxUploaderGUI::new);
    }
}