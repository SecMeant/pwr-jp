package lab02;

import java.util.ArrayList;
import java.util.Arrays;

class SequenceGenerator
{
	private ArrayList<int[]> sequences;

	ArrayList<int[]> getCombinations(int[] sourceSet, int returnSetSize)
	{
		int data[] = new int[returnSetSize];
		sequences = new ArrayList<int[]>();

		if(returnSetSize >= sourceSet.length)
		{
			sequences.add(sourceSet);
			return sequences;
		}
		
		generateCombinations(sourceSet, data, 0, sourceSet.length-1, 0, returnSetSize);

		return sequences;
	}

	void generateCombinations(int arr[], int data[], int start,
                            int end, int index, int r)
	{
			// Current combination has appropriate size
			if (index == r)
			{
				this.sequences.add(Arrays.copyOfRange(data, 0, r));
				return;
			}

			// replace index with all possible elements. The condition
			// "end-i+1 >= r-index" makes sure that including one element
			// at index will make a combination with remaining elements
			// at remaining positions
			for (int i=start; i<=end && end-i+1 >= r-index; i++)
			{
					data[index] = arr[i];
					generateCombinations(arr, data, i+1, end, index+1, r);
			}
	}
}
