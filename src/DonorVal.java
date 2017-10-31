import java.util.Collections;
import java.util.PriorityQueue;

public class DonorVal {
		private int count;
		private double sum;
		private double median;
		private PriorityQueue<Double> minHeap;
		private PriorityQueue<Double> maxHeap;
		private long numElements;
		
		/*
		 * Constructor to initaialixe the data members
		 */
		public DonorVal(int count, double sum, double median) {
			this.count = count;
			this.sum = sum;
			this.median = median;
			numElements = 0;
			minHeap = new PriorityQueue<>();
			maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		}
		
		public int getCount() {
			return count;
		}
		
		public long getSum() {
			return (long)Math.round(sum);
		}
		
		public void setCount() {
			count++;
			return;
		}
		
		public void setSum(double val) {
			sum += val;
			return;
		}
		
		/*
		 * Add numbers to the existing stream
		 */
		public void addNumberToStream (double num) {
			maxHeap.offer(num);
			
			if (numElements % 2 == 0) {
				if (minHeap.isEmpty()) {
					numElements++;
					return;
				}
				
				else if (maxHeap.peek() > minHeap.peek()) {
					double maxHeapRoot = maxHeap.poll();
					double minHeapRoot = minHeap.poll();
					maxHeap.offer(minHeapRoot);
					minHeap.offer(maxHeapRoot);
				}
			}
			else {
				minHeap.offer(maxHeap.poll());
			}
			
			numElements++;
		}
		
		/*
		 * Funtion to return the median
		 */
		public int getMedian() {
			if (numElements % 2 != 0)
				median = new Double(maxHeap.peek());
			else
				median =  (maxHeap.peek() + minHeap.peek()) / 2.0; 
			
			return (int)Math.round(median);
		}
	}