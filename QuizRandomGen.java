package itembank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.*;


/********************************************
 * 2014.10.06				-K.C.Kang
 * 
 * 
 * 중복되지 않는 랜덤한 숫자의 배열을 얻기 위한 클래스
 *******************************************/

public class QuizRandomGen {
	private HashSet<Integer> mRandNum;		//랜덤 숫자 중복 체크를 위한 해쉬셋
	private ArrayList<Integer> mNumList;	//중복되지 않는 숫자들을 저장할 리스트
	
	
	public ArrayList<Integer> RandGenList(int count, int maxValue) {
		
		if(maxValue < count)
			return null;		//최대 수보다 필요한 수가 더 크면 수행하지 않고 종료.
		
		mRandNum = new HashSet<Integer>();
		mNumList = new ArrayList<Integer>();
		
		Random rand = new Random();
		
		while (true)
		{
		    Integer number = rand.nextInt(maxValue);
		    
		   // System.out.println(number);

		    if (!mRandNum.contains(number))	//해쉬 셋에 포함되는지 확인 후
		    {
		    	mRandNum.add(number);		//해쉬 셋에 저장
		    	mNumList.add(number);		//리스트에 저장
		        
		        if (mRandNum.size() == count)	//필요한 수량만큼 생성되면 종료
		            break;
		    }
		}

		return mNumList;
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Start!");
		
		QuizRandomGen qrg = new QuizRandomGen();
		ArrayList<Integer> arr = qrg.RandGenList(20, 20);
		
		for(Integer i : arr) {
			System.out.print(i+":");
		}
	}
}
