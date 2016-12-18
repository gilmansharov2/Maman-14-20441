
public class Maman14 
{

	public static int what(int[] a) // Complexity: O(n)
	{
		if (a.length == 0) // If the array is empty
			return 0;
		int max = a[0];
		/*
		 * if the array contains only negative numbers, we'll return the maximum number in the array
		 */
		for (int i = 1; i < a.length; i++)
			max = (a[i] > max) ? a[i] : max;
		if (max < 0)
			return max;
		max = 0;
		int sum = 0;
		for (int i = 0; i < a.length; i++)
		{
			if (a[i] + sum > 0)
				sum += a[i];
			else
				sum = 0;
			
			max = (sum > max) ? sum : max;
		}
		return max;
	}
	
	
	public static void main(String[] args)
	{

	}
}