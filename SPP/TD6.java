/*
I
  1)
    @ requires(\forall int i; 0 <= i && i < n;(j <= i && i<= k)==> b[i]==0)
  2)
    @ requires(\forall int i; i >= 0 && i < n; b[i]==0 ==> i >= j && i <= k)
  3)
    @ requires(!(\forall int i; i >= 0 && i < n; b[i]==0 ==> i >= j && i <= k))
    @ requires(\exist int i; i >= 0 && i< n; b[i]==0 && (i < j || i > k))
  4)
    @ requires()
*/


public class Tp6 {
  /*@ requires true;
   */
  void ex1(int j, int k, int[] b){

  }

  /*@ ensures (\result >= -1 && \result < t.length);
      ensures !(\exists int i; i >= 0 && i < t.length; t[i] ==x) <==> \result == -1;
      ensures \result != -1 ==> t[\result] == x;
      diverges false;
   */
  public static int recherche(int[] t, int x) {
    int i;
    /*@ loop invariant;
        assignable
    for(i=0;i<t.length;i++){
      if(t[i] == x){
        return i;
      }
    }
    return -1;
  }
}
