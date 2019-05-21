package PartB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex3B {

	/**
	 * A static function that creates a given number of text files.
	 * Each file contains a random number of rows.
	 * @param n - number files.
	 * @return - array of file names.
	 * @throws IOException
	 */
	public static String[] createFiles(int n) throws IOException
	{
		String [] files = new String[n];
		String input = "Hello World";

		Random r = new Random(123);
		for (int i = 0; i < files.length; i++) 
		{
			int numLines = r.nextInt(1000);
			files[i] = "File_" + i + ".txt";

			FileWriter fw = new FileWriter(files[i]);
			BufferedWriter bw = new BufferedWriter(fw);

			int j = 0;
			while(j < numLines) 
			{
				bw.write(input);
				bw.newLine();
				j++;
			}

			bw.close();

		}

		return files;
	}

	/**
	 * function that accepts and deletes file names.
	 * @param fileNames - File names.
	 */
	public static void deleteFiles(String[] fileNames)
	{
		for (int i = 0; i < fileNames.length; i++) 
		{
			File file = new File(fileNames[i]);
			file.delete();
		}
	}

	/**
	 * The function creates files, thread for every file and counting number of lines.
	 * @param numFiles - number of files to create.
	 * @throws IOException
	 */
	public static void countLinesThreads(int numFiles) throws IOException
	{
		String[] file_names = createFiles(numFiles);
		int sum = 0;
		
		LineCounter[] th_line_counter = new LineCounter[numFiles];
		Thread [] threads = new Thread[numFiles];
		long time = System.currentTimeMillis();
		
		for (int i = 0; i < th_line_counter.length; i++) {
			th_line_counter[i] = new LineCounter(file_names[i]);
			threads[i] = new Thread(th_line_counter[i]);
			th_line_counter[i].start();
			while ((th_line_counter[i]).isAlive());
			sum += th_line_counter[i].getNum_of_lines();
			
		}
		for (int i = 0; i < numFiles; i++)
		{
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		time = System.currentTimeMillis()-time;
		
		System.out.println("Lines: " + sum +"   Time: "+time);
		deleteFiles(file_names);
	}

	/**
	 * The function creates file and counting file the number of lines.
	 * Uses a technique ThreadPool. 
	 * @param num - number of files to create.
	 * @throws IOException
	 * https://howtodoinjava.com/java/multi-threading/java-callable-future-example/
	 */
	public static void countLinesThreadPool(int num) throws IOException {
		String[] file_names= createFiles(num);
		int sum = 0;
		long time=System.currentTimeMillis();
		List<Future<Integer>> resultList = new ArrayList<>();
		ExecutorService pool = Executors.newCachedThreadPool();
		for (int i=0; i<num; i++){
			ThreadPool th  = new ThreadPool(file_names[i]);
            Future<Integer> result = pool.submit(th);
            resultList.add(result); 
		}
		
		time=System.currentTimeMillis()-time;
		
		for(Future<Integer> future : resultList)
        {
              try
              {
                  sum += future.get();
              }
              catch (InterruptedException | ExecutionException e)
              {
                  e.printStackTrace();
              }
          }
		
		System.out.println("Lines: " + sum+"   Time: "+time);

		pool.shutdown();
		deleteFiles(file_names);
		
	}

	/**
	 * The function creates files and counting the number of lines. Only one process.
	 * @param numFiles - number of files to create.
	 * @throws IOException
	 */
	public static void countLinesOneProcess(int numFiles) throws IOException {
		
		String[] fileNames= createFiles(numFiles);
		int sum =0;
		long time = System.currentTimeMillis();
		for (int i = 0; i < numFiles; i++)
		{
			LineCounter line_counter = new LineCounter(fileNames[i]);
			line_counter.run();
			sum+=line_counter.getNum_of_lines();
		}

		time = System.currentTimeMillis()-time;
		System.out.println("Lines: " + sum+"   Time: "+time);

		deleteFiles(fileNames);
		

	}

	public static void main(String[] args) throws IOException {

		int num = 1000;
		countLinesThreads(num);
		countLinesOneProcess(num);
		countLinesThreadPool(num);
	}





}
