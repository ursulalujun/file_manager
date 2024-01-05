import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZipUtil {
    public static void compressFile(String srcPath, String outPath) {
        //读取源文件
        File srcFile = new File(srcPath);
        //判断输出路径是否正确
        File outFile = new File(outPath);

        if (outFile.isDirectory()) {
            //用"/"作文判断标准
            if (outPath.endsWith(File.separator)) {
                outPath += srcFile.getName().split("\\.")[0] + ".zip";
            } else {
                outPath += File.separator + srcFile.getName().split("\\.")[0] + ".zip";
            }
        }

        try {
            //读取文件流
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            //压缩文件
            compressFile(srcFile, srcFile.getName(),zipOutputStream);
            //关闭流
            zipOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private static void compressFile(File file, String fileName, final ZipOutputStream outputStream) {
        try {
            //如果是目录
            if (file.isDirectory()) {
                //创建文件夹
                outputStream.putNextEntry(new ZipEntry(fileName+"/"));
                //迭代判断，并且加入对应文件路径
                File[] files = file.listFiles();
                Iterator<File> iterator = Arrays.asList(files).iterator();
                while (iterator.hasNext()) {
                    File f = iterator.next();
                    compressFile(f, fileName+"/"+f.getName(), outputStream);
                }
            } else {
                //创建文件
                outputStream.putNextEntry(new ZipEntry(fileName));
                //读取文件并写出
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                byte[] bytes = new byte[1024];
                int n;
                while ((n = bufferedInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, n);
                }
                //关闭流
                fileInputStream.close();
                bufferedInputStream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void unzip(String zipFilePath, String desDirectory) {
        try{
            File desDir = new File(desDirectory);
            if (!desDir.exists()) {
                boolean mkdirSuccess = desDir.mkdir();
                if (!mkdirSuccess) {
                    throw new Exception("创建解压目标文件夹失败");
                }
            }
            // 读入流
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
            // 遍历每一个文件
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (zipEntry.isDirectory()) { // 文件夹
                    String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                    // 直接创建
                    mkdir(new File(unzipFilePath));
                } else { // 文件
                    String unzipFilePath = desDirectory + File.separator + zipEntry.getName();
                    File file = new File(unzipFilePath);
                    // 创建父目录
                    mkdir(file.getParentFile());
                    // 写出文件流
                    BufferedOutputStream bufferedOutputStream =
                            new BufferedOutputStream(new FileOutputStream(unzipFilePath));
                    byte[] bytes = new byte[1024];
                    int readLen;
                    while ((readLen = zipInputStream.read(bytes)) != -1) {
                        bufferedOutputStream.write(bytes, 0, readLen);
                    }
                    bufferedOutputStream.close();
                }
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // 如果父目录不存在则创建
    private static void mkdir(File file) {
        if (null == file || file.exists()) {
            return;
        }
        mkdir(file.getParentFile());
        file.mkdir();
    }
}
