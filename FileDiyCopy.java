import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class FileDiyCopy {

    public static void copyDiyDir(String srcPath,String destPath) throws IOException{
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("please select from path filetype name time size");
    	String diyType = scanner.nextLine();
    	String path = null;
    	String fileType = null;
    	String name = null;
    	String timeType = null;
    	String time = null;
    	String sizeType = null;
    	int size = -1;
    	if(diyType.equals("path")) {
    		System.out.println("please enter your path, e.g. C:\\yourpath ");
    		path = scanner.nextLine();
    		//用户指定路径
    	}else if(diyType.equals("filetype")) {
    		System.out.println("please enter your file type, e.g. txt");
    		fileType = scanner.nextLine();
    		//用户指定文件类型
    	}else if(diyType.equals("name")) {
    		System.out.println("please enter your file name, e.g. computer_science");
    		name = scanner.nextLine();
    		//用户指定文件名
    	}else if(diyType.equals("time")) {
    		System.out.println("please enter your updated-time demand, e.g. before 2023-10-10 or after 2023-10-10");
    		timeType = scanner.next();
    		time = scanner.next();
    		//用户指定时间范围
    	}else if(diyType.equals("size")) {
    		System.out.println("please enter your file size demand in kb, e.g. smallerthan 10 or largerthan 10");
    		sizeType = scanner.next();
    		size = Integer.parseInt(scanner.next());
    		//用户指定文件大小
    	}else {
    		System.out.println("Wrong Input!");
    	}
    	copyDir(srcPath, destPath, diyType, path, fileType, name, timeType, time, sizeType, size);
    	scanner.close();
    }
    
	public static void copyDir(String srcPath,String destPath, String diyType, String path, String fileType, String name, String timeType, String time, String sizeType, Integer size) throws IOException {
        File src = new File(srcPath);//源头
        File dest = new File(destPath);//目的地
        //判断是否为目录，不存在则不作操作
        if(!src.isDirectory()) {
            return;
        }
        //判断目的目录是否存在，不存在就创建目录
        if(!dest.exists()) {
            boolean mkdir = dest.mkdir();
        }
        //获取源头目录下的文件列表组成数组，每个对象代表一个目录或者文件
        File[] srcList = src.listFiles();
        if (null != srcList && srcList.length > 0){
            //遍历源头目录下的文件列表
            for (File aSrcList : srcList) {
                //如果是目录的话
                if (aSrcList.isDirectory()) {
                    //递归调用遍历该目录
                    copyDir(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName(), diyType, path, fileType, name, timeType, time, sizeType, size);
                } else if (aSrcList.isFile()) {
                	if(diyType.equals("path")) {
                		String parentPath = aSrcList.getAbsolutePath();
                		//该文件当前路径
                		if(parentPath.startsWith(path)==true) {
                			 copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                		}
                	}else if(diyType.equals("filetype")) {
                		String fileName = aSrcList.getName();
                		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
                		//该文件文件类型
                		if(fileExtension.equals(fileType)==true) {
                			copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                		}
                	}else if(diyType.equals("name")) {
                		String fileName = aSrcList.getName();
                		String nameWithoutExtension = fileName.substring(0,fileName.lastIndexOf("."));
                		//该文件文件名
                		if(nameWithoutExtension.equals(name)==true) {
                			copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                		}
                	}else if(diyType.equals("time")) {                		
                		Date lastModifiedDate = new Date(aSrcList.lastModified());
                		//该文件最后修改时间
                		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                		Date timeDate = null;
                		try{
                			timeDate = dateFormat.parse(time);
                		}catch(ParseException e) {
                			e.printStackTrace();
                		}
                		System.out.println(timeDate);
                		System.out.println(lastModifiedDate);
                		if(timeType.equals("before")) {
                			if(lastModifiedDate.before(timeDate)) {
                				copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                			}
                		}else if(timeType.equals("after")) {
                			if(lastModifiedDate.after(timeDate)) {
                				copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                			}
                		}
                	}else if(diyType.equals("size")) {
                		int fileSize = (int)(aSrcList.length()/1024);
                		if(sizeType.equals("smallerthan")) {
                			if(fileSize < size) {
                				copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                			}
                		}else if(sizeType.equals("largerthan")) {
                			if(fileSize > size) {
                				copyFile(srcPath + File.separator + aSrcList.getName(), destPath + File.separator + aSrcList.getName());
                			}
                		}
                	}
                }
            }
        }
    }


    //    实现对文件的复制
    public static void copyFile(String isFile, String osFile) throws IOException {
    	
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(isFile);
            os = new FileOutputStream(osFile);
            byte[] data = new byte[4096];//缓存容器
            int len;//接收长度
            while((len=is.read(data))!=-1) {

                os.write(data, 0, len);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //	释放资源 分别关闭 先打开的后关闭
            try {
                if(null!=os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(null!=is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}
}
