package itembank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import oracle.sql.BLOB;

public class MakeSolvePage {
	private ArrayList<Quizzes> quizList;
	private Integer[] rightAnswers;
	private String workPath; 
	private String saveWorkPath;
	private String savePicPath;
	
	public MakeSolvePage(ArrayList<Quizzes> qList, Integer[] rAnswers) {
		this.quizList = qList;
		this.rightAnswers = rAnswers;
		
		workPath = makeSolvePath();
		
		if(workPath!=null) {
			makeDirectory(workPath);	
			makeSolveFile(saveWorkPath);
			workPath = null;
		}
		
	}
	
	public void makeSolveFile(String path) {
		BufferedWriter bw = null;
		String file = path + "\\solution.html";
		
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("<HTML>\r\n");
			bw.write("<HEAD>\r\n");
			bw.write("<meta charset=\"UTF-8\">\r\n");  			  
			bw.write("</HEAD>\r\n"); 
			bw.write("<BODY>\r\n");
						
			for(int i=0; i<quizList.size(); i++) {
				bw.write("<TABLE border=1 width=800>\r\n");
				
				insertTable(bw, (i+1) +". " + quizList.get(i).getQuiz());
				
				
				if(quizList.get(i).getQuizImg()!=null) {
					byteToImage(savePicPath+"\\"+i+".gif", quizList.get(i).getQuizImg());
					insertTable(bw, "<img src=\"images\\" + i + ".gif\">");
				}
				
				insertTable(bw, "가. " + quizList.get(i).getExam1());
				insertTable(bw, "나. " + quizList.get(i).getExam2());
				insertTable(bw, "다. " + quizList.get(i).getExam3());
				insertTable(bw, "라. " + quizList.get(i).getExam4());
				
				
				if(quizList.get(i).getExamImg()!=null) {
					byteToImage(savePicPath+"\\"+i+"e.gif", quizList.get(i).getExamImg());
					insertTable(bw, "<img src=\"images\\" + i + "e.gif\">");
				}
				
				insertTable(bw, "정답 : " + String.valueOf(quizList.get(i).getAnswer()));
				insertTable(bw, "선택한 답 : " + String.valueOf(this.rightAnswers[i]));
				insertTable(bw, "풀이 : " + quizList.get(i).getSolution());
				
				bw.write("</TABLE>\r\n");
				bw.write("<HR>\r\n");
				
			}
			
			bw.write("</BODY>\r\n");
			bw.write("</HTML>\r\n");
			
			bw.write("\r\n");
			
			bw.flush();
			bw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void insertTable(BufferedWriter bw, String str) {
		try {
				bw.write("<TR>\r\n");
				bw.write("<TD>\r\n");
				bw.write(str+"\r\n");
				bw.write("</TD>\r\n");
				bw.write("</TR>\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void byteToImage(String path, byte[] binData) {
		/*****************************************
		 * BLOB 데이터를 파일로 변경
		 * 
		 ****************************************/
		
		FileOutputStream fos = null;
		//InputStream is = null;
		
		try {
			
			fos = new FileOutputStream(new File(path));
			//is = binData.getBinaryStream();
			
			int size = (int)binData.length;
			//byte[] buf = new byte[size];
			
			//int length = -1;
			
			//while ((length = is.read(buf)) != -1) {
			fos.write(binData, 0 , binData.length);
			fos.flush();
			//}
			
			fos.close();
			//is.close();
			
			//} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			} 
	}
	
	private String makeSolvePath() {
		/********************************************
		 * 14.10.07					-K.C.Kang
		 * 
		 * JFilechooser 클래스를 이용하여 파일 다이얼로그로 파일을
		 * 찾는 일을 한다.
		 *******************************************/
		
		JFileChooser fileDlg = new JFileChooser();
		
		fileDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // folder가 디렉토리만 인식하도록 설정
		
		int ret = fileDlg.showOpenDialog(null);
	
		
		if(ret != JFileChooser.APPROVE_OPTION) { // 사용자가  창을 강제로 닫았거나 취소 버튼을 누른 경우
	                JOptionPane.showMessageDialog(null,"디렉토리를 선택하지 않았습니다", "경고", 
                    JOptionPane.WARNING_MESSAGE);
	                return null;
	    		}
	     
	    // 사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
	    String filePath = fileDlg.getSelectedFile().getPath(); // 파일 경로명을 알아온다.
	   	
		return filePath;
	}
	
	private void makeDirectory(String path) {
	    
		String currentDate = new SimpleDateFormat("yyMMddhhmmss").format(new java.util.Date());
		
		saveWorkPath = path + "정보처리기사풀이_" +currentDate;
	    savePicPath = saveWorkPath+"\\images";
	    
	    //System.out.println(picPath);
	       
	    File workDir = new File(saveWorkPath);
	    File picDir = new File(savePicPath);
	    
	    workDir.mkdir();
	    picDir.mkdir();
	    
	}
	
	public static void main(String[] args) {
		new MakeSolvePage(null, null);
	}
}
