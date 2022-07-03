// importing Java AWT class
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

// extending Frame class to our class AWTExample1  
public class Window extends JFrame {
   // initializing using constructor  
   Window() {


       //blank text
        DataMethods.Data data = DataMethods.getData();
        JTextField blankTf;
        blankTf = new JTextField("", 30);


        blankTf.setBounds(30, 260, 200, 30);
        add(blankTf);


       //blocklist pane
       JPanel blocklistPane = new JPanel();
        blocklistPane.setBounds(400, 400, 400, 400);
       blocklistPane.setBackground(new Color(215, 215, 215, 255));
       blocklistPane.setLayout( new GridBagLayout());

        int textY = 0;

        for (String s: data.getBlocklist())
        {
            JPanel blockedPane = new JPanel();

            blockedPane.setBounds(10, textY * 2, 400, 50);
            blockedPane.setBackground(new Color(140, 140, 140, 255));




            JLabel blockedSite = new JLabel(s);


            GridBagConstraints frameConstraints = new GridBagConstraints();

            frameConstraints.gridx = 0;
            frameConstraints.gridy = textY;

            blockedPane.add(blockedSite);
            blocklistPane.add(blockedPane, frameConstraints);



            textY ++;


        }

       add(blocklistPane);



        // setting the title of Frame
        setTitle("This is our basic AWT example");

        // no layout manager
        setLayout(null);

        // now frame will be visible, by default it is not visible
        setVisible(true);
      
      
   }




    // Field members
    static JPanel panel = new JPanel();
    static Integer indexer = 1;
    static ArrayList<JLabel> listOfLabels = new ArrayList<JLabel>();
    static JTextField addTextField;
    static ArrayList<JTextField> listOfTextFields = new ArrayList<JTextField>();

    public static void main(String[] args)
    {
        // Construct frame
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(990, 990));
        frame.setTitle("My Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Frame constraints
        GridBagConstraints frameConstraints = new GridBagConstraints();

        // Construct panel
        panel.setPreferredSize(new Dimension(600, 600));
        panel.setLayout(new GridBagLayout());
        panel.setBorder(LineBorder.createBlackLineBorder());

        // Add panel to frame
        frameConstraints.gridx = 0;
        frameConstraints.gridy = 2;
        frameConstraints.weighty = 1;
        frame.add(panel, frameConstraints);

        //fetch data
        DataMethods.Data data = DataMethods.getData();

        // onOff button



        frameConstraints.gridx = 0;
        frameConstraints.gridy = 0;
        RoundButton onOffButton = new RoundButton("doesn't really matter");
        onOffButton.setPreferredSize(new Dimension(100, 50));
        if(data.on)
        {
            onOffButton.setText("Turn Off");
        }
        else
        {
            onOffButton.setText("Turn On");
        }


        frame.add(onOffButton, frameConstraints);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                DataMethods.Data d = DataMethods.getData();

                System.out.println("WHat is this " + d.on);
                WebsiteBlocker.turn(!d.on);

                if(!d.on)
                {
                    onOffButton.setText("Turn Off");
                }
                else
                {
                    onOffButton.setText("Turn On");
                }


            }

        };
        onOffButton.addActionListener(actionListener);



        frameConstraints.gridx = 0;
        frameConstraints.gridy = 1;
        JPanel addBlockPanel = new JPanel();
        frame.add(addBlockPanel, frameConstraints);


        // Construct button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());

        for (String s: data.getBlocklist())
        {
            addText(s);
        }


        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 0;
        textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        textFieldConstraints.weightx = 0.5;
        textFieldConstraints.insets = new Insets(10, 10, 10, 10);

        addTextField = new JTextField(40);
        addTextField.setSize(100, 200);
        addBlockPanel.add(addTextField, textFieldConstraints);



        // Add button to frame

        frameConstraints.gridx = 1;
        frameConstraints.gridy = 0;
        addBlockPanel.add(addButton, frameConstraints);

        // Pack frame
        frame.pack();

        // Make frame visible
        frame.setVisible(true);
    }

    public static class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);

            // These statements enlarge the button so that it
            // becomes a circle rather than an oval.
            Dimension size = getPreferredSize();
            setPreferredSize(size);

            // This call causes the JButton not to paint
            // the background.
            // This allows us to paint a round background.
            setContentAreaFilled(false);
        }

        // Paint the round background and label.
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                // You might want to make the highlight color
                // a property of the RoundButton class.
                g.setColor(Color.lightGray);
            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getSize().width-1,
                    getSize().height-1,8, 8);

            // This call will paint the label and the
            // focus rectangle.
            super.paintComponent(g);
        }

        // Paint the border of the button using a simple stroke.
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getSize().width-1,
                    getSize().height-1, 8, 8);
        }

        // Hit detection.
        Shape shape;
        public boolean contains(int x, int y) {
            // If the button has changed size,
            // make a new shape object.
            if (shape == null ||
                    !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8);
            }
            return shape.contains(x, y);
        }

    }
    static class AddButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            addText(addTextField.getText());
            DataMethods.addBlock(addTextField.getText());
        }

    }

    public static void addText(String newUrl)
    {
        if(newUrl.length() == 0)
        {
            return;
        }
        // Clear panel
        panel.removeAll();

        // Create label and text field
        JTextField jTextField = new JTextField();
        jTextField.setText(newUrl);
        jTextField.setSize(100, 200);
        listOfTextFields.add(jTextField);
        listOfLabels.add(new JLabel("Label " + indexer));

        // Create constraints
        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        GridBagConstraints labelConstraints = new GridBagConstraints();

        // Add labels and text fields
        for(int i = 0; i < indexer; i++)
        {
            // Text field constraints
            textFieldConstraints.gridx = 1;
            textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
            textFieldConstraints.weightx = 0.5;
            textFieldConstraints.insets = new Insets(10, 10, 10, 10);
            textFieldConstraints.gridy = i;

            // Label constraints
            labelConstraints.gridx = 0;
            labelConstraints.gridy = i;
            labelConstraints.insets = new Insets(10, 10, 10, 10);

            // Add them to panel
            panel.add(listOfLabels.get(i), labelConstraints);
            panel.add(listOfTextFields.get(i), textFieldConstraints);
        }

        // Align components top-to-bottom
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = indexer;
        c.weighty = 1;
        panel.add(new JLabel(), c);

        // Increment indexer
        indexer++;
        panel.updateUI();
    }


}

