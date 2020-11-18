package com.lzq.struct.util.algorithm;

public class KMPTest {


    public static void main(String[] args) {
        String str = "BBC ABCDAB ABCDABCDABDE";
        String model = "ABCDABD";

        int index = violentMatch(str,model);
        System.out.println(index);
    }

    // 1、暴力匹配算法
    public static int violentMatch(String str,String model){

        int intLength = str.length();
        int intModel = model.length();

        int i = 0;
        int j = 0;
        while (i<intLength && j<intModel){

//            if(String.valueOf(str.charAt(i)).equals(String.valueOf(model.charAt(j)))){
            if (str.charAt(i)==model.charAt(j)){
                i++;
                j++;
            }else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j==intModel){
            return i-j;
        }else {
            return -1;
        }
    }

    // 2、kmp算法【含优化next数组后的算法】
    public static int KMPSearch(String str,String model){

        int intLStr = str.length();
        int intLModel = model.length();

        // 获取next数组
        int[] next = getNext(model,intLModel);

        //原始串，匹配到第i个序号
        int i = 0;

        // 模式串匹配到第j位。初始值为0
        int j = 0;
        while (i<intLStr && j<intLModel){

            // 如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++
            if (j==-1 || str.charAt(i)==model.charAt(j)){
                i++;
                j++;
            }else {
                // 如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j]
                j = next[j];
            }
        }
        if (j==intLModel){
            return intLStr - intLModel;
        }else {
            return -1;
        }

    }


    // 获取模式串的next数组
    public static int[] getNext(String model,int mLength){
        int[] next = new int[mLength];

        // next值与k类似，next不同第数组值，即等于j不同编号时，k的值。可以把k理解成一个临时变量
        next[0] = -1;

        // k表示模式串匹配到 模式串的第j位 出现不匹配时，模式串前j-1长度的串 的公共前缀后缀子串长度
        // 初始值为-1，为了方便计算。即开始时，初值设定。
        int k = -1;

        // 模式串匹配到第j位。初始值为0
        int j = 0;
        while (j < mLength-1){

            // model.charAt(k)表示前缀；
            // model.charAt(j)表示后缀。
            if (k==-1 || model.charAt(k)==model.charAt(j)){
                ++k;
                ++j;

                // next[j] = k;     //之前只有这一行，可用下面四行优化

                //因为不能出现p[j] = p[ next[j ]]，所以当出现时需要继续递归，k = next[k] = next[next[k]]
                if (model.charAt(next[k])==model.charAt(j)){
                    next[j] = next[k];
                }else {
                    next[j] = k;  //之前只有这一行
                }
            }else {
                k = next[k];
            }
        }

        return next;
    }



}
