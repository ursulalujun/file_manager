import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.awt.*;

class FileDiyCopy extends JFrame implements ActionListener{
	JPanel jp1, jp2, jp3, jp4, jp5;
	JLabel jl1, jl2, jl3, jl4, jl5, jl6;
	JTextField srcFileName, srcFileType, srcFileSize1, srcFileSize2, srcDateTime1, srcDateTime2;
	JButton jb;

	String fileType, fileName;
	String time1, time2;
	String size1, size2;

	String srcPath, destPath;

	FileDiyCopy(String srcPath,String destPath){
		super("自定义文件备份");
		this.srcPath = srcPath;
		this.destPath = destPath;

		jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		jl1 = new JLabel("请输入表示待还原文件类型的后缀名（例如，txt）：");
		srcFileType = new JTextField(5);
		jp1.add(jl1);
		jp1.add(srcFileType);

		jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		jl2 = new JLabel("请输入待备份文件的文件名：");
		srcFileName = new JTextField(20);
		jp2.add(jl2);
		jp2.add(srcFileName);

		jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		jl3 = new JLabel("请输入待备份的文件大小（以KB为单位，不含区间端点）：");
		srcFileSize1 = new JTextField(5);
		jl4 = new JLabel(" - ");
		srcFileSize2 = new JTextField(5);
		jp3.add(jl3);
		jp3.add(srcFileSize1);
		jp3.add(jl4);
		jp3.add(srcFileSize2);

		fileType = srcFileName.getSelectedText();
		jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		jl5 = new JLabel("请输入待还原的文件的修改时间（日期格式为yyyy-MM-dd）：");
		srcDateTime1 = new JTextField(7);
		jl6 = new JLabel(" - ");
		srcDateTime2 = new JTextField(7);
		jp4.add(jl5);
		jp4.add(srcDateTime1);
		jp4.add(jl6);
		jp4.add(srcDateTime2);

		jp5 = new JPanel();
		jp5.setLayout(new FlowLayout());
		jb = new JButton("确定");
		jp5.add(jb);
		System.out.println(fileType);
		jb.setActionCommand("selectFile");
		jb.addActionListener(this);

		this.setLayout(new GridLayout(5, 1));
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);

		this.setLocation(200, 200);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("selectFile")) {
			fileType = srcFileType.getText();
			fileName = srcFileName.getText();
			time1 = srcDateTime1.getText();
			time2 = srcDateTime2.getText();
			size1 = srcFileSize1.getText();
			size2 = srcFileSize2.getText();

			System.out.println("文件名："+fileName+" 文件类型："+fileType);
			System.out.println("文件大小："+size1+" - "+size2);
			System.out.println("文件时间："+time1+" - "+time2);

			Date timeDate1 = null, timeDate2 = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //该文件最后修改时间
			if(time1!=null){
				try {
					timeDate1 = dateFormat.parse(time1);
				} catch(ParseException e1) {
					e1.printStackTrace();
				}
			}
			if(time2!=null){
				try {
					timeDate2 = dateFormat.parse(time2);
				} catch(ParseException e1) {
					e1.printStackTrace();
				}
			}
			int fileSize1=0, fileSize2=Integer.MAX_VALUE;
			if(size1!=null) {
				try {
					fileSize1 = Integer.parseInt(size1);
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				}
			}
			if(size2!=null) {
				try {
					fileSize2 = Integer.parseInt(size2);
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				}
			}
			copyDir(srcPath, destPath, fileType, fileName, timeDate1, timeDate2, fileSize1, fileSize2);
		}
	}


	public static void copyDir(String srcPath, String destPath,
							   String fileType, String name,
							   Date timeDate1, Date timeDate2,
							   Integer minFileSize, Integer maxFileSize) {
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
                    copyDir(srcPath + File.separator + aSrcList.getName(),
							destPath + File.separator + aSrcList.getName(),
							fileType, name, timeDate1, timeDate2, minFileSize, maxFileSize);
                } else if (aSrcList.isFile()) {

					String fileName = aSrcList.getName();
					String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
					String nameWithoutExtension = fileName.substring(0,fileName.lastIndexOf("."));
					int fileSize = (int)(aSrcList.length()/1024);

					Date lastModifiedDate = new Date(aSrcList.lastModified());

					if ((fileType==null || fileExtension.equals(fileType) == true)
							&& (fileType == null || nameWithoutExtension.equals(name)==true)
							&& lastModifiedDate.before(timeDate1)
							&& lastModifiedDate.after(timeDate2)
							&& fileSize > minFileSize
							&& fileSize < maxFileSize
					) {
						copyFile(srcPath + File.separator + aSrcList.getName(),
								destPath + File.separator + aSrcList.getName());
                	}
                }
            }
        }
    }


    //    实现对文件的复制
    public static void copyFile(String isFile, String osFile) {
    	
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
        } catch (IOException e) {
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

