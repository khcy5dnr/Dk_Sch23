package diskScheduling.Algorithm;

import java.util.ArrayList;
import java.util.Collections;

public class C_Scan {
	
	private int cylinder;
	private int currentPosition;
	private int head;
	private ArrayList<Integer> requestQueue;
	private ArrayList<Integer> processedQueue = new ArrayList<Integer>();
	private int sum = 0;
	private int position = 0;
	
	@SuppressWarnings("unchecked")
	public C_Scan(int cylinder,int currentPosition, ArrayList<Integer> request){
		this.cylinder = cylinder;
		this.currentPosition = currentPosition;
		this.requestQueue = (ArrayList<Integer>) request.clone();
		this.head = currentPosition;
	}
	
	public void Disk_CScan(){
		processedQueue.add(currentPosition);
		
		//sort the request queue
		Collections.sort(requestQueue);
		
		//get position of request higher than head
		for(int i = 0; i < requestQueue.size(); i++){
			if(requestQueue.get(i) < head){
				position++;
			}
		}
		
		currentPosition = head;
		//add total seek time from head to the last cylinder
		for(int i = position; i < requestQueue.size(); i++){
			sum += Math.abs(requestQueue.get(i) - currentPosition);
			currentPosition = requestQueue.get(i);
			processedQueue.add(currentPosition);
		}
		
		processedQueue.add(cylinder-1);
		sum += Math.abs((cylinder-1) - currentPosition);
		currentPosition = 0;
		processedQueue.add(currentPosition);
		
		//add total seek time from 0 to the last request in ascending order
		for(int i = 0; i < position; i++){
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
