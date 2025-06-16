import java.util.*;

public class Main {

    public static boolean checkAnagram(String str1, String str2){
        if(str1.length() != str2.length()){
            return false;
        }
        Map<Character, Integer> freqMap = new HashMap<>();
        for(char c: str1.toCharArray()){
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        for(char c: str2.toCharArray()){
            if(!freqMap.containsKey(c)){
                return false;
            }
            else {
                freqMap.put(c, freqMap.get(c) - 1);
            }
            if(freqMap.get(c) == 0){
                freqMap.remove(c);
            }
        }
        return freqMap.isEmpty();
    }
    public static void main(String[] args) {

        System.out.println(checkAnagram("earth", "earth"));

//        int first = Integer.MIN_VALUE;
//        int second = Integer.MIN_VALUE;

//        for(int num: arr){
//            if(num>first){
//                second = first;
//                first = num;
//            } else if(num>second && num<first){
//                second = num;
//            }
//        }
//        if(second == Integer.MIN_VALUE){
//            System.out.println("-1");
//        }
//        else {
//            System.out.println(second);
//        }

//        int[] arr = new int[]{5,2,6,1,2,4,8,8};
//        Optional<Integer> secondHighest = Arrays.stream(arr)
//                .boxed()
//                .distinct()
//                .sorted(Comparator.reverseOrder())
//                .skip(1)
//                .findFirst();
//
//        if(secondHighest.isPresent()){
//            System.out.println(secondHighest.get());
//        }
//        else {
//            System.out.println("-1");
//        }

    }
}