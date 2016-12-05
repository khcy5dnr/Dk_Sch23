package diskScheduling.Algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class Look {
	
	private int currentPosition;
	private int head;
	private ArrayList<Integer> requestQueue;
	private ArrayList<Integer> processedQueue = new ArrayList<Integer>();
	private int sum = 0;
	private int position = 0;
	
	@SuppressWarnings("unchecked")
	public Look(int currentPosition, ArrayList<Integer> request){
		this.currentPosition = currentPosition;
		this.requestQueue = (ArrayList<Integer>) request.clone();
		this.head = currentPosition;
	}
	
	public void Disk_Look(){
		processedQueue.add(currentPosition);
		
		//sort the request queue
		Collections.sort(requestQueue);
		
		//get position of request higher than head
		for(int i = 0; i < requestQueue.size(); i++){
			if(requestQueue.get(i) < head){
				position++;
			}
		}
		
		//sort in descending order from position of the head
		for(int i = 0; i < position; i++){
			for(int j = 0; j < (position - i - 1); j++){
				if(requestQueue.get(j) < requestQueue.get(j+1)){
					currentPosition = requestQueue.get(j);
					requestQueue.set(j,requestQueue.get(j+1));
					requestQueue.set(j+1, currentPosition);
				}
			}
		}
		currentPosition = head;
		
		//add total seek time from head to the lowest request
		for(int i = 0; i < position; i++){
			sum += Math.abs(requestQueue.get(i) - currentPosition);
			currentPosition = requestQueue.get(i);
			processedQueue.add(currentPosition);
		}
		
		//add total seek time from lowest request to the last request
		for(int i = position; i < requestQueue.size(); i++){
			sum += Math.abs(requestQueue.get(i) - currentPosition);
			currentPosition = requestQueue.get(i);
			processedQueue.add(currentPosition);
		}
	}
	
	public int getSum() {
		return sum;
	}
	
	public ArrayList<Integer> getProcessedQueue() {
		return processedQueue;
	}
	
}
