package com.xyz.generator.api.dom.java;

import java.util.HashSet;
import java.util.Set;

/**
 * 类: JavaReservedWords <br>
 * 描述: Java语言保留关键字<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月01日 18:30
 */
public class JavaReservedWords {

    private static Set<String> RESERVED_WORDS;

    static {
        String[] words = {
                "abstract",
                "assert",
                "boolean",
                "break",
                "byte",
                "case",
                "catch",
                "char",
                "class",
                "const",
                "continue",
                "default",
                "do",
                "double",
                "else",
                "enum",
                "extends",
                "final",
                "finally",
                "float",
                "for",
                "goto",
                "if",
                "implements",
                "import",
                "instanceof",
                "int",
                "interface",
                "long",
                "native",
                "new",
                "package",
                "private",
                "protected",
                "public",
                "return",
                "short",
                "static",
                "strictfp",
                "super",
                "switch",
                "synchronized",
                "this",
                "throw",
                "throws",
                "transient",
                "try",
                "void",
                "volatile",
                "while"
        };

        RESERVED_WORDS = new HashSet<String>(words.length);

        for (String word : words){
            RESERVED_WORDS.add(word);
        }
    }

    public static boolean containsWord(String word){
        boolean rc;
        if (word == null){
            rc = false;
        }else{
            rc = RESERVED_WORDS.contains(word);
        }

        return rc;
    }


    private JavaReservedWords() {
    }
}
