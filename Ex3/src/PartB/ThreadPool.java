package PartB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.concurrent.Callable;
/**
 * This class produces a task(using Callable) which counts number of lines in file.
 * @author Raam Banin
 *
 */
public class ThreadPool implements Callable<Integer> {

	private String file_names;
	private int number = 0;
	
	public ThreadPool(String s){
		this.file_names = s;
	}
	
	@Override
	public Integer call() throws Exception {
		
		try {

			FileReader fr=new FileReader(file_names);
			BufferedReader br=new BufferedReader(fr);
			while(br.readLine()!=null)
				number++;

			br.close();
			fr.close();

		} catch (IOException e) {
			System.out.println("Cant open the file... sorry");
			e.printStackTrace();
		}
		
		return number;
	}

}
