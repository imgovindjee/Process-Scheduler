import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.List;
import java.util.Objects;

public class JAVA_GUI {
    private JFrame frame;
    private JPanel panel;
    private CustomPanel chartPanel1;
    private JScrollPane tablePanel;
    private JScrollPane chartPanel;
    private JTable table;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton computeBtn;
    private JLabel result;
    private JLabel tatLabel;
    private  JLabel tatResultLabel;
    private  JLabel wtLabel;
    private JLabel wtResultLabel;
    private JComboBox option;
    private DefaultTableModel model;


    public  JAVA_GUI(){
        model = new DefaultTableModel(new String[]{"Process", "AT", "BT", "Priority", "WT", "TAT"}, 0);


        table = new JTable(model);
        table.setFillsViewportHeight(true);
        tablePanel = new JScrollPane(table);
        tablePanel.setBounds(25, 25, 450, 250);


        addBtn = new JButton("Add");
        addBtn.setBounds(300, 280, 85, 25);
        addBtn.setFont(new Font("Segue UI", Font.PLAIN, 11));
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new String[]{"", "", "", "", ""});
            }
        });


        removeBtn = new JButton("Remove");
        removeBtn.setBounds(390, 280, 85, 25);
        removeBtn.setFont(new Font("Segue UI", Font.PLAIN, 11));
        removeBtn.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row > -1){
                    model.removeRow(row);
                }
            }
        }));

        chartPanel1 = new CustomPanel();
        chartPanel1.setBackground(Color.WHITE);
        chartPanel = new JScrollPane(chartPanel1);
        chartPanel.setBounds(25, 310, 450, 100);



        wtLabel = new JLabel("Average Waiting Time");
        wtLabel.setBounds(25, 425, 180, 25);
        wtResultLabel = new JLabel();
        wtResultLabel.setBounds(215, 425, 180, 25);
        tatLabel = new JLabel("Average Turn Around Time");
        tatLabel.setBounds(25, 450, 180, 25);
        tatResultLabel = new JLabel();
        tatResultLabel.setBounds(215, 450, 180, 25);


        option = new JComboBox(new String[]{"FCFS", "SJFS", "SRTF", "PSN", "PSP", "RR"});
        option.setBounds(390, 420, 85, 20);



        computeBtn = new JButton("Compute");
        computeBtn.setBounds(390, 450, 85, 25);
        computeBtn.setFont(new Font("Segue UI", Font.PLAIN, 11));
        computeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) option.getSelectedItem();
                CPUScheduler scheduler;

                switch (Objects.requireNonNull(selected)) {
                    case "FCFS" :
                        scheduler = new FirstComeFirstServe();
                        break;
                    case "SJFS" :
                        scheduler = new ShortestJobFirstServe();
                        break;
                    case "SRTF" :
                        scheduler = new ShortestRemainingTimeFirst();
                        break;
                    case "PSN" :
                        scheduler = new PriorityNonPreemptiveScheduling();
                        break;
                    case "PSP" :
                        scheduler = new PriorityPreemptiveScheduling();
                        break;
                    case "RR" :
                        String quantum_time = JOptionPane.showInputDialog("Time Quantum");
                        if(quantum_time == null){
                            return;
                        }
                        scheduler = new RoundRobin();
                        scheduler.setTimeQuantum(Integer.parseInt(quantum_time));
                        break;
                    default:
                        return;
                }

                for (int i=0; i<model.getRowCount(); i++){
                    String process = (String) model.getValueAt(i, 0);
                    int arrival_time = Integer.parseInt((String) model.getValueAt(i, 1));
                    int burst_time = Integer.parseInt((String) model.getValueAt(i, 2));
                    int priority_level;

                    if(selected.equals("PSN") || selected.equals("PSP")){
                        if(!model.getValueAt(i, 3).equals("")){
                            priority_level = Integer.parseInt((String) model.getValueAt(i, 3));
                        } else{
                            priority_level = 1;
                        }
                    } else {
                        priority_level = 1;
                    }
                    scheduler.add(new Row(process, arrival_time, burst_time, priority_level));
                }

                scheduler.process();

                for(int i=0; i<model.getRowCount(); i++){
                    String process = (String) model.getValueAt(i, 0);
                    Row row = scheduler.getRow(process);
                    model.setValueAt(row.getWaitingTime(), i, 4);
                    model.setValueAt(row.getTurnAroundTime(), i, 5);
                }
                wtResultLabel.setText(Double.toString((scheduler.getAverageWaitingTime())));
                tatResultLabel.setText(Double.toString(scheduler.getAverageTurnAroundTime()));
                chartPanel1.setTimeline(scheduler.getTimeline());
            }
        });

        JLabel jLabel1 = new JLabel();
        jLabel1.setText("This Software is Completely free, and used to visualize the OS Process Scheduler's.");
        jLabel1.setFont(new Font("Segoe UI", 0, 10));
        jLabel1.setBounds(100, 480, 390, 10);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("MadeBy@ShreeGovindJee ");
        jLabel2.setFont(new Font("Segoe UI", 0, 10));
        jLabel2.setBounds(190, 490, 150, 15);

        JLabel jlabel3 = new JLabel();
        jlabel3.setFont(new Font("Segoe UI", 0, 10));
        jlabel3.setForeground(new Color(0, 51, 204));
        jlabel3.setText("https://imgovindjee.github.io/site/");
        jlabel3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jlabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jlabel3MouseClicked(e);
            }
        });
        jlabel3.setBounds(315, 490, 200, 15);

        panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(500, 520));
        panel.add(tablePanel);
        panel.add(addBtn);
        panel.add(removeBtn);
        panel.add(chartPanel);
        panel.add(wtLabel);
        panel.add(wtResultLabel);
        panel.add(tatLabel);
        panel.add(tatResultLabel);
        panel.add(option);
        panel.add(computeBtn);
        panel.add(jLabel1);
        panel.add(jLabel2);
        panel.add(jlabel3);

        frame = new JFrame("CPU Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
    }

    private void jlabel3MouseClicked(MouseEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://imgovindjee.github.io/site/"));
        } catch (Exception exception) {
            System.out.println("[JAVA GUI] Error encountered while processing the Developer Site link.");
            exception.printStackTrace(); // FOR ERROR VISUALIZATION
        }
    }


    //    Customize the Panel
    class CustomPanel extends JPanel{
        private List<Event> timeline;

        @Override
    protected void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            if(timeline != null){
                int width = 30;
                for(int i=0; i<timeline.size(); i++){
                    Event e = timeline.get(i);
                    int x = 30*(i+1);
                    int y = 20;

                    graphics.drawRect(x, y, 30, 30);
                    graphics.setFont(new Font("Segue UI", Font.BOLD, 14));
                    graphics.drawString(e.getProcessName(), x+10, y+20);
                    graphics.setFont(new Font("Segue UI", Font.PLAIN, 11));
                    graphics.drawString(Integer.toString(e.getStartTime()), x-5, y+45);

                    if(i == timeline.size() - 1){
                        graphics.drawString(Integer.toString(e.getFinishTime()), x+27, y+45);
                    }
                    width += 30;
                }
                this.setPreferredSize(new Dimension(width, 75));
            }
        }

        public void setTimeline(List<Event> timeline){
            this.timeline = timeline;
            repaint();
        }
    }





//    Drive CODE
    public static void  main(String[] args) {
        new JAVA_GUI();
    }
}
