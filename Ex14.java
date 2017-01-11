/**
 * @author Gil
 * @ID 313260192
 */
public class Ex14 {

	/**
	 * 1.a. The function returns the maximal sub-array length which is sum divided by 3 without residue.
	 * 1.b. Complexity: O(n^3).
	 * 1.c. See function 'what' above.
	 */
	
	public static int what(int[] a) // 1.d. Complexity: O(n).
	// only 2 "for" loops, seperated, in comparison to the previous function, the private 'f' function is O(n), which makes the 'what' function O(n^3). I didn't use the 'f' function,
	// instead, I summed the array, as each index represents the sum of the array from 0 to the current index.
	// Now we're looking on 3 sub-arrays; the first one is from the first index of the array to the last index of the last element(each element is the array sum
	// from the beginning to the element) that divided by 3 without residue. The second sub-array is from the first element that divided by 3 with residue of 1,
	// to the last element that divided by 3 with residue 1. The third sub-array is the same as the second, but with the residue of 2.
	// now that we have these 3 sub-arrays, we'll calculate the longest sub-array- this sub array is the sub array we're looking for and it's length will be returned.
	{
		int firstZero = 0, lastZero = -1;
		int firstOne = -1, lastOne = -1;
		int firstTwo = -1, lastTwo = -1;
		for (int i = 0; i < a.length; i++)
		{
			a[i] += (i > 0) ? a[i - 1] : 0;
			
			switch(Math.abs(a[i] % 3))
			{
			case 0:
				break;
			case 1:
				firstOne = (firstOne == -1) ? i : firstOne;
				break;
			case 2:
				firstTwo = (firstTwo == -1) ? i : firstTwo;
				break;
			}
			
			switch(Math.abs(a[a.length - i - 1] % 3))
			{
			case 0:
				lastZero = (lastZero == -1) ? a.length - i - 1 : lastZero;
				break;
			case 1:
				lastOne = (lastOne == -1) ? a.length - i - 1 : lastOne;
				break;
			case 2:
				lastTwo = (lastTwo == -1) ? a.length - i - 1 : lastTwo;
				break;
			}
		}


		int zero = lastZero - firstZero + 1;
		int one = lastOne - firstOne;
		int two = lastTwo - firstTwo;
		
		int max = Math.max(zero,  Math.max(one, two));
		return max;
	}
	
	/**
	 * 2. The function divides the array to sub-arrays, each sub-array is from one zero value to another. We will change the sub-arrays as their indexes at the sub-array (if the distance from one zero to another is 6, then
	 * we'll have '0,1,2,3,4,0'). and then we'll change the sub-array again- but now from the end to the beginning (we need to put the distance to the closest zero), and we'll check which one of the indexes of the sub-array
	 * (from the beginning to the end, or from the end to the beginning) is closer to a zero.
	 * Also there are 2 exceptions, the sub-array from the beginning to the first zero, and the sub-array from the last zero to the end (because there is only one zero in these sub-arrays, instead of two),
	 * so we'll check each one of them individually.
	 * In conclusion, we're going into 6 for loops, which it's time complexity is O(n), we don't declare or allocating memory for any non-constant value (except the array which is not included), so the memory complexity is O(1).
	 * @param a an array contains only 1 and 0 values
	 */
	
	public static void zeroDistance(int[] a) // Time Complexity: O(n), Memory Complexity: O(1)
	{
		int firstZero = 0, lastZero = a.length - 1;
		int count = 0;
		for (int i = 0; i < a.length; i++) //finding the index of the first zero
			if (a[i] == 0)
			{
				firstZero = i;
				break;
			}
		
		for (int i = a.length - 1; i >= 0; i--) //finding the index of the last zero
			if (a[i] == 0)
			{
				lastZero = i;
				break;
			}
		
		for (int i = firstZero; i >= 0; i--) // changing the array from the first zero to the beginning of the array
			a[i] = count++;
		
		count = 0;
		for (int i = lastZero; i < a.length; i++) // changing the array from the last zero to the end of the array
			a[i] = count++;
		
		count = 0;
		for (int i = firstZero; i < lastZero; i++) // changing the array from the first zero to the last zero
			if (a[i] == 0)
				count = 0;
			else
				a[i] = ++count;
		
		count = 0;
		for (int i = lastZero; i > firstZero; i--) // changing the array from the last zero to the first zero, but we still check that the value in the box is from the closest zero (count < a[i]).
			if (a[i] == 0)
				count = 0;
			else
				a[i] = (count < a[i]) ? ++count : a[i];
	}
	
	/**
	 * The function gets an array and an array index, it counts the distance from the closest zero value from the right and the left, and returns the minimal distance between the two
	 * @param a an array contains only 1 and 0 values
	 * @param x an array index
	 */
	
	
	/**
	 * I overloaded the original method with a method that gets 4 arguments- the two Strings, and an index per string (integer i for string s, and integer j for string t).
	 * @param s The original string.
	 * @param t The string we check for transformation from s.
	 * @return true if the String t is a transformation of String s, otherwise, returns false. 
	 */
	public static boolean isTrans(String s, String t)
	{
		return isTrans(s, t, 0, 0);
	}
	
	
	/**
	 * The method first checks if the lengths of the strings are reasonable (s cannot be longer than t because t should be a transformation of s).
	 * if the lengths are reasonable, then we split each iteration for 2 cases: the char at i in s equals to the char at j in t, and the case that the chars are non-equal.
	 * 
	 * first case: if the chars are equal, and i and j are representing the last index at s and t, then the we finished checking and the result is true.
	 * if not, we'll raise i and j, and in case i cannot be raised anymore (equals to last index), we'll raise j only.
	 * 
	 * second case: if the chars aren't equal, then it could be for two reasons- the char has been multiplied in the transformation (which is okay), or the char isn't a valid char for a
	 * transformed string (which is not okay).
	 * so we check if the char has been multiplied or not (by comparing the current char represented by j to the previous char represented by j-1).
	 * if it hadn't been multiplied, then the char is not a valid char and the method will return false, if it has been multiplied, we'll raise j by 1 and continue to check the two strings
	 * until we'll return false or true, if we ended and the method hadn't returned anything, we'll return true by default.
	 * @param s The original string.
	 * @param t The string we check for transformation from s.
	 * @param i Represents an index for string s.
	 * @param j Represents an index for string t.
	 * @return true if the String t is a transformation of String s, otherwise, returns false. 
	 */
	private static boolean isTrans(String s, String t, int i, int j)
	{
		if (t.length() < s.length())
			return false;
		else if (s.charAt(i) == t.charAt(j)) {
			if ((j == t.length()-1) && (i == s.length()-1))
				return true;
			else if (i == s.length()-1)
				return (isTrans (s, t, i, ++j));
			else 
				return (isTrans (s, t, ++i, ++j));
		}
		else if (s.charAt(i) != t.charAt(j)) {
			if (j == t.length()-1)
				return false;
			else if (t.charAt(j-1) != t.charAt(j))
				return false;
			return (isTrans (s, t, i, ++j));
		}
		return true;
	}
	
	/**
	 * 
	 * @param a an array containing integers.
	 * @param pattern an array containing only 0, 1 or 2.
	 * @return true if the array a contains a sub-array compatible with the pattern array
	 */
	public static boolean match(int[] a, int[] pattern)
	{
		return match(a, pattern, 0, 0, 0);
	}
	
	//TODO: comment this shit
	/**
	 * 
	 * @param a
	 * @param pattern
	 * @param i
	 * @param j
	 * @param aIndex
	 * @return
	 */
	private static boolean match(int[] a, int[] pattern, int i, int j, int aIndex)
	{
		if (pattern.length == 0)
			return true;
		if (a.length < pattern.length)
			return false;
		if (a[i]/10 == 0 && pattern[j] != 2)
		{
			if (j == pattern.length - 1)
				return true;
			if (i == a.length - 1)
				return false;
			return match(a, pattern, ++i, ++j, aIndex);
		}
		else if (a[i]/10 > 0 && a[i]/10 < 10 && pattern[j] != 1)
		{
			if (j == pattern.length - 1)
				return true;
			if (i == a.length - 1)
				return false;
			return match(a, pattern, ++i, ++j, aIndex);
		}
		else
		{
			if (i == a.length - 1)
				return false;
			j = 0;
			return match(a, pattern, ++aIndex, j, aIndex);
		}
	}
	
	public static void main(String[] args) 
	{
		int[] a = new int[] {1,1,0,1,1,1,1,0,1,1,1,1,1,0,1,1};
		zeroDistance(a);
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + "|");
		}
		System.out.println();
		
		//System.out.println(match(new int[] {2323,3, 57, 422, 3555, 355,657}, new int[] {1,0,2}));
	}

}












