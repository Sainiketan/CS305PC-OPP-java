import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

class Medicine {
    String name, dose, colorCode, shape, imagePath;
    int stock, threshold;
    java.util.List<LocalTime> times;

    Medicine(String n, String d, String c, String sh, String img, int s, int th, java.util.List<LocalTime> t) {
        name = n;
        dose = d;
        colorCode = c;
        shape = sh;
        imagePath = img;
        stock = s;
        threshold = th;
        times = t;
    }

    boolean needsRefill() { return stock <= threshold; }
    boolean isLowStock() { return stock <= threshold; }
}

public class MedicineReminderAWT extends Frame implements ActionListener {
    Button addBtn, viewBtn, doseBtn, reminderBtn;
    static java.util.List<Medicine> meds = new ArrayList<>();
    final String ALERT_IMAGE = "remind.png";
    volatile boolean backgroundRunning = true;

    MedicineReminderAWT() {
        setLayout(new GridLayout(4, 1, 10, 10));

        addBtn = new Button("➕ Add Medicine");
        viewBtn = new Button("📋 View Medicines");
        doseBtn = new Button("💊 Take Dose");
        reminderBtn = new Button("⏰ Check Reminders (Now)");

        addBtn.setFont(new Font("Arial", Font.BOLD, 22));
        viewBtn.setFont(new Font("Arial", Font.BOLD, 22));
        doseBtn.setFont(new Font("Arial", Font.BOLD, 22));
        reminderBtn.setFont(new Font("Arial", Font.BOLD, 22));

        add(addBtn); add(viewBtn); add(doseBtn); add(reminderBtn);
        addBtn.addActionListener(this); viewBtn.addActionListener(this);
        doseBtn.addActionListener(this); reminderBtn.addActionListener(this);

        setTitle("Elder-Friendly Medicine Reminder System");
        setSize(500, 450); setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                backgroundRunning = false; dispose(); System.exit(0);
            }
        });

        ensureAlertImageExists();

        Thread bg = new Thread(() -> {
            while (backgroundRunning) {
                checkRemindersBackground();
                try { Thread.sleep(30 * 1000); } catch (Exception e) {}
            }
        });
        bg.setDaemon(true);
        bg.start();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==addBtn) addMedicine();
        if(e.getSource()==viewBtn) viewMedicines();
        if(e.getSource()==doseBtn) takeDose();
        if(e.getSource()==reminderBtn) checkReminders();
    }

    // ---------------- Add Medicine ----------------
    void addMedicine(){
        try{
            String name = JOptionPane.showInputDialog("Medicine Name:");
            if(name==null||name.trim().isEmpty()) return;
            String dose = JOptionPane.showInputDialog("Dose (Ex: 500mg):"); if(dose==null)dose="";
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));
            int threshold = Integer.parseInt(JOptionPane.showInputDialog("Low Stock Threshold:"));
            String color = JOptionPane.showInputDialog("Color Code (Green/Yellow/Red):"); if(color==null)color="";
            String shape = JOptionPane.showInputDialog("Shape (Round/Oval/Capsule):"); if(shape==null)shape="";
            String image = JOptionPane.showInputDialog("Tablet Image Path:"); if(image==null)image="";

            String timeInput = JOptionPane.showInputDialog("Reminder Times (HH:MM,HH:MM):"); 
            if(timeInput==null)timeInput="";
            java.util.List<LocalTime> times = new ArrayList<>();
            for(String t:timeInput.split(",")){
                try{times.add(LocalTime.parse(t.trim()));}catch(Exception ex){}
            }

            meds.add(new Medicine(name,dose,color,shape,image,stock,threshold,times));
            JOptionPane.showMessageDialog(null,"Medicine Added Successfully!");
        }catch(Exception ex){ JOptionPane.showMessageDialog(null,"Invalid Input!"); }
    }

    // ---------------- View Medicines ----------------
    void viewMedicines(){
        if(meds.isEmpty()){ JOptionPane.showMessageDialog(null,"No medicines added!"); return;}
        StringBuilder sb = new StringBuilder();
        int i=1;
        for(Medicine m: meds){
            sb.append(i++ + ". " + m.name + " | Dose: " + m.dose + " | Stock: " + m.stock +
                    " | Color: " + m.colorCode + " | Shape: "+m.shape+" | Times: "+m.times+"\n");
        }
        JOptionPane.showMessageDialog(null,sb.toString());
    }

    // ---------------- Take Dose ----------------
    void takeDose(){
        if(meds.isEmpty()){ JOptionPane.showMessageDialog(null,"No medicines to take."); return;}
        String[] medNames = meds.stream().map(m->m.name).toArray(String[]::new);
        String choice = (String)JOptionPane.showInputDialog(null,"Select Medicine:","Take Dose",
                JOptionPane.QUESTION_MESSAGE,null,medNames,medNames[0]);
        if(choice==null)return;

        for(Medicine m:meds){
            if(m.name.equals(choice)){
                if(m.stock>0){
                    m.stock--;
                    JOptionPane.showMessageDialog(null,"Dose taken!\nRemaining Stock: "+m.stock);
                } else {
                    JOptionPane.showMessageDialog(null,"No stock left! Please refill.");
                }

                if(m.isLowStock()){
                    speak("Low stock alert for " + m.name);
                    JOptionPane.showMessageDialog(null,"⚠ Low Stock for "+m.name);
                }
                break;
            }
        }
    }

    // ---------------- Reminder Check ----------------
    void checkReminders(){
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        for(Medicine m: meds){
            if(m.times.contains(now)){
                triggerAlertFor(m);
            }
        }
    }

    void checkRemindersBackground(){
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        for(Medicine m: meds){
            if(m.times.contains(now)){
                EventQueue.invokeLater(() -> triggerAlertFor(m));
            }
            if(m.isLowStock()){
                EventQueue.invokeLater(() -> {
                    speak("Low stock alert for "+m.name);
                    JOptionPane.showMessageDialog(null,"⚠ Low Stock for "+m.name);
                });
            }
        }
    }

    // ---------------- Trigger Alert ----------------
    void triggerAlertFor(Medicine m){
        speak("It is time to take "+m.name+" dose "+m.dose);
        showTabletAlert(m);
    }

    // ---------------- Voice ----------------
    void speak(String message){
        if(message==null||message.trim().isEmpty()) return;
        try{
            String sanitized = message.replace("'", " ");
            String cmd = "PowerShell -Command \"Add-Type -AssemblyName System.Speech; "+
                    "$speak=New-Object System.Speech.Synthesis.SpeechSynthesizer; "+
                    "$speak.Speak('"+sanitized+"');\"";
            Runtime.getRuntime().exec(cmd);
        }catch(Exception ex){ System.out.println("Voice Error: "+ex);}
    }

    // ---------------- Swing Tablet Alert (Fixed Image Rendering) ----------------
    void showTabletAlert(Medicine m){

        // Load image fully
        BufferedImage tabletImg = null;
        try {
            if (m.imagePath != null && !m.imagePath.isEmpty()) {
                tabletImg = ImageIO.read(new File(m.imagePath));
            }
        } catch (IOException ex) {
            System.out.println("Image load error: " + ex);
        }

        JFrame frame = new JFrame("Medicine Reminder");
        frame.setSize(420, 480);
        frame.setLayout(null);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel info = new JLabel("Take: " + m.name + " (" + m.dose + ")");
        info.setBounds(50, 20, 350, 25);
        info.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel color = new JLabel("Color: " + m.colorCode);
        color.setBounds(50, 50, 300, 25);

        JLabel shape = new JLabel("Shape: " + m.shape);
        shape.setBounds(50, 80, 300, 25);

        BufferedImage finalImg = tabletImg;

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(getColor(m.colorCode));

                switch (m.shape.toLowerCase()) {
                    case "round":
                        g.fillOval(120, 40, 120, 120);
                        break;
                    case "oval":
                        g.fillOval(100, 50, 160, 90);
                        break;
                    case "capsule":
                        g.fillRoundRect(100, 50, 160, 90, 50, 50);
                        break;
                    default:
                        g.fillOval(120, 40, 120, 120);
                }

                if (finalImg != null) {
                    g.drawImage(finalImg, 40, 180, 150, 100, null);
                }
            }
        };

        drawingPanel.setBounds(0, 120, 400, 300);

        JButton ok = new JButton("MARK AS TAKEN");
        ok.setBounds(120, 400, 160, 30);
        ok.addActionListener(e -> frame.dispose());

        frame.add(info);
        frame.add(color);
        frame.add(shape);
        frame.add(drawingPanel);
        frame.add(ok);

        frame.setVisible(true);
    }

    // ---------------- Color Helper ----------------
    Color getColor(String code){
        switch(code.toLowerCase()){
            case "green": return Color.GREEN;
            case "yellow": return Color.YELLOW;
            case "red": return Color.RED;
            default: return Color.GRAY;
        }
    }

    // ---------------- Ensure alert image ----------------
    void ensureAlertImageExists(){
        try{
            File f = new File(ALERT_IMAGE); 
            if(f.exists()) return;

            BufferedImage bi = new BufferedImage(300,300,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bi.createGraphics();
            g.setColor(Color.RED);
            g.fillOval(50,50,200,200);
            g.dispose();
            ImageIO.write(bi,"PNG",f);
        }catch(Exception e){ System.out.println("Alert Image Error: "+e); }
    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> new MedicineReminderAWT());
    }
}
