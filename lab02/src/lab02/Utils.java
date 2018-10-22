package lab02;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

class Utils
{
	public static int[] getArraysComplement(int[] array, int[] subarray)
	{
		List<Integer> arrayI = IntStream.of(array).boxed().collect(Collectors.toList());
		List<Integer> subarrayI = IntStream.of(subarray).boxed().collect(Collectors.toList());
		
		arrayI.removeAll(subarrayI);
		return arrayI.stream().mapToInt(i->i).toArray();
	}

	public static boolean contains(int[] array, int value)
	{
		for (int el : array)
		{
			if(el == value) return true;
		}
		
		return false;
	}
}
