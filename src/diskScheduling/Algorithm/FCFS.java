package diskScheduling.Algorithm;

import java.util.ArrayList;

public class FCFS {

	private int currentPosition;
	private ArrayList<Integer> requestQueue;
	private ArrayList<Integer> processedQueue = new ArrayList<Integer>();
	private int sum = 0;
	
	@SuppressWarnings("unchecked")
	public FCFS(int currentPosition, ArrayList<Integer> request){
		this.currentPosition = currentPosition;
		this.requestQueue = (ArrayList<Integer>) request.clone();
	}
	
	public void Disk_FCFS(){
		processedQueue.add(currentPosition);
		for(int i = 0; i < requestQueue.size(); i++){
			processedQueue.add(requestQueue.get(i));
			currentPosition -= requestQueue.get(i);
			if(currentPosition < 0){
				currentPosition = -currentPosition;
			}
			
			sum += currentPosition;
			currentPosition = requestQueue.get(i);
		}
	}
	
	public int getSum() {
		return sum;
	}
	
	public ArrayList<Integer> getProcessedQueue() {
		return processedQueue;
	}
}

