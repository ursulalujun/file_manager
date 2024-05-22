import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FileManagerApp extends JFrame implements ActionListener{
    JPanel jp;
    JLabel jl;
    JButton backup;
    JButton restore;
    JButton exit;

    public static void main(String[] args){
        new FileManagerApp();
    }


    FileManagerApp() {
        ImageIcon ic = new ImageIcon("./image/icon.png");
        jl=new JLabel(ic);
        jp=new JPanel();
        backup=new JButton("备份(B)");
        // 设置助记符:使用alt+B可以激活相应的事件
        backup.setMnemonic('B');
        restore=new JButton("恢复(R)");
        backup.setMnemonic('R');
        exit = new JButton("退出(X)");
        exit.setMnemonic('X');
        backup.setActionCommand("backup");
        backup.addActionListener(this);
        restore.setActionCommand("restore");
        restore.addActionListener(this);
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        jp.setLayout(new FlowLayout());
        jp.add(backup);
        jp.add(restore);
        jp.add(exit);
        this.add(jl, BorderLayout.NORTH);
        this.add(jp, BorderLayout.SOUTH);
        this.setTitle("文件备份助手");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBounds(600, 250, 700, 350);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("backup")){
            new BackUpDialogue();
        }
        if (e.getActionCommand().equals("restore")){
            new RecoverFileDialogue();
        }
        if (e.getActionCommand().equals("exit")){
            System.exit(0);
        }
    }
}


