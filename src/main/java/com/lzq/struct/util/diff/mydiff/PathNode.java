package com.lzq.struct.util.diff.mydiff;

import lombok.Data;

@Data
public class PathNode {

    /**
     * Position in the original sequence.
     */
    public final int i;
    /**
     * Position in the revised sequence.
     */
    public final int j;
    /**
     * The previous node in the path.
     */
    public final PathNode prev;

    public final boolean snake;

    public PathNode(int i, int j, PathNode prev, boolean snake) {
        this.i = i;
        this.j = j;
        this.prev = prev;
        this.snake = snake;
    }
}
