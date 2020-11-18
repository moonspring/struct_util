package com.lzq.struct.util.diff;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import com.lzq.struct.enums.TagEnum;

import java.util.Arrays;
import java.util.List;

public class MyersDiff {

    public static void main(String[] args) {

        try {

//            String a = "ABCABBACDAB";
//            String b = "CBABACDAA";

            List<String> text0 = Arrays.asList("A","B","C","A","B","B","A","C","D","A","B");
            List<String> text1 = Arrays.asList("C","B","A","B","A","C","D","A","A");


            /* Myers差分算法的配置信息 */
            DiffRowGenerator generator = DiffRowGenerator.create()
                    .showInlineDiffs(true) /* 展示差异 */
                    .mergeOriginalRevised(true) /* 单行合并：启用change标记，默认支持Insert和Delete */

                    .ignoreWhiteSpaces(true) /* 忽略空格 */
                    .inlineDiffByWord(false) /* 区分大小写 */
                    .oldTag(f -> TagEnum.delTag.getTag())      //introduce markdown style for strikethrough
                    .newTag(f -> TagEnum.addTag.getTag())     //introduce markdown style for bold
                    .build();

            //compute the differences for two test texts.
            List<DiffRow> rows = generator.generateDiffRows(text0,text1);

            System.out.println(rows);

        } catch (DiffException e) {
            e.printStackTrace();
        }

    }
}
