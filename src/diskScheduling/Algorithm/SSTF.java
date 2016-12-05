package diskScheduling.Algorithm;

import java.util.ArrayList;

public class SSTF {
	
	private int currentPosition;
	private ArrayList<Integer> requestQueue;
	private ArrayList<Integer> processedQueue = new ArrayList<Integer>();
	private int sum = 0;
	private int minimum;
	private int position = 0;
	
	@SuppressWarnings("unchecked")
	public SSTF(int currentPosition, ArrayList<Integer> request){
		this.currentPosition = currentPosition;
		this.requestQueue = (ArrayList<Integer>) request.clone();		
	}
	
	public void Disk_SSTF(){
		processedQueue.add(currentPosition);
		for(int i = 0; i < requestQueue.size(); i++){
			
			minimum = Math.abs(requestQueue.get(i)-currentPosition);
			position = i;
			for(int j = i; j < requestQueue.size(); j++){
				if(minimum > Math.abs(currentPosition - requestQueue.get(j) )){
					position = j;
					minimum = Math.abs(currentPosition - requestQueue.get(j));
				}
				
			}
			processedQueue.add(requestQueue.get(position));
			sum += Math.abs(currentPosition - requestQueue.get(position));
			currentPosition = requestQueue.get(position);
			requestQueue.set(position, requestQueue.get(i));
			requestQueue.set(i, currentPosition);
		}
	} 
	
	public int getSum() {
		return sum;
	}
	
	public ArrayList<Integer> getProcessedQueue() {
		return processedQueue;
	}
}
