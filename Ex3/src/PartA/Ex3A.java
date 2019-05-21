package PartA;

/**
 * 
 * @author Raam Banin
 *
 */
public class Ex3A implements Runnable {

	private long num;
	private boolean prime;

	public void run(){
		prime = Ex3_tester.isPrime(num);
	}

	/**
	 * The function calculates whether the natural number is prime.
	 * https://www.geeksforgeeks.org/daemon-thread-java/
	 * @param n - natural number.
	 * @param maxTime - time.
	 * @return - true or false is prime.
	 * @throws RuntimeException
	 */
	public boolean isPrime(long n, double maxTime) throws RuntimeException
	{
		num = n;
		long time=(long)(maxTime*1000);
		Thread th1 = new Thread(this);
		
		th1.setDaemon(true);

		long begin = System.currentTimeMillis();
		th1.start();
		long end = System.currentTimeMillis();

		while((end-begin)<time){
			if (prime != false)
				return prime;
			end = System.currentTimeMillis();
		}
		throw new RuntimeException("There was no reply for " +time+ " seconds");

		
	}
}
