//import java.io.File;
//import java.io.IOException;
//
//public class FileCopy {
//    public void copy(String srcPath,String dstPath,String extension, String name, String date, String size)
//            throws IOException {
//        try {
//            File file=new File(srcPath);
//            if(file.isDirectory()){
//                FileCopyUtils.copyDir(srcPath,dstPath);
//            }
//            else {
//                File f=new File(dstPath);
//                if(!f.exists()){
//                    f.createNewFile();
//                }
//                FileCopyUtils.copyFile(srcPath,dstPath);
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void compress(String srcPath,String dstPath){
//        try {
//            FileZipUtil.compressFile(srcPath,dstPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void unzip(String srcPath, String dstPath) throws Exception {
//        try {
//            UnZipUtil.unzip(srcPath,dstPath);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//
//    public void encrypy(String srcPath,String dstPath,int key) throws IOException {
//            try {
//                File file=new File(srcPath);
//                if(file.isDirectory()){
//                    FileEncrypyUtils.search(srcPath,dstPath, key);
//                }
//                else {
//                    File f=new File(dstPath);
//                    if(!f.exists()){
//                        f.createNewFile();
//                    }
//                    FileEncrypyUtils.encrypy(srcPath,dstPath,key);
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//    }
//
//    public void decrypy(String srcPath,String dstPath,int key) throws IOException {
//        FileEncrypyUtils.decrypy(srcPath,dstPath,key);
//    }
//
//    public void varify(String firstpath,String secondpath) throws IOException {
//        varifyUtils first=new varifyUtils();
//        varifyUtils second=new varifyUtils();
//        first.search(firstpath,firstpath);
//        second.search(secondpath,secondpath);
//
////        System.out.println(first.map);
////        System.out.println(second.map);
////        if (first.map.equals(second.map)){
////            System.out.println(true);
////        }else {
////            System.out.println(false);
////        }
//    }
//}
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class RecoverFileDialogue extends JFrame implements ActionListener {
    JPanel jp1, jp2, jp3, jp4;
    JLabel jl1, jl2, jl3;
    JButton jsrc, jdes, jok;
    JTextField inputKey;
    String srcPath, destPath;
    int key;

    RecoverFileDialogue() {
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
        jl3 = new JLabel("请输入解密密钥（1-6位的整数）：");
        inputKey = new JTextField(5);
        jp3.add(jl3);
        jp3.add(inputKey);

        jp4 = new JPanel();
        jp4.setLayout(new FlowLayout());
        jok = new JButton("确定");
        jok.setActionCommand("OK");
        jok.addActionListener(this);
        jp4.add(jok);

        this.setLayout(new GridLayout(4, 1));
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);

        this.setLocation(200, 200);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("chooseSrc")) {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setCurrentDirectory(new File("./test"));
            jfc.setDialogTitle("恢复文件选择");
            jfc.showSaveDialog(null);
            jfc.setVisible(true);
            srcPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(srcPath);
        } else if (e.getActionCommand().equals("chooseDest")) {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setCurrentDirectory(new File("./test"));
            jfc.setDialogTitle("目标路径选择");
            int ret = jfc.showOpenDialog(null);
            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }
            destPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(destPath);
        } else if (e.getActionCommand().equals("OK")){
            String keyStr = inputKey.getText();
            try {
                key = Integer.parseInt(keyStr);
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
            fileRecover(srcPath, destPath, key);
            FileUtils.writeRecoverLog(srcPath, destPath, key);
            JOptionPane.showMessageDialog(null, "已恢复备份文件！");
        }
    }

    public void fileRecover(String srcPath, String destPath, int key) {
        try {
            File file = new File(srcPath);
            if (!file.isFile()) {
                JOptionPane.showMessageDialog(null, "请选择正确的文件路径！");
            } else {
                String fileName = file.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (fileExtension.equals("zip")) {
                    String tempPath=destPath + File.separator + "temp";
                    FileZipUtil.unzip(srcPath, tempPath);
                    FileEncrypyUtils.decrypyDir(tempPath, destPath, key);
                    File tempFile = new File(tempPath);
                    FileUtils.deleteFile(tempFile);
                } else {
                    FileEncrypyUtils.decrypy(srcPath, destPath, key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

