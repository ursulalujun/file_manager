import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.awt.*;
import java.util.List;

class BackUpDialogue extends JFrame implements ActionListener{
	JPanel jp1, jp2, jp3, jp4, jp5;
	JLabel jl1, jl2, jl3, jl4, jl5;
	JTextField inputKey;
	JButton jsrc, jdes, jok;

	String srcPath, destPath;
	int key;

	BackUpDialogue(){
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

		jp4 = new JPanel();
		jp4.setLayout(new FlowLayout());
		jl3 = new JLabel("请输入加密的密钥（1-6位的整数）");
		inputKey=new JTextField(5);
		jp4.add(jl3);
		jp4.add(inputKey);

		this.setLayout(new GridLayout(4, 1));
		this.add(jp1);
		this.add(jp2);
		this.add(jp4);
		this.add(jp3);

		this.setLocation(200, 200);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
	}

	public void fileCopy(String srcPath,String destPath, int key) {
		try {
			File file=new File(srcPath);
			if(file.isDirectory()){
				new DirCopyDialogue(srcPath, destPath, key);
			}
			else {
				File f=new File(destPath);
				if(!f.exists()){
					f.createNewFile();
				}
				FileEncrypyUtils.encrypy(srcPath, destPath, key);
				FileUtils.writeBackUpLog(false, "", srcPath, destPath, "", key);
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
			String keyStr = inputKey.getText();
			try{
				key=Integer.parseInt(keyStr);
			}catch (NumberFormatException e2){
				e2.printStackTrace();
			}
			fileCopy(srcPath, destPath, key);
		}
	}
}

class DirCopyDialogue extends JFrame implements ActionListener{
	JPanel jp1, jp2, jp3, jp4, jp5, jp6;
	JLabel jl1, jl2, jl3, jl4, jl5, jl6, jl7, jl8;
	JTextField srcFileName, srcFileType, srcFileSize1, srcFileSize2, srcDateTime1, srcDateTime2;
	JButton jb;
	Checkbox cb1, cb2;

	String fileType, fileName;
	String time1, time2;
	String size1, size2;

	String srcPath, destPath;
	int key;

	DirCopyDialogue(String srcPath,String destPath, int key){
		super("自定义文件备份");
		this.srcPath = srcPath;
		this.destPath = destPath;
		this.key = key;

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
		jl3 = new JLabel("请输入待备份的文件大小（以KB为单位）：");
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
		jb.setActionCommand("selectFile");
		jb.addActionListener(this);

		jp6 = new JPanel();
		jp6.setLayout(new FlowLayout());
		jl7 = new JLabel("加密");
		cb1 = new Checkbox();
		jl8 = new JLabel("打包压缩");
		cb2 = new Checkbox();
		jp6.add(jl7);
		jp6.add(cb1);
		jp6.add(jl8);
		jp6.add(cb2);

		this.setLayout(new GridLayout(6, 1));
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		// this.add(jp6);
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
			System.out.println("加密秘钥："+key);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //该文件最后修改时间

			Date timeDate1 = null, timeDate2=null;
			try{
				timeDate1 = dateFormat.parse("1980-1-1");
				timeDate2 = dateFormat.parse("2024-10-10");
			} catch (ParseException e0){
				e0.printStackTrace();
			}
			if(!time1.equals("")){
				try {
					timeDate1 = dateFormat.parse(time1);
				} catch(ParseException e1) {
					e1.printStackTrace();
				}
			}
			if(!time2.equals("")){
				try {
					timeDate2 = dateFormat.parse(time2);
				} catch(ParseException e1) {
					e1.printStackTrace();
				}
			}
			int fileSize1=0, fileSize2=Integer.MAX_VALUE;
			if(!size1.equals("")) {
				try {
					fileSize1 = Integer.parseInt(size1);
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				}
			}
			if(!size2.equals("")) {
				try {
					fileSize2 = Integer.parseInt(size2);
				} catch (NumberFormatException e2) {
					e2.printStackTrace();
				}
			}

			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			Date dateNow = new Date(System.currentTimeMillis());

			String tempPath=destPath + File.separator + formatter.format(dateNow);
			copyDir(srcPath, tempPath,
					fileType, fileName,
					timeDate1, timeDate2,
					fileSize1, fileSize2, key);
			FileZipUtil.compressFile(tempPath, destPath);
			File tempFile= new File(tempPath);
			FileUtils.deleteFile(tempFile);
		}
	}


	public void copyDir(String srcPath, String destPath,
						String fileType, String fileName,
						Date timeDate1, Date timeDate2,
						int minFileSize, int maxFileSize,
						int key) {
        File src = new File(srcPath);//源头
        File dest = new File(destPath);//目的地
		List<String> copiedFiles = new ArrayList<String>();
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
							fileType, fileName, timeDate1, timeDate2, minFileSize, maxFileSize,
							key);
                } else if (aSrcList.isFile()) {

					String name = aSrcList.getName();
					String fileExtension = name.substring(name.lastIndexOf(".") + 1);
					String nameWithoutExtension = name.substring(0,name.lastIndexOf("."));
					int fileSize = (int)(aSrcList.length()/1024);

					Date lastModifiedDate = new Date(aSrcList.lastModified());

					if ((fileType.equals("") || fileExtension.equals(fileType) == true)
							&& (fileName.equals("") || nameWithoutExtension.equals(fileName)==true)
							&& lastModifiedDate.before(timeDate2)
							&& lastModifiedDate.after(timeDate1)
							&& fileSize >= minFileSize
							&& fileSize <= maxFileSize
					) {
						FileEncrypyUtils.encrypy(srcPath + File.separator + aSrcList.getName(),
									destPath + File.separator + aSrcList.getName(), key);
						copiedFiles.add(aSrcList.getName());
                	}
                }
            }
        }
		String copiedFilesStr="";
		for (String file:copiedFiles){
			copiedFilesStr=copiedFilesStr+file+"，";
		}
		String selected = "文件名："+fileName+"；文件类型："+fileType
				+ "；文件大小："+size1+" - "+size2
				+ "；文件更新时间："+time1+" - "+time2;
		FileUtils.writeBackUpLog(true, copiedFilesStr.substring(0,copiedFilesStr.length()-1),
				this.srcPath, this.destPath, selected, key);
		if(copiedFiles.size()==0)
			JOptionPane.showMessageDialog(null, "无符合条件的文件，请重新输入！");
		else
			JOptionPane.showMessageDialog(null,
				"文件备份成功！已备份："+copiedFilesStr.substring(0,copiedFilesStr.length()-1));
	}

}

