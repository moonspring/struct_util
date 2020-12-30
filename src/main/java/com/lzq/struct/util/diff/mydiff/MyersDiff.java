package com.lzq.struct.util.diff.mydiff;

import com.github.difflib.algorithm.DifferentiationFailedException;
import com.github.difflib.patch.Patch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * @author adam
 */
public class MyersDiff<T> {

    /**
     * 默认相等规则
     */
    private final BiPredicate<T, T> DEFAULT_EQUALIZER = Object::equals;
    private final BiPredicate<T, T> equalizer;

    public MyersDiff() {
        equalizer = DEFAULT_EQUALIZER;
    }

    public MyersDiff(final BiPredicate<T, T> equalizer) {
        Objects.requireNonNull(equalizer, "equalizer must not be null");
        this.equalizer = equalizer;
    }

    /**
     * Computes the minimum diffpath that expresses de differences between the original and revised
     * sequences, according to Gene Myers differencing algorithm.
     *
     * @param orig The original sequence.
     * @param rev The revised sequence.
     * @return A minimum {@link PathNode Path} accross the differences graph.
     * @throws DifferentiationFailedException if a diff path could not be found.
     */
    public PathNode buildPath(final List<T> orig, final List<T> rev)
            throws DifferentiationFailedException {
        Objects.requireNonNull(orig, "original sequence is null");
        Objects.requireNonNull(rev, "revised sequence is null");

        // these are local constants
        final int N = orig.size();
        final int M = rev.size();

        /* 最大步数 */
        final int MAX = N + M + 1;
        final int size = 1 + 2 * MAX;
        final int middle = size / 2;

        /* 构建走坐标数组（用于存储每一步的最优路径位置） */
        final PathNode diagonal[] = new PathNode[size];

        /* 用于获取初始位置的辅助节点 */
        diagonal[middle + 1] = new PathNode(0, -1, null, true);

        /* 外层循环，步数 */
        for (int d = 0; d < MAX; d++) {

            /* 内存循环 当前位置斜率，以2为步长， 因为从所在位置走一步，斜率只会相差2 */
            for (int k = -d; k <= d; k += 2) {

                /* 找出对应斜率所在位置，以及它上一步的位置（高位 低位） */
                final int kmiddle = middle + k;
                final int kplus = kmiddle + 1;
                final int kminus = kmiddle - 1;
                PathNode prev;
                int i;

                /* 若k=-d，则一定是从上往下走，即i相同
                * 若diagonal[kminus].i < diagonal[kplus].i，则最优路径一定是从上往下走，即i相同
                * */
                if ((k == -d) || (k != d && diagonal[kminus].i < diagonal[kplus].i)) {
                    i = diagonal[kplus].i;
                    prev = diagonal[kplus];
                } else {

                    /* 若k=d，则一定是从左往右走，即i+1
                    * 若diagonal[kminus].i = diagonal[kplus].i，则最优路径一定是从左往右，即i+1
                    *  */
                    i = diagonal[kminus].i + 1;
                    prev = diagonal[kminus];
                }
                /* 上一步的低位数据不再存储在数组中（每个k只清空低位即可全部清空） */
                diagonal[kminus] = null; // no longer used

                /* 根据i和k，计算出来j */
                int j = i - k;

                /* 当前是diff节点 */
                PathNode node = new PathNode(i, j, prev, false);

                /* 判断比较的两个数组中，当前位置的数据是否相同。相同，则到对角线位置 */
                while (i < N && j < M && equalizer.test(orig.get(i), rev.get(j))) {
                    i++;
                    j++;
                }

                /* 判断是否到对角线位置，若是，则生成snack节点，前节点为diff节点 */
                if (i > node.i) {
                    node = new PathNode(i, j, node,true);
                }

                /* 设置当前位置到数组中 */
                diagonal[kmiddle] = node;

                /* 达到目标位置，返回当前节点 */
                if (i >= N && j >= M) {
                    return diagonal[kmiddle];
                }
            }
            diagonal[middle + d - 1] = null;
        }

//        return diagonal[0];
        // According to Myers, this cannot happen
        throw new DifferentiationFailedException("could not find a diff path");
    }



    /**
     * Constructs a {@link Patch} from a difference path.
     *fdfdjfeifjefwj
     * @param path The path.
     * @param orig The original sequence.
     * @param rev The revised sequence.
     * @return A {@link Patch} script corresponding to the path.
     * @throws DifferentiationFailedException if a {@link Patch} could not be built from the given
     * path.
     */
    public List<String> buildRevision(PathNode path, List<T> orig, List<T> rev) {

        List<String> result = new ArrayList<>();

        Objects.requireNonNull(path, "path is null");
        Objects.requireNonNull(orig, "original sequence is null");
        Objects.requireNonNull(rev, "revised sequence is null");

        while (path != null && path.prev != null && path.prev.j >= 0) {

            if (path.isSnake()) {

                int endi = path.i;
                int begini = path.prev.i;

                for (int i = endi-1; i >=begini ; i--) {
                    result.add("  "+orig.get(i));
                }
            }else {
                int i = path.i;
                int j = path.j;
                int prei = path.prev.i;

                if (prei<i){
                    result.add("- "+orig.get(i-1));
                }else {
                    result.add("+ "+rev.get(j-1));
                }
            }
            path = path.prev;
        }
        Collections.reverse(result);
        return result;
    }


}
