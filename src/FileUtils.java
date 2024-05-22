import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    public static void writeBackUpLog(boolean isDir, String copiedFiles, String srcPath, String destPath,
                                String selected, int key){
        File log = new File("log.txt");
        FileWriter writer=null;
        try{
            if (!log.exists())
                log.createNewFile(); // 创建目标文件
            writer = new FileWriter(log, true);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date dateNow = new Date(System.currentTimeMillis());
            if(isDir){
                writer.append(formatter.format(dateNow) + " 文件夹备份\n"
                        + "原文件夹路径：" + srcPath + "\n"
                        + "备份路径：" + destPath +"\n"
                        + "备份文件：" + copiedFiles + "\n"
                        + "筛选条件：" + selected + "\n"
                        + "加密秘钥：" + key + "\n\n");
                writer.flush();
            }
            else {
                writer.append(formatter.format(dateNow) + " 文件备份\n"
                        + "原文件路径：" + srcPath + "\n"
                        + "备份路径：" + destPath +"\n"
                        + "加密秘钥：" + key + "\n\n");
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "日志写入出错！");
            e.printStackTrace();
        } finally {
            try{
                if (null != writer)
                    writer.close();
            } catch (IOException e2){
                JOptionPane.showMessageDialog(null, "关闭日志文件时出错！");
                e2.printStackTrace();
            }
        }
    }

    public static void writeRecoverLog(String srcPath, String destPath, int key){
        File log = new File("log.txt");
        FileWriter writer=null;
        try{
            if (!log.exists())
                log.createNewFile(); // 创建目标文件
            writer = new FileWriter(log, true);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date dateNow = new Date(System.currentTimeMillis());
            writer.append(formatter.format(dateNow) + " 备份恢复\n"
                    + "备份路径：" + srcPath + "\n"
                    + "恢复路径：" + destPath +"\n"
                    + "加密秘钥：" + key + "\n\n");
            writer.flush();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "日志写入出错！");
            e.printStackTrace();
        } finally {
            try{
                if (null != writer)
                    writer.close();
            } catch (IOException e2){
                JOptionPane.showMessageDialog(null, "关闭日志文件时出错！");
                e2.printStackTrace();
            }
        }
    }

    public static void deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            JOptionPane.showMessageDialog(null, "文件删除失败,请检查文件是否存在以及文件路径是否正确");
            return;
        }
        //获取目录下子文件
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                //递归删除目录下的文件
                deleteFile(f);
            } else {
                //文件删除
                f.delete();
                //打印文件名
            }
        }
        //文件夹删除
        file.delete();
    }
}
