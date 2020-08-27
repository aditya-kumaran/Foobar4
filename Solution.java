import java.util.ArrayList;

public class Solution {
    static int[][] pairs;
    static int[] taken;
    static int matched;
    public static int solution(int[] banana_list) {       
        
        
        // matching the pairs
        int length = banana_list.length;
        taken = new int[length];
        pairs = new int[length][length];
        
        for (int i=0;i<=length-2;i++)
        {
            pairs[i][i] =0;
            for (int j=i+1;j<=length-1;j++)
            {
                if (banana_list[i]!=banana_list[j])
                {
                    int sum = banana_list[i]+banana_list[j];
                    int gcd = gcd(banana_list[i], banana_list[j]);
                    if (check(sum,gcd)==true)
                    {
                        pairs[i][j] = 1;
                        pairs[j][i] = 1;
                    }
                    else
                    {
                        pairs[i][j]=0;
                        pairs[j][i]=0;
                    }
                }
            }
        }
        // printing pairs
        
        for (int row =0; row<length; row++)
        {
            for (int col=0; col<length; col++)
            {
                System.out.print(pairs[row][col]+",");
            }
            System.out.println();
        }
        
        // counting number of pairs for each guard
        for (int i=0; i<length;i++)
        {
            taken [i] = 0;
            for (int j=0; j<length;j++)
            {
                taken[i]+= pairs[i][j];
            }
        }
        // comparing pairs with taken
        matched = 0;
        int done = 0;
        while (done==0)
        {
            done = matchnext();
        }


        return length-matched;
    }
    
    public static boolean check(int sum, int gcd)
    {
        int fact = sum/gcd;
        
        return ((fact-1) & fact) != 0;
    }
    
    public static int gcd (int a, int b)
    {
        if (a<b)
        {
            int high = b;
            b = a;
            a = high;
        }
        if (b==0)
        {
            return a;
        }
        return gcd (b, a%b);    
    }
    
    public static void compensate (int index)
    {
        for (int y=0; y<pairs.length; y++)
        {
            pairs[y][index] = 0;
            pairs[index][y] = 0;
        }
        
        // printing pairs
        
        for (int row =0; row<pairs.length; row++)
        {
            for (int col=0; col<pairs.length; col++)
            {
                System.out.print(pairs[row][col]+",");
            }
            System.out.println();
        }
        System.out.println();
        // counting number of pairs for each guard
        for (int i=0; i<pairs.length;i++)
        {
            taken [i] = 0;
            for (int j=0; j<pairs.length;j++)
            {
                taken[i]+= pairs[i][j];
            }
        }
    }
    
    public static int match (int index)
    {
        int matched_index = -1;
        int lowest_taken = pairs.length;
        for (int h=0; h<pairs.length; h++)
        {
            if (pairs[index][h]==1)
            {
                if (taken[h]<lowest_taken)
                {
                    matched_index = h;
                    lowest_taken = taken[h];
                }
            }
        }
        return matched_index;
    }
    
    public static int matchnext ()
    {
        int temp = matched;
        for (int numpairs = 1; numpairs < pairs.length; numpairs ++)
        {
            for (int index = 0; index < pairs.length ; index ++)
            {
                if (taken[index] == numpairs)
                {
                    int matched_index = match(index);
                    compensate(index);
                    compensate(matched_index);
                    matched += 2;
                }
            }
        }
        
        if (temp==matched)
        {
            return 1;
        }
        
        return 0;
    }
}