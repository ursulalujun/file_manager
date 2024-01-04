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

import java.io.File;
import java.io.IOException;

public class FileCopy {
    public void copy(String srcPath,String dstPath) throws IOException {
        try {
            File file=new File(srcPath);
            if(file.isDirectory()){
                FileCopyUtils.copyDir(srcPath,dstPath);
            }
            else {
                File f=new File(dstPath);
                if(!f.exists()){
                    f.createNewFile();
                }
                FileCopyUtils.copyFile(srcPath,dstPath);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void compress(String srcPath,String dstPath){
        try {
            FileZipUtil.compressFile(srcPath,dstPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unzip(String srcPath, String dstPath) throws Exception {
        try {
            UnZipUtil.unzip(srcPath,dstPath);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void encrypy(String srcPath,String dstPath,int key) throws IOException {
        try {
            File file=new File(srcPath);
            if(file.isDirectory()){
                FileEncrypyUtils.search(srcPath,dstPath, key);
            }
            else {
                File f=new File(dstPath);
                if(!f.exists()){
                    f.createNewFile();
                }
                FileEncrypyUtils.encrypy(srcPath,dstPath,key);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void decrypy(String srcPath,String dstPath,int key) throws IOException {
        FileEncrypyUtils.decrypy(srcPath,dstPath,key);
    }

    public void varify(String firstpath,String secondpath) throws IOException {
        varifyUtils first=new varifyUtils();
        varifyUtils second=new varifyUtils();
        first.search(firstpath,firstpath);
        second.search(secondpath,secondpath);

//        System.out.println(first.map);
//        System.out.println(second.map);
//        if (first.map.equals(second.map)){
//            System.out.println(true);
//        }else {
//            System.out.println(false);
//        }
    }
}

