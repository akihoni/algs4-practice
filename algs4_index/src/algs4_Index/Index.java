package algs4_Index;

import edu.princeton.cs.algs4.*;

import java.util.*;

public class Index 
{
    public static void main(String[] args) 
    {
        ArrayList<String> passage = new ArrayList<>();
        In In = new In("test.txt");
        In In2 = new In("query.txt");
        int count = 0;
        
        while(In.hasNextLine()){
            String[] a = In.readLine().split(" ");
            for (String word : a){
                passage.add(word);
                count ++;
            }
        }
       // System.out.println(passage);
        BinarySearchST<String,ArrayList<Integer>> ST = new BinarySearchST<>(count);
        for(int i=0;i<count;i++){
            if(ST.contains(passage.get(i))){
            	ST.get(passage.get(i)).add(i);
            }else{
            	ST.put(passage.get(i),new ArrayList<>());
            	ST.get(passage.get(i)).add(i);
            }
            //System.out.println(ST.get(passage.get(i))+" "+passage.get(i));
        }
        while(In2.hasNextLine()){
            String[] word = In2.readLine().split(" ");
            if(!ST.contains(word[0])){
            	System.out.println("no such word");
            	continue;
            }
            boolean flag = false;
            if(word.length == 1){
                for (Integer index : ST.get(word[0])){
                    System.out.println(index+1+" "+word[0]);
                    break;
                }
                continue;
            }
            for(Integer index : ST.get(word[0])){
                for(int i=0;i<word.length;i++){
                    if(!(passage.get(index+i).equals(word[i]))) break;
                    if(i==word.length-1) flag = true;
                }
                if(flag){
                    System.out.print(index+1+" ");
                    for(int i =0;i<word.length;i++) System.out.print(word[i]+" ");
                    System.out.println();
                    break;
                }
            }
            if(!flag){
                System.out.print("-- ");
                for(int i =0;i<word.length;i++) System.out.print(word[i]+" ");
                System.out.println();
            }
        }
    }
}
