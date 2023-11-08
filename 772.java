// 772. Basic Calculator III
// Implement a basic calculator to evaluate a simple expression string.

// The expression string contains only non-negative integers, '+', '-', '*', '/' operators, and open '(' and closing parentheses ')'. The integer division should truncate toward zero.

// You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].

// Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().

 

// Example 1:

// Input: s = "1+1"
// Output: 2
// Example 2:

// Input: s = "6-4/2"
// Output: 4
// Example 3:

// Input: s = "2*(5+5*2)/3+(6/2+8)"
// Output: 21

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Solution {
    public int calculate(String s) {
        
     //  ref  https://www.youtube.com/watch?v=YazIB0OZBoI
        // "5 + 2*5 * (5+5*2)/3+(6/2+8)"
         //   1. (5*2 + 5)
        // basic math operation
            // first calculate operand in the ();
            // then * or /
            // then + or -
        Stack<Integer> nums = new Stack<>();
        Stack<Character> operators = new Stack<>();
        // case 
            // digit 
            // whitespace
            // (
            // )
            // operators
            
         for (int i = 0; i < s.length(); i++) {
             char ch = s.charAt(i);
             if(Character.isDigit(ch)) {
                 int num = s.charAt(i) - '0';
                 while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                     num = 10 * num + s.charAt(i + 1) - '0';
                     i++;
                 }
                 nums.push(num);
             } else if (ch == ' ') {
                 continue;
             } else if (ch == '(') {
                 operators.push(ch);          
             } else if (ch == ')') {
                 while (operators.peek() != '(') {
                    nums.push(operate(nums, operators));                
                 }
                 operators.pop();
                 
             } else { // operators
                 while (!operators.isEmpty() && compare(ch, operators.peek()) <= 0) {
                     nums.push(operate(nums, operators));
                 }
                 operators.push(ch);
             }     
         }
        while (!operators.isEmpty()) {
            nums.push(operate(nums, operators));
        }
        return nums.pop();
    }
    private int compare(char a,  char b) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('(', -1);
        map.put('+', 0);
        map.put('-', 0);
        map.put('*', 1);
        map.put('/', 1);
        return map.get(a) - map.get(b);
    }
    private int operate(Stack<Integer> nums, Stack<Character> operators) {
        int a = nums.pop();
        int b = nums.pop();
        char op = operators.pop();
        
        switch (op) {
            case '+': return b + a;
            case '-': return b - a;
            case '*': return b * a;
            case '/': return b / a;
            default: return 0;
        }
    }
}