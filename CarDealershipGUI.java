import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarDealershipGUI extends JFrame {
    private DatabaseConnection dbConnection;
    private JTextArea textArea;

    public CarDealershipGUI(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Car Dealership Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        JButton btnModelsByColor = new JButton("List Models by Color");
        JButton btnBrandsByDealer = new JButton("List Brands by Dealer");
        JButton btnBasePrices = new JButton("List Base Prices");

        // Attach listeners to the buttons
        btnModelsByColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModelsByColor();
            }
        });

        btnBrandsByDealer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listBrandsByDealer();
            }
        });

        btnBasePrices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listBasePrices();
            }
        });

        panel.add(btnModelsByColor);
        panel.add(btnBrandsByDealer);
        panel.add(btnBasePrices);

        add(panel, BorderLayout.NORTH);
    }

    private void listModelsByColor() {
    }

    private void listBrandsByDealer() {
    }

    private void listBasePrices() {
    }
}