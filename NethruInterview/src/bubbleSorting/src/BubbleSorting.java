package bubbleSorting.src;

public class BubbleSorting {

	public int[] sort(int[] array) {
		int temp;
		int length = array.length;
		
		/*if(length == 2) {
			if(array[0] > array[1]){
				temp = array[1];
				array[1] = array[0];
				array[0] = temp;
			}
		}
		
		if(length == 3) {
			if(array[0] > array[1]){
				temp = array[1];
				array[1] = array[0];
				array[0] = temp;
			}
			
			if(array[1] > array[2]){
				temp = array[1];
				array[1] = array[2];
				array[2] = temp;
			}
			
			if(array[0] > array[1]) {
				temp = array[0];
				array[0] = array[1];
				array[1] = temp;
			}
		}*/
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length - (i+1); j++ ) {
				if(array[j] > array[j+1]) {
					temp = array[j+1];
					array[j+1] = array[j];
					array[j] = temp;
				}
			}
		}
		
		return array;
	}
}
