import java.util.ArrayList;
public class AVLTree implements AVLTreeADT{
    private int size;
    private AVLTreeNode root;
    public AVLTree(){
        this.size = 0;
        this.root = new AVLTreeNode();
    }
    public void setRoot(AVLTreeNode node){
        this.root = node;
    }
    public AVLTreeNode root(){
        return root;
    }
    public boolean isRoot(AVLTreeNode node){
        if(node.getParent()==null){
            return true;
        }
        else{
            return false;
        }
    }
    public int getSize(){
        return size;
    }
    public AVLTreeNode get(AVLTreeNode node, int key){
        if(node.isLeaf()){
            return node;
        }
        else{
            if(node.getKey() == key){
                return node;
            }
            else if(node.getKey()<key){
                return get(node.getRight(),key);
            }
            else{
                return get(node.getLeft(),key);
            }
        }
    }
    public AVLTreeNode smallest(AVLTreeNode node){
        if(node.isLeaf()){
            return null;
        }
        else{
            AVLTreeNode p = node;
            while(p.getLeft()!=null && p.getRight() != null){
                p = p.getLeft();
            }
            return p.getParent();
        }
    }
    public AVLTreeNode put(AVLTreeNode node, int key, int data) throws TreeException{
        AVLTreeNode p = get(node, key);
        if(!p.isLeaf()){
            throw new TreeException("error");
        }
        else{
        p.setKey(key);
        p.setData(data);
        AVLTreeNode l = new AVLTreeNode(p);
        AVLTreeNode r = new AVLTreeNode(p);
        p.setLeft(l);
        p.setRight(r);
        size++;
        }
        return p;

    }
    public AVLTreeNode remove(AVLTreeNode node, int key) throws TreeException {
        AVLTreeNode cur_node = get(node, key);
        AVLTreeNode parent = cur_node.getParent();
        boolean equalheight = false;
        if (!cur_node.isRoot()){
            if(parent.getRight().getHeight() == parent.getLeft().getHeight()){
                equalheight = true;
            }
        }
        if(cur_node.isLeaf()){
            throw new TreeException("there is no such node");
        }
        else {
            if (cur_node.getLeft().isLeaf() || cur_node.getRight().isLeaf()){
                AVLTreeNode remain_node = new AVLTreeNode();
                if(cur_node.getLeft().isLeaf()){
                    remain_node = cur_node.getRight();
                    size--;
                }
                else{ remain_node = cur_node.getLeft();
                    size--;
                }
                if(cur_node.isRoot()){
                    this.root = remain_node;
                    root.setParent(null);
                }
                else{
                    if(parent.getRight()==cur_node){
                        parent.setRight(remain_node);
                        if (cur_node.getHeight()+1 == parent.getHeight() && !equalheight){
                            parent.setHeight(parent.getHeight()-1);
                        }
                    }
                    else {
                        parent.setLeft(remain_node);
                        if (cur_node.getHeight()+1 == parent.getHeight() && !equalheight ){
                            parent.setHeight(parent.getHeight()-1);
                        }}
                    remain_node.setParent(parent);
                }
            }
            else {
                AVLTreeNode small = smallest(cur_node.getRight());
                cur_node.setData(small.getData());
                cur_node.setKey(small.getKey());
                remove(small, small.getKey());
            }
            return cur_node;
        }

//        AVLTreeNode p = get(node, key);
//        AVLTreeNode temp = p;
//        if (p.isLeaf()) {
//            throw new TreeException("error");
//        }
//        else {
//            if(p.isRoot()){
//                if(p.getLeft().isLeaf()){
//                    AVLTreeNode c = p.getRight();
//                    this.root = c;
//                }
//                else{
//                    AVLTreeNode c = p.getLeft();
//                    this.root = c;
//
//                }
//            }
//
//            else if (p.getKey() > p.getParent().getKey()) {
//                if (p.getLeft().isLeaf()) {
//                    AVLTreeNode c = p.getRight();
//                    p = p.getParent();
//                    c.setParent(p);
//                    p.setRight(c);
//                    size--;
//
//                }
//                else if (p.getRight().isLeaf()) {
//                    AVLTreeNode c = p.getLeft();
//                    p = p.getParent();
//                    c.setParent(p);
//                    p.setRight(c);
//                    size--;
//                }
//                else {
//                    AVLTreeNode s = smallest(p.getRight());
//                    p.setKey(s.getKey());
//                    p.setData(s.getData());
//                    remove(s, s.getKey());
//                }
//
//            }
//            else{
//                if (p.getLeft().isLeaf()) {
//                    AVLTreeNode c = p.getRight();
//                    p = p.getParent();
//                    c.setParent(p);
//                    p.setLeft(c);
//
//                    size--;
//
//                }
//                else if (p.getRight().isLeaf()) {
//                    AVLTreeNode c = p.getLeft();
//                    p = p.getParent();
//                    c.setParent(p);
//                    p.setLeft(c);
//
//                    size--;
//                } else {
//                    AVLTreeNode s = smallest(p.getRight());
//                    p.setKey(s.getKey());
//                    p.setData(s.getData());
//                    remove(s, s.getKey());
//                }
//            }
//        }
//        return p;

        }

    public ArrayList<AVLTreeNode> inorder(AVLTreeNode node){
        ArrayList A = new ArrayList();
        inorderRec(node, A);
        return A;

    }
    public void inorderRec(AVLTreeNode node, ArrayList<AVLTreeNode> list){
        if(node.isLeaf()){
            return;
        }
        inorderRec(node.getLeft(),list);
        list.add(node);
        inorderRec(node.getRight(),list);
    }
    public void recomputeHeight(AVLTreeNode node) {
        if (node.isLeaf()) {
            return;
        } else if (node.getRight().isLeaf() && node.getLeft().isLeaf()) {
            node.setHeight(1);
            return;
        } else {
            recomputeHeight(node.getLeft());
            recomputeHeight(node.getRight());
            int max_height = Math.max(node.getLeft().getHeight() + 1, node.getRight().getHeight() + 1);
            node.setHeight(max_height);
        }
    }
//        if(node.isInternal()) {
//            node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
//        }
//    }

    public void rebalanceAVL(AVLTreeNode r, AVLTreeNode v){
        AVLTreeNode pa = v.getParent(); //pre-store the parent of node v

        boolean onleft = true;
        if (!v.isRoot()){
            if(v.getParent().getRight()==v){// check if v , the first unbalanced subtree is the left or right child of its parent, set true if it is left child
                onleft = false;
            }}
        AVLTreeNode y = taller(v, onleft);
        if ( y == v.getLeft()){
            onleft = true;
        }
        else{
            onleft = false;
        }
        AVLTreeNode x = taller(y, onleft);
        AVLTreeNode subRoot = rotation(v, y, x);

        if (v.isRoot()){
            this.root = subRoot;
            subRoot.setParent(null);
        }
        recomputeHeight(subRoot);
        subRoot.setParent(pa);
        int count=1;
        while(pa != null){
            pa.setHeight(subRoot.getHeight()+count);
            pa = pa.getParent();
            count++;
        }


    }



    public void putAVL(AVLTreeNode node, int key, int data) throws TreeException{
        AVLTreeNode p =put(node, key, data);
        if (p.isRoot()){
            p.setHeight(1);
        }
        else{
            while(p!=null && Math.abs(p.getLeft().getHeight()-p.getRight().getHeight())<=1 ){
                recomputeHeight(p);
                p = p.getParent();
            }
            if (p!=null){
                rebalanceAVL(node, p);
            }
        }

    }
    public void removeAVL(AVLTreeNode node, int key) throws TreeException{
        AVLTreeNode p = remove(node, key);
        while(p!=null){
            if(Math.abs(p.getLeft().getHeight()-p.getRight().getHeight())>1){
                rebalanceAVL(root,p);
            }
            p = p.getParent();
        }

    }
    public AVLTreeNode taller(AVLTreeNode node, boolean onLeft){

        if(node.getLeft().getHeight()>node.getRight().getHeight()){
            return node.getLeft();
        }
        else if(node.getLeft().getHeight()<node.getRight().getHeight()){
            return node.getRight();
        }
        else{
            if(onLeft){
                return node.getLeft();
            }
            else{
                return node.getRight();
            }


        }

    }
    public AVLTreeNode rotate(AVLTreeNode node) {
        AVLTreeNode p = node.getParent();
        AVLTreeNode gP = p.getParent();
        AVLTreeNode nodeRight = node.getRight();
        AVLTreeNode nodeLeft = node.getLeft();
        if(node.getParent().isInternal()) {


            if (p.getLeft() == node) {

                node.setRight(p);

                p.setParent(node);

                p.setLeft(nodeRight);
                nodeRight.setParent(p);
                node.setParent(gP);
                if(gP!=null&&gP.getLeft()==p){
                    gP.setLeft(node);

                }
                else if(gP!=null&& gP.getRight()==p){
                    gP.setRight(node);
                }

                if(root.getKey()==p.getKey()){
                    root=node;
                    recomputeHeight(root);

                }

            }
            // right
            else if (p.getRight() == node) {

                node.setLeft(p);

                p.setParent(node);

                p.setRight(nodeLeft);
                nodeLeft.setParent(p);
                node.setParent(gP);

                if(gP!=null&&gP.getLeft()==p){
                    gP.setLeft(node);

                }
                else if(gP!=null&& gP.getRight()==p){
                    gP.setRight(node);
                }
                if(root.getKey()==p.getKey()){
                    root=node;
                    recomputeHeight(root);
                }




            }





        }
        recomputeHeight(node);
        return node;


    }



    public AVLTreeNode rotation(AVLTreeNode z, AVLTreeNode y, AVLTreeNode x){
        recomputeHeight(z);
        if(x.getKey()>y.getKey()&&y.getKey()>z.getKey()||x.getKey()<y.getKey()&&y.getKey()<z.getKey()){
            rotate(y);
            return y;
        }
        else{
            rotate(x);
            rotate(x);
            return x;
        }

    }
//    private AVLTreeNode leftLeftRotation(AVLTreeNode k2) {
//        AVLTreeNode k1 = new AVLTreeNode();
//
//    }


}
