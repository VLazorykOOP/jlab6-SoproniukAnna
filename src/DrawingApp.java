import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingApp extends JFrame {
    private JColorChooser colorChooser;
    private int lineWidth = 2; // Товщина лінії за замовчуванням
    private Color currentColor = Color.BLACK; // Колір за замовчуванням
    private boolean drawing = false;
    private int prevX, prevY;

    public DrawingApp() {
        setTitle("Програма для малювання пером");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Створення області для малювання
        JPanel drawingPanel = new JPanel();
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawing = true;
                prevX = e.getX();
                prevY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drawing = false;
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing) {
                    Graphics g = drawingPanel.getGraphics();
                    g.setColor(currentColor);
                    g.drawLine(prevX, prevY, e.getX(), e.getY());
                    prevX = e.getX();
                    prevY = e.getY();
                }
            }
        });

        // Створення меню для вибору кольору і товщини лінії
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Опції");

        JMenuItem colorMenuItem = new JMenuItem("Вибрати колір");
        colorMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = JColorChooser.showDialog(colorChooser, "Вибрати колір", currentColor);
            }
        });

        JMenuItem lineWidthMenuItem = new JMenuItem("Товщина лінії");
        lineWidthMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = JOptionPane.showInputDialog("Введіть товщину лінії:");
                    if (input != null) {
                        lineWidth = Integer.parseInt(input);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Неправильний формат товщини лінії.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        optionsMenu.add(colorMenuItem);
        optionsMenu.add(lineWidthMenuItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);

        // Додавання області для малювання до фрейму
        getContentPane().add(drawingPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DrawingApp();
            }
        });
    }
}
