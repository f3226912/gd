package com.gudeng.commerce.gd.api.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CopyOfUtils {
	
    static HashMap<Character,Integer> varTable;
    
	public static void dataToPicture(String source, String picpath) throws Exception {
		initVarTable();
    	if(source==null){
    		source="";
    	}
    	source = source.replaceAll("<", "");
    	source = source.replaceAll(">", "");
		ByteArrayInputStream inputStream = new ByteArrayInputStream(source.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		FileOutputStream os = new FileOutputStream(picpath);
		char h,l,t;
		int tmpByte;
		try{
			do{
				t = (char)br.read();
				if(Character.isSpaceChar(t))//���ո�
					continue;
				if(isEnd(t)) // ��ֹ�ж�
					break;
				h = t;
				
				t = (char)br.read();
				if(Character.isSpaceChar(t))//���ո�
					continue;
				if(isEnd(t)) // ��ֹ�ж�
					break;
				l = t;
				
				tmpByte = getByte(h, l);
				os.write(tmpByte);
				os.flush();
			}while(true);
		}catch(Exception e){
			l = '0';
		}
		br.close();
		os.close();
	}
	
	public static void initVarTable(){
		varTable = new HashMap<Character,Integer>();
		varTable.clear();
		varTable.put('0', 0);
		varTable.put('1', 1);
		varTable.put('2', 2);
		varTable.put('3', 3);
		varTable.put('4', 4);
		varTable.put('5', 5);
		varTable.put('6', 6);
		varTable.put('7', 7);
		varTable.put('8', 8);
		varTable.put('9', 9);
		varTable.put('A', 10);
		varTable.put('B', 11);
		varTable.put('C', 12);
		varTable.put('D', 13);
		varTable.put('E', 14);
		varTable.put('F', 15);
	}
	
	public static boolean isEnd(char c){
		try{
			varTable.get(c);
		}catch(Exception e){
			return true;
		}
		return false;
	}
	
	public static byte getByte(char H,char L) throws Exception{
		try{
			H = Character.toUpperCase(H);
			L = Character.toUpperCase(L);
			int _h = varTable.get(H);
			int _l = varTable.get(L);
			
			int tmp = (_h << 4) + (_l);
			byte rtn = (byte)tmp;
			return rtn;
		}catch(Exception err){
			throw new Exception("Error input char");
		}
	}
    
}
