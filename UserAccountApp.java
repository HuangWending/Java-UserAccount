import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class UserAccountApp {
    private JFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberPasswordCheckBox;

    private JFrame registerFrame;
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JTextField birthdayField;
    private JTextField nameField;
    private JTextArea introTextArea;
    private JTextField countryField;
    private JTextField educationField;
    private JTextField websiteField;
    private JTextField socialMediaField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    private JFrame accountFrame;
    private JTextArea accountInfoTextArea;

    private File dataFile;

    public UserAccountApp() {
        createMainFrame();
        createRegisterFrame();
        createAccountFrame();
        dataFile = new File("data.txt");
    }

    private void createMainFrame() {
        mainFrame = new JFrame("登录");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("用户名:");
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("密码:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        rememberPasswordCheckBox = new JCheckBox("记住密码");
        panel.add(rememberPasswordCheckBox);

        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateUser(username, password)) {
                    showAccountFrame(username);
                    mainFrame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "登录失败，请检查用户名和密码。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("注册账户");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterFrame();
                mainFrame.setVisible(false);
            }
        });
        panel.add(registerButton);

        JButton forgotPasswordButton = new JButton("忘记密码");
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "无法找到密码，请重新注册账户。", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(forgotPasswordButton);

        mainFrame.getContentPane().add(panel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createRegisterFrame() {
        registerFrame = new JFrame("注册账户");
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        JLabel usernameLabel = new JLabel("用户名:");
        panel.add(usernameLabel);

        regUsernameField = new JTextField(20);
        panel.add(regUsernameField);

        JLabel passwordLabel = new JLabel("密码:");
        panel.add(passwordLabel);

        regPasswordField = new JPasswordField(20);
        panel.add(regPasswordField);

        JLabel birthdayLabel = new JLabel("生日:");
        panel.add(birthdayLabel);

        birthdayField = new JTextField(20);
        panel.add(birthdayField);

        JLabel nameLabel = new JLabel("姓名:");
        panel.add(nameLabel);

        nameField = new JTextField(20);
        panel.add(nameField);

        JLabel introLabel = new JLabel("介绍:");
        panel.add(introLabel);

        introTextArea = new JTextArea(5, 20);
        JScrollPane introScrollPane = new JScrollPane(introTextArea);
        panel.add(introScrollPane);

        JLabel countryLabel = new JLabel("国家/地区:");
        panel.add(countryLabel);

        countryField = new JTextField(20);
        panel.add(countryField);

        JLabel educationLabel = new JLabel("教育经历:");
        panel.add(educationLabel);

        educationField = new JTextField(20);
        panel.add(educationField);

        JLabel websiteLabel = new JLabel("个人网站:");
        panel.add(websiteLabel);

        websiteField = new JTextField(20);
        panel.add(websiteField);

        JLabel socialMediaLabel = new JLabel("社交媒体:");
        panel.add(socialMediaLabel);

        socialMediaField = new JTextField(20);
        panel.add(socialMediaField);

        JLabel genderLabel = new JLabel("性别:");
        panel.add(genderLabel);

        ButtonGroup genderButtonGroup = new ButtonGroup();
        maleRadioButton = new JRadioButton("男");
        femaleRadioButton = new JRadioButton("女");
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        panel.add(genderPanel);

        JButton registerButton = new JButton("确定");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = regUsernameField.getText();
                String password = new String(regPasswordField.getPassword());
                String birthday = birthdayField.getText();
                String name = nameField.getText();
                String intro = introTextArea.getText();
                String country = countryField.getText();
                String education = educationField.getText();
                String website = websiteField.getText();
                String socialMedia = socialMediaField.getText();
                String gender = maleRadioButton.isSelected() ? "男" : "女";

                if (registerUser(username, password, birthday, name, intro, country, education, website, socialMedia, gender)) {
                    JOptionPane.showMessageDialog(registerFrame, "账户创建成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    registerFrame.setVisible(false);
                    mainFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "无法创建账户，请重试。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(registerButton);

        registerFrame.getContentPane().add(panel);
        registerFrame.pack();
    }

    private boolean registerUser(String username, String password, String birthday, String name, String intro,
                                 String country, String education, String website,
                                 String socialMedia, String gender) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true));
            writer.write(username + "," + password + "," + birthday + "," + name + "," + intro + "," +
                    country + "," + education + "," + website + "," + socialMedia + "," + gender);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createAccountFrame() {
        accountFrame = new JFrame("账户信息");
        accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        accountInfoTextArea = new JTextArea(10, 30);
        accountInfoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(accountInfoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(true);
                accountFrame.setVisible(false);
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        accountFrame.getContentPane().add(panel);
        accountFrame.pack();
    }

    private void showRegisterFrame() {
        clearRegisterFrame();
        registerFrame.setVisible(true);
    }

    private void clearRegisterFrame() {
        regUsernameField.setText("");
        regPasswordField.setText("");
        birthdayField.setText("");
        nameField.setText("");
        introTextArea.setText("");
        countryField.setText("");
        educationField.setText("");
        websiteField.setText("");
        socialMediaField.setText("");
        maleRadioButton.setSelected(true);
    }

    private void showAccountFrame(String username) {
        String accountInfo = getAccountInfo(username);
        accountInfoTextArea.setText(accountInfo);
        accountFrame.setVisible(true);
    }

    private String getAccountInfo(String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    reader.close();
                    return "用户名: " + parts[0] + "\n" +
                            "生日: " + parts[2] + "\n" +
                            "姓名: " + parts[3] + "\n" +
                            "介绍: " + parts[4] + "\n" +
                            "国家/地区: " + parts[5] + "\n" +
                            "教育经历: " + parts[6] + "\n" +
                            "个人网站: " + parts[7] + "\n" +
                            "社交媒体: " + parts[8] + "\n" +
                            "性别: " + parts[9];
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UserAccountApp();
            }
        });
    }
}
