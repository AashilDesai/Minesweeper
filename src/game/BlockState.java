package game;

/**
 * This enum indicates the state of a Block.
 * Flag means there is a flag on top
 * Covered means that the block's number is not showing
 * UNCOVERED means that the block's number/bomb is showing
 */

public enum BlockState {
	FLAG, COVERED, UNCOVERED
}
