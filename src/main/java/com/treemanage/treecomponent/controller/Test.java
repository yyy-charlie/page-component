package com.treemanage.treecomponent.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @ClassName Test
 * @Description TODO
 * @Author ycn
 * @Date 2020-04-07
 **/
public class Test {
    public static final Map<Character, Character> map = new HashMap<Character, Character>() {{
        put('{', '}');
        put('[', ']');
        put('(', ')');
        put('?', '?');
    }};

    public static boolean isValid(String s) {
        if (s.length() > 0 && !map.containsKey(s.charAt(0))) {
            return false;
        }
        LinkedList<Character> statck = new LinkedList<Character>() {{
            add('?');
        }};
        for (Character c : s.toCharArray()) {
            if (map.containsKey(c)) {
                statck.addLast(c);
            } else if (map.get(statck.removeLast()) != c) {
                return false;
            }
        }
        return statck.size() == 1;
    }

    public static void main(String[] args) {
        System.out.println(isValid("[{()}]"));
    }
}
