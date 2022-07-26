package com.company.project.util;

import java.util.Stack;

/**
 * Created on 2022/7/5.
 *
 * @author huyan
 * @menu
 */
public class LeetCodeTestCase {

    /**
     *  有效括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Stack<Character> brackets  = new Stack<Character>();
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            switch(c){
                case '(':
                case '[':
                case '{':
                    brackets.push(c);
                    break;
                case ')':
                    if(!brackets.empty()){
                        if(brackets.peek()== '('){
                            brackets.pop();
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                    break;
                case ']':
                    if(!brackets.empty()){
                        if(brackets.peek()=='['){
                            brackets.pop();
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                    break;
                case '}':
                    if(!brackets.empty()){
                        if(brackets.peek()=='{'){
                            brackets.pop();
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
            }
        }
        return brackets.empty();
    }


}
