
public class TreeNode {
    private QueueNode value;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(QueueNode value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public TreeNode(QueueNode value, TreeNode left, TreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public QueueNode getValue() {
        return value;
    }

    public void setValue(QueueNode value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
