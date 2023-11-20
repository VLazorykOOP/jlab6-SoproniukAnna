import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MatrixGUI extends JFrame
{

    private JTextField nField;
    private JTextArea matrixArea;
    private JTextArea resultArea;

    public MatrixGUI() {
        setTitle("Matrix Operations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));

        nField = new JTextField();
        JButton loadFileButton = new JButton("Load Matrix from File");
        matrixArea = new JTextArea();
        resultArea = new JTextArea();

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadMatrixFromFile();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(MatrixGUI.this, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MatrixGUI.this, "Invalid input format!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (CustomMatrixException ex) {
                    JOptionPane.showMessageDialog(MatrixGUI.this, "Custom Matrix Exception: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(new JLabel("Enter matrix size (n):"));
        mainPanel.add(nField);
        mainPanel.add(loadFileButton);
        mainPanel.add(new JScrollPane(matrixArea));
        mainPanel.add(new JLabel("Result:"));
        mainPanel.add(new JScrollPane(resultArea));

        add(mainPanel);
    }

    private void loadMatrixFromFile() throws FileNotFoundException, NumberFormatException, CustomMatrixException {
        int n = Integer.parseInt(nField.getText());
        int[][] x = new int[n][n];
        boolean[] y = new boolean[n];

        Arrays.fill(y, true);

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    x[i][j] = scanner.nextInt();
            }

            scanner.close();

            matrixArea.setText("Matrix loaded from file:\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    matrixArea.append(x[i][j] + "\t");
                matrixArea.append("\n");
            }

            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    for (int k = j + 1; y[i] && k < n; k++)
                        if (x[i][j] == x[i][k])
                            y[i] = false;

            resultArea.setText("Result:\n");
            for (boolean value : y)
                resultArea.append(value + "\t");
        } else {
            throw new FileNotFoundException("No file selected");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MatrixGUI matrixGUI = new MatrixGUI();
            matrixGUI.setVisible(true);
        });
    }
}

class CustomMatrixException extends ArithmeticException {
    public CustomMatrixException(String message) {
        super(message);
    }
}
