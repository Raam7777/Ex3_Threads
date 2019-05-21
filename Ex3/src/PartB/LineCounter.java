package PartB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class LineCounter extends Thread implements Runnable {

	private String file_name;
	private int num_of_lines = 0;
	
	public LineCounter(String name){
		this.file_name = name;
	}
	
	@Override
	public void run() {
		
		try {

			FileReader fr=new FileReader(file_name);
			BufferedReader br=new BufferedReader(fr);
			while(br.readLine()!=null)
				num_of_lines++;

			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("Cant open the file... sorry");
			e.printStackTrace();
		}
		
	}

	public int getNum_of_lines() {
		return num_of_lines;
	}

}
