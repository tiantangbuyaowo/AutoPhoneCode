package org.tj.code.ui;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

/*import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;*/

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 6636098005177941822L;
    private JPanel contentPane;
    private JTextField txt_username;
    private JTextField txt_password;
    private JTextField txt_tableprefix;
    private final String[] drivers = {"com.mysql.jdbc.Driver", "oracle.jdbc.dirver.OracleDriver"};
    private String[] keyTypeText = {"UUID", "自增"};
    private JTextField txt_url;
    private String keyType = "01";//FtlDef.KEY_TYPE_01;
    /**
     * UIManager中UI字体相关的key
     */
    public static String[] DEFAULT_FONT = new String[]{"Table.font", "TableHeader.font", "CheckBox.font", "Tree.font",
            "Viewport.font", "ProgressBar.font", "RadioButtonMenuItem.font", "ToolBar.font", "ColorChooser.font",
            "ToggleButton.font", "Panel.font", "TextArea.font", "Menu.font", "TableHeader.font", "TextField.font",
            "OptionPane.font", "MenuBar.font", "Button.font", "Label.font", "PasswordField.font", "ScrollPane.font",
            "MenuItem.font", "ToolTip.font", "List.font", "EditorPane.font", "Table.font", "TabbedPane.font",
            "RadioButton.font", "CheckBoxMenuItem.font", "TextPane.font", "PopupMenu.font", "TitledBorder.font",
            "ComboBox.font"};
    private final Object[] options = {"确定 ", " 取消 "};
    private JTextField txt_author;
    private JTextField txt_contact;
    private JComboBox txt_dbtype;
    private JTextField txt_package;
    private JTextField txt_outdir;
    private JFileChooser chooser;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                try {
                    org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                    UIManager.put( "RootPane.setupButtonVisible", false );
                    // 设置本属性将改变窗口边框样式定义
                    BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
                    org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                    // 调整默认字体
                    for (int i = 0; i < DEFAULT_FONT.length; i++) {
                        UIManager.put( DEFAULT_FONT[i], new Font( "微软雅黑", Font.PLAIN, 13 ) );
                    }
                    MainFrame frame = new MainFrame();
                    frame.setVisible( true );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        init();
    }

    /**
     * 初始化布局
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    void init() {
        setTitle( "酷云科技代码生成器" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 377, 490 );
        contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        contentPane.setLayout( null );
        @SuppressWarnings({"rawtypes", "unchecked"})
        final JComboBox comboBox_driver = new JComboBox( drivers );
        comboBox_driver.setBounds( 110, 30, 205, 25 );
        contentPane.add( comboBox_driver );
        JLabel label = new JLabel( "数据库连接：" );
        label.setHorizontalAlignment( SwingConstants.RIGHT );
        label.setBounds( 10, 35, 90, 15 );
        contentPane.add( label );
        JLabel lblUsername = new JLabel( "用户名：" );
        lblUsername.setHorizontalAlignment( SwingConstants.RIGHT );
        lblUsername.setVerticalAlignment( SwingConstants.BOTTOM );
        lblUsername.setBounds( 10, 75, 90, 15 );
        contentPane.add( lblUsername );
        JLabel lblPassword = new JLabel( "登录密码：" );
        lblPassword.setVerticalAlignment( SwingConstants.BOTTOM );
        lblPassword.setHorizontalAlignment( SwingConstants.RIGHT );
        lblPassword.setBounds( 10, 115, 90, 15 );
        contentPane.add( lblPassword );
        txt_username = new JTextField();
        txt_username.setText( "root" );
        txt_username.setBounds( 110, 70, 205, 25 );
        contentPane.add( txt_username );
        txt_username.setColumns( 10 );
        txt_password = new JTextField();
        txt_password.setText( "123" );
        txt_password.setColumns( 10 );
        txt_password.setBounds( 110, 110, 205, 25 );
        contentPane.add( txt_password );
        JButton btn_test = new JButton( "测试连接" );
        btn_test.setUI( new BEButtonUI().setNormalColor( BEButtonUI.NormalColor.green ) );
        btn_test.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validation()) {
                    String classDriver = comboBox_driver.getSelectedItem().toString();
                    String url = txt_url.getText();
                    String username = txt_username.getText();
                    String password = txt_password.getText();
                    Connection conn = null;//new DatabaseInfoOp(classDriver, url, username, password).getConnectionByJDBC();
                    if (null != conn) {
                        showInfo( "连接成功！" );
                    } else {
                        showError( "数据库连接失败，请检查后重试！" );
                    }
                }
            }
        } );
        btn_test.setBounds( 55, 415, 95, 25 );
        contentPane.add( btn_test );
        JButton btn_gener = new JButton( "生成代码" );
        btn_gener.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validation()) {
                    String classDriver = comboBox_driver.getSelectedItem().toString();
                    String url = txt_url.getText();
                    String username = txt_username.getText();
                    String password = txt_password.getText();
                    Connection conn = null;//new DatabaseInfoOp(classDriver, url, username, password).getConnectionByJDBC();
                    if (null == conn) {
                        showError( "数据库连接失败，请检查后重试！" );
                        return;
                    }
                    /*CodeResourceUtil.DIVER_NAME = comboBox_driver.getSelectedItem().toString();
					CodeResourceUtil.URL = txt_url.getText();// url
					CodeResourceUtil.USERNAME = txt_username.getText();// 数据库账号
					CodeResourceUtil.PASSWORD = txt_password.getText();// 数据库密码
*/
                    String classPackage = txt_package.getText();// 包名
                    String codeName = txt_author.getText();// 作者
                    String outPath = txt_outdir.getText() + File.separator + "code";// 生成目录
                    String tableName = txt_tableprefix.getText();// 数据库表名
                    File file = new File( outPath ); // 如果存在则删除；否则创建
                    if (!file.exists()) {
                        file.mkdirs();
                    } else {
                        String[] children = file.list();
                        for (int i = 0; i < children.length; i++) {
                            new File( file, children[i] ).delete();
                        }
                    }
                    /** 此处修改成你的 表名 和 作者 ***/
                    //CodeGenerateFactory.codeGenerate(tableName, codeName, classPackage, keyType, outPath, false);
                    int response = JOptionPane.showOptionDialog( MainFrame.this, "代码已经生成，是否打开输出目录？", "确认",
                            JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0] );
                    if (response == 0) {
                        // 打开文件夹
                        try {
                            Runtime.getRuntime().exec( "cmd.exe /c start " + outPath );
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    // }
                }
            }
        } );
        btn_gener.setUI( new BEButtonUI().setNormalColor( BEButtonUI.NormalColor.blue ) );
        btn_gener.setBounds( 220, 415, 95, 25 );
        contentPane.add( btn_gener );
        txt_tableprefix = new JTextField();
        txt_tableprefix.setText( "q_glue" );
        txt_tableprefix.setColumns( 10 );
        txt_tableprefix.setBounds( 110, 190, 205, 25 );
        contentPane.add( txt_tableprefix );
        JLabel label_1 = new JLabel( "数据表名：" );
        label_1.setVerticalAlignment( SwingConstants.BOTTOM );
        label_1.setHorizontalAlignment( SwingConstants.RIGHT );
        label_1.setBounds( 10, 195, 90, 15 );
        contentPane.add( label_1 );
        JLabel lblUrl = new JLabel( "数据库URL：" );
        lblUrl.setVerticalAlignment( SwingConstants.BOTTOM );
        lblUrl.setHorizontalAlignment( SwingConstants.RIGHT );
        lblUrl.setBounds( 10, 155, 90, 15 );
        contentPane.add( lblUrl );
        txt_url = new JTextField();
        txt_url.setText( "jdbc:mysql://localhost:3306/A_PCS_OS" );
        txt_url.setColumns( 10 );
        txt_url.setBounds( 110, 150, 205, 25 );
        contentPane.add( txt_url );
        JLabel label_2 = new JLabel( "作者：" );
        label_2.setVerticalAlignment( SwingConstants.BOTTOM );
        label_2.setHorizontalAlignment( SwingConstants.RIGHT );
        label_2.setBounds( 10, 299, 90, 15 );
        contentPane.add( label_2 );
        txt_author = new JTextField();
        txt_author.setText( "唐靖" );
        txt_author.setColumns( 10 );
        txt_author.setBounds( 110, 294, 205, 25 );
        contentPane.add( txt_author );
        txt_contact = new JTextField();
        txt_contact.setText( "QQ:954218947" );
        // txt_contact.setEditable(false);
        txt_contact.setColumns( 10 );
        txt_contact.setBounds( 110, 334, 205, 25 );
        contentPane.add( txt_contact );
        JLabel label_3 = new JLabel( "联系方式：" );
        label_3.setVerticalAlignment( SwingConstants.BOTTOM );
        label_3.setHorizontalAlignment( SwingConstants.RIGHT );
        label_3.setBounds( 10, 339, 90, 15 );
        contentPane.add( label_3 );
        txt_package = new JTextField();
        txt_package.setText( "center" );
        txt_package.setColumns( 10 );
        txt_package.setBounds( 110, 225, 205, 25 );
        contentPane.add( txt_package );
        JLabel label_4 = new JLabel( "实体包：" );
        label_4.setVerticalAlignment( SwingConstants.BOTTOM );
        label_4.setHorizontalAlignment( SwingConstants.RIGHT );
        label_4.setBounds( 10, 230, 90, 15 );
        contentPane.add( label_4 );
        JLabel label_6 = new JLabel( "主键策略：" );
        label_6.setVerticalAlignment( SwingConstants.BOTTOM );
        label_6.setHorizontalAlignment( SwingConstants.RIGHT );
        label_6.setBounds( 10, 265, 90, 15 );
        contentPane.add( label_6 );
        txt_dbtype = new JComboBox( keyTypeText );
        txt_dbtype.setBounds( 110, 260, 205, 25 );
        contentPane.add( txt_dbtype );
        JLabel label_5 = new JLabel( "生成目录：" );
        label_5.setVerticalAlignment( SwingConstants.BOTTOM );
        label_5.setHorizontalAlignment( SwingConstants.RIGHT );
        label_5.setBounds( 10, 374, 90, 15 );
        contentPane.add( label_5 );
        txt_outdir = new JTextField();
        txt_outdir.setText( "D:/exemple" );
        txt_outdir.setColumns( 10 );
        txt_outdir.setBounds( 110, 369, 150, 25 );
        contentPane.add( txt_outdir );
        JButton btn_browse = new JButton( "浏览" );
        btn_browse.setUI( new BEButtonUI().setNormalColor( BEButtonUI.NormalColor.green ) );
        btn_browse.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
                int intRetVal = chooser.showOpenDialog( null );
                if (intRetVal == JFileChooser.APPROVE_OPTION) {
                    txt_outdir.setText( chooser.getSelectedFile().getPath() );
                }
            }
        } );
        btn_browse.setBounds( 270, 369, 50, 25 );
        contentPane.add( btn_browse );
        setResizable( false );
        setLocationRelativeTo( null );
        txt_dbtype.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    String s = evt.getItem().toString();// 选中的值
                    if (s.equals( keyTypeText[0] )) {
                        //keyType = FtlDef.KEY_TYPE_01;
                    } else {
                        //keyType = FtlDef.KEY_TYPE_02;
                    }
                }
            }
        } );
    }

    /**
     * 验证是否为空
     *
     * @return
     */
    public boolean validation() {
        if (isEmpty( txt_username )) {
            showWarning( "username不能为空！" );
            txt_username.requestFocus();
            return false;
        }
        if (isEmpty( txt_password )) {
            showWarning( "password不能为空！" );
            txt_password.requestFocus();
            return false;
        }
        if (isEmpty( txt_url )) {
            showWarning( "url不能为空！" );
            txt_url.requestFocus();
            return false;
        }
        if (isEmpty( txt_tableprefix )) {
            showWarning( "表名不能为空！" );
            txt_package.requestFocus();
            return false;
        }
        if (isEmpty( txt_package )) {
            showWarning( "实体包不能为空！" );
            txt_package.requestFocus();
            return false;
        }
        if (isEmpty( txt_outdir )) {
            showWarning( "生成代码目录不能为空！" );
            txt_outdir.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 判断输入框是否为空
     *
     * @param component
     * @return
     */
    public boolean isEmpty(JTextComponent component) {
        String content = component.getText();
        return null == content || content.equals( "" );
    }

    /**
     * 错误提示
     *
     * @param title
     * @param msg
     */
    public void showError(String msg) {
        JOptionPane.showMessageDialog( null, msg, "错误", JOptionPane.ERROR_MESSAGE );
    }

    /**
     * 警告提示
     *
     * @param msg
     */
    public void showWarning(String msg) {
        JOptionPane.showMessageDialog( null, msg, "警告", JOptionPane.WARNING_MESSAGE );
    }

    /**
     * 信息提示
     *
     * @param title
     * @param msg
     */
    public void showInfo(String msg) {
        JOptionPane.showMessageDialog( null, msg, "信息", JOptionPane.INFORMATION_MESSAGE );
    }
}
