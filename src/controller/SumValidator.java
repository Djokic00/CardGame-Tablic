package controller;

import model.Card;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class SumValidator {

    public boolean isValidSum(int targetSum, HashSet<Card> selectedTableCards) {
        return getValidSum(targetSum, selectedTableCards) != -1;
    }
    public int getValidSum(int targetSum, HashSet<Card> selectedTableCards) {
        int cntAce = 0;
        for (Card card : selectedTableCards) {
            if (card.getFaceName().equals("A")) cntAce++;
        }
        int N;
        boolean[][] dp;
        boolean flag = false;
        ArrayList<Integer> n = new ArrayList<>();

        for (Card card : selectedTableCards) {
            n.add(card.getValue());
        }
        while (true) {
            N = n.size();
            if (N == 0) {
               return 1;
            }
            dp = sumOfSubset(n, targetSum);
            // Ukoliko je kombiancija netacna da se proba jos jednom samo da se sad A ne racuna kao 11 vec kao 1
            if (dp[N][targetSum] == false) {
               if (cntAce > 0 && !flag) {
                   n = new ArrayList<>();
                   for (Card card : selectedTableCards) {
                       if (card.getValue() == 11) n.add(1);
                       else n.add(card.getValue());
                   }
                   flag = true;
               }
               else return -1;
            }
            else {
                List<Integer> list = returnSubset(n, dp, N, targetSum);
                for (Integer x : list) {
                    n.remove(x);
                }
            }
        }
    }

    // Sluzi za proveru da li je data kombinacija izvodljiva za dati zbir
    static boolean[][] sumOfSubset(List<Integer> masa, int targetSum) {
        int N = masa.size();
        boolean[][] dp = new boolean[N+1][targetSum+1];
        dp[0][0] = true;
        for (int m = 1; m <= targetSum; m++)
            dp[0][m] = false;
        for (int n = 1; n <= N; n++) {
            dp[n][0] = true;
            for (int m = 0; m <= targetSum; m++)
                dp[n][m] = dp[n-1][m] || masa.get(n-1) <= m && dp[n-1][m - masa.get(n-1)];
        }
        return dp;
    }

    // Sluzi da vrati elemente koji daju odgovarajuci zbir
    static List<Integer> returnSubset(List<Integer> masa, boolean[][] dp, int n, int m) {
        List<Integer> podskup = new ArrayList<>();
        while (m > 0) {
            if (dp[n][m] == false) return null;
            while (dp[n][m] == true)
                n--;
            podskup.add(masa.get(n));
            m -= masa.get(n);
        }
        return podskup;
    }
}

