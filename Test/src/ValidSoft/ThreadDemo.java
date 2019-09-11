package ValidSoft;

public class ThreadDemo {
	protected static final int MAX_THREAD_COUNT = 8;
	private volatile static int completedThread = MAX_THREAD_COUNT;
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println("start threading with count" + MAX_THREAD_COUNT);
				
				for (int i = 0; i < MAX_THREAD_COUNT; i++) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							doWork();
						}

						private void doWork() {
							// TODO Auto-generated method stub
							
						}

					}) {
	
					}.start();
				}
				
				waitThread();
				
			}
		}) {

		}.start();
	}
	
	
	protected static void waitThread() {
		while(getCompletedThreadCount() > 0) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			
			}
		}
		
	}


	private static int getCompletedThreadCount() {
		
		
		return completedThread;
	}

}
