import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CopyFileApp extends JFrame implements ActionListener{
    JPanel jp=null;
    JLabel jl=null;
    JButton backup=null;
    JButton restore=null;
    JButton exit=null;

    // 备份页面
    /*
    JMenu file = null;
    JMenuItem open = null;
    JMenuItem save = null;
    JMenuItem exit = null;
     */

    public static void main(String[] args){
        new CopyFileApp();
    }


    CopyFileApp() {
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
            BackUpApp bApp=new BackUpApp();
        }
        if (e.getActionCommand().equals("restore")){
            RecoverFileApp rApp = new RecoverFileApp();
        }
        if (e.getActionCommand().equals("exit")){
            System.exit(0);
        }
    }

}

class BackUpApp extends JFrame implements ActionListener{
    JPanel jp1, jp2, jp3, jp4, jp5;
    JLabel jl1, jl2, jl3, jl4, jl5;
    JButton jsrc, jdes, jok;

    String srcPath, destPath;

    BackUpApp(){
        super("文件备份");

        jp1 = new JPanel();
        jp1.setLayout(new FlowLayout());
        jl1 = new JLabel("输入待备份的文件路径");
        jsrc = new JButton("浏览");
        jsrc.setActionCommand("chooseSrc");
        jsrc.addActionListener(this);
        jp1.add(jl1);
        jp1.add(jsrc);

        jp2 = new JPanel();
        jp2.setLayout(new FlowLayout());
        jl2 = new JLabel("输入备份的目标路径");
        jdes = new JButton("浏览");
        jdes.setActionCommand("chooseDest");
        jdes.addActionListener(this);
        jp2.add(jl2);
        jp2.add(jdes);

        jp3 = new JPanel();
        jp3.setLayout(new FlowLayout());
        jok = new JButton("确定");
        jok.setActionCommand("OK");
        jok.addActionListener(this);
        jp3.add(jok);

        this.setLayout(new GridLayout(3, 1));
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);

        this.setLocation(200, 200);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
    }

    public void diyCopy(String srcPath,String destPath) {
        try {
            File file=new File(srcPath);
            if(file.isDirectory()){
                new FileDiyCopy(srcPath, destPath);
            }
            else {
                File f=new File(destPath);
                if(!f.exists()){
                    f.createNewFile();
                }
                FileDiyCopy.copyFile(srcPath,destPath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("chooseSrc")){
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("./test"));
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.setDialogTitle("备份文件选择");
            int ret = jfc.showOpenDialog(null);
            if(ret != JFileChooser.APPROVE_OPTION){
                return;
            }
            srcPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(srcPath);
        }
        else if (e.getActionCommand().equals("chooseDest")){
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setCurrentDirectory(new File("./test"));
            jfc.setDialogTitle("目标路径选择");
            int ret = jfc.showOpenDialog(null);
            if(ret != JFileChooser.APPROVE_OPTION){
                return;
            }
            destPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(destPath);
        }
        else if (e.getActionCommand().equals("OK")) {
            diyCopy(srcPath, destPath);
        }
    }
}

class RecoverFileApp extends JFrame implements ActionListener{
    JPanel jp1, jp2, jp3;
    JLabel jl1, jl2;
    JButton jsrc, jdes, jok;

    String srcPath, destPath;

    RecoverFileApp() {
        jp1 = new JPanel();
        jp1.setLayout(new FlowLayout());
        jl1 = new JLabel("选择待还原的文件路径");
        jsrc = new JButton("浏览");
        jsrc.setActionCommand("chooseSrc");
        jsrc.addActionListener(this);
        jp1.add(jl1);
        jp1.add(jsrc);

        jp2 = new JPanel();
        jp2.setLayout(new FlowLayout());
        jl2 = new JLabel("选择还原的目标路径");
        jdes = new JButton("浏览");
        jdes.setActionCommand("chooseDest");
        jdes.addActionListener(this);
        jp2.add(jl2);
        jp2.add(jdes);

        jp3 = new JPanel();
        jp3.setLayout(new FlowLayout());
        jok = new JButton("确定");
        jok.setActionCommand("OK");
        jok.addActionListener(this);
        jp3.add(jok);

        this.setLayout(new GridLayout(3, 1));
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);

        this.setLocation(200, 200);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("chooseSrc")){
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setCurrentDirectory(new File("./test"));
            jfc.setDialogTitle("恢复文件选择");
            jfc.showSaveDialog(null);
            jfc.setVisible(true);
            srcPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(srcPath);
        }
        else if (e.getActionCommand().equals("chooseDest")){
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setCurrentDirectory(new File("./test"));
            jfc.setDialogTitle("目标路径选择");
            int ret = jfc.showOpenDialog(null);
            if(ret != JFileChooser.APPROVE_OPTION){
                return;
            }
            destPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(destPath);
        }
    }
}

