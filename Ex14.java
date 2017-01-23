/**
 * This class is the answer to Exercise 14 at the course "Introduction to Computer science and Java programming language" in the Open University of Israel.
 * @author Gil Mansharov
 * @ID 313260192
 */
public class Ex14 {
	/**
	 * This method gets a number and a modulo number which we want to get modulo from, and get it's positive modulo as if the modulo is non-nagetive,
	 * it will be returned, and if it is negative, it will return the: the negative modulo + the modulo variable.
	 * @param num the number.
	 * @param modulo the modulo number.
	 */
	private static int floorMod(int num, int modulo) // Time complexity: O(1)
	{
		num %= modulo;
		return num >= 0 ? num : num + modulo;
	}

	/**
	 * 1.a. The function returns the maximal contiguous sub-array length which its sum divided by 3 without residue.
	 * 1.b. Complexity: O(n^3).
	 * 1.c. See function 'what' above.
	 * 1.d. Complexity: O(n).
	 * only 1 "for" loop, in comparison to the previous function, the private 'f' function is O(n), which makes the 'what' function O(n^3).
	 * I didn't use the 'f' function, instead, I summed the array, as each iteration I save the sum of the array from 0 to the current index,
	 * and save the current modulo of this sum as the "last occurrence of the sum that divided by 3 with this residue",
	 * this data is saved in 3 variables, lastZero, lastOne and lastTwo. Until the end of the array we'll have the last occurrences of this information.
	 * Now we're looking on 3 sub-arrays; the first one is from the first index of the array to the last index of modulo 3 divided sum.
	 * The second sub-array is from the first element that divided by 3 with residue of 1, to the last element that divided by 3 with residue 1.
	 * The third sub-array is the same as the second, but with the residue of 2.
	 * now that we have these 3 sub-arrays limits, we'll calculate the longest sub-array- this sub array is the sub array we're looking for and it's length will be returned.
	 */
	public static int what(int[] a) // Time Complexity: O(n), Memory Complexity: O(1)
	{
		if (a == null)
			return 0;
		int firstZero = -1, lastZero = -1;
		int firstOne = -1, lastOne = -1;
		int firstTwo = -1, lastTwo = -1;
		int sum = 0;
		for (int i = 0; i < a.length; i++)
		{
			sum += a[i];
			if (firstOne == -1)
				firstOne = (floorMod(sum, 3) == 1) ? i : firstOne;
			if (firstTwo == -1)
				firstTwo = (floorMod(sum, 3) == 2) ? i : firstTwo;
			switch(floorMod(sum, 3))
			{
				case 0:
					lastZero = i;
					break;
				case 1:
					lastOne = i;
					break;
				case 2:
					lastTwo = i;
					break;
			}
		}
		int zero = lastZero - firstZero;
		int one = lastOne - firstOne;
		int two = lastTwo - firstTwo;

		return Math.max(zero, Math.max(one, two));
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
		if (a == null)
			return;
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
	 * I overloaded the original method with a method that gets 4 arguments- the two Strings, and an index per string (integer i for string s, and integer j for string t).
	 * @param s The original string.
	 * @param t The string we check for transformation from s.
	 * @return true if the String t is a transformation of String s, otherwise, returns false.
	 */
	public static boolean isTrans(String s, String t)
	{
		if (s == null && t == null)
			return true;
		else if (s == null)
			return false;
		else if (t == null)
			return false;
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
		if (pattern == null || pattern.length == 0)
			return true;
		else if (a == null || a.length < pattern.length)
			return false;
		return match(a, pattern, 0, 0, 0);
	}

	/**
	 * The method always treats the array as a "collection" of sub-arrays with a length of the pattern array.
	 * The method iterates every sub-array and checks if it fits the pattern, if it fits, returns true.
	 * if we reached the end of the array or reached a sub-array which its length can't be longer than the pattern
	 * array, the method returns false.
	 * @param a array a
	 * @param pattern pattern array
	 * @param i the index for the checked sub-array of array a
	 * @param j the index for the pattern array
	 * @param aIndex the index of the beginning of the current sub-array.
	 * @return True if the there is a sub-array that matches the pattern, otherwise false.
	 */
	private static boolean match(int[] a, int[] pattern, int i, int j, int aIndex)
	{
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
	}
}