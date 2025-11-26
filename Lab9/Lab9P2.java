import java.awt.*;
import javax.swing.*;

class Lab9P2 extends JFrame {
    Lab9P2() {
        super("JTable Demo");

        String[] Heading = {"name", "rollno", "course"};
        String[][] data = {
            {"Teja", "12", "IOT"},
            {"satvendra", "34", "CSD"},
            {"anudeep", "56", "CSM"},
            {"sai", "78", "CSE"},
        };

        JTable jt = new JTable(data, Heading);

       
        JScrollPane sp = new JScrollPane(jt);

        add(sp);

        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String... args) {
        new Lab9P2();
    }
}
