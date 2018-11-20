package com.altman29.arraylib;

/**
 * Created by Csy on 2018/11/20.
 * e-mial:chensiyuan@duozhuan.cn
 * Desc: 数组的二次封装
 */
public class Array {
    /**
     * 操作的数组
     */
    private int[] data;

    /**
     * 当前数据的大小。修改元素时，需要维护
     * 即表示数组中有多少个元素，同时也指向了数组中第一个没有元素的位置
     * 向数组末尾添加一个元素时，就是向size的位置添加一个元素
     * 关于index的合法性检测，只有add中 index==size合法，因为这是相当于想末尾添加元素。而其他情况是不能=的。size = 最大的index+1
     */
    private int size;

//    其实就是data的length 不需创建
//    private int capacity;

    /**
     * 构造函数，传入数组的容量capacity构造Array
     * 也可以做传入数组，变换成自己的数组-待实现
     *
     * @param capacity
     */
    public Array(int capacity) {
        data = new int[capacity];
        size = 0;
    }

    /**
     * 无参构造，默认10
     */
    public Array() {
        this(10);//用户不知道创建大小时，默认10
    }

    /**
     * 获取当前数组元素个数
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组的容量
     *
     * @return
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 返回数组是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向数组中添加一个元素
     * 过时，有调用add的方法实现
     *
     * @param e
     */
    public void _addLast(int e) {
        //1.查询容量是否可以添加
        if (size == data.length) {
            //没有容量，添加失败，先抛出异常
            throw new IllegalArgumentException("AddLast failed. Array is full.");
        }
//        data[size] = e;
//        size++;
        //也可以这也写，但是不推荐，一行只执行一种操作
        data[size++] = e;
    }


    /**
     * 向第index个位置插入一个新元素e     //从后往前挪
     *
     * @param index
     * @param e
     */
    public void add(int index, int e) {
        //1.查询容量是否可以添加
        if (size == data.length) {
            //没有容量，添加失败，先抛出异常
            throw new IllegalArgumentException("Add failed. Array is full.");
        }

        //2.index 合法检测
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size");
        }

        //从后往前挪（控制循环多少次）
        for (int i = size - 1; i >= index; i--) {
            //把前一个元素值赋给后一个元素，这样循环完，index位置元素就空出来了。但是还是存放之前的元素，不过此时已经可以覆盖了
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
        //此时，addLast方法就可以调用add方法了。
        /*  注意：
        整个数组一共有size个元素，最后一个元素的索引为size-1。
        这个循环从最后一个元素(data[size-1])开始，将其前面，直到index位置的元素，都向后挪一个位置，腾出空间，给新加入的元素。
        在第一次调用，或者待添加元素放到整个数组末尾，即index == size时，这个循环是进不去的，可以想一下，此时循环开始的条件是
        i=size-1，而循环进行的条件是i>=index，初始，i就小于了index了，所以循环进不去。 可以这样理解，当要把新元素添加到数组最后
        一个位置时，是不需要挪动数组的任何一个元素，给新元素腾位置的，直接把新元素放到当前末尾就好。即，此时直接执行
        data[index] =e，此时的index=size.
        */
    }

    /**
     * 向所有元素后添加一个新元素
     *
     * @param e
     */
    public void addLast(int e) {
        add(size, e);
    }

    /**
     * 向所有元素前添加一个新元素
     *
     * @param e
     */
    public void addFirst(int e) {
        add(0, e);
    }

    /**
     * 获取index索引位置的元素
     *
     * @param index
     * @return
     */
    public int get(int index) {
        //1.index合法检测
        if (index < 0 || index >= size) {
            //保证用户无法访问没有元素的位置
            throw new IllegalArgumentException("Get failed.Index is illegal.");
        }
        /*
         * 这里index的合法性检测 index>=size 和add方法中index合法性检测index>size 没有=号是不同的。
         * 这个get是获取，如果能等于，就是取空的元素 显然是错误的 所以是 index>=size
         * 而add中 是插入操作，index=size是可以的，就相当于向数组末尾添加一个元素
         */
        return data[index];
    }

    /**
     * 修改
     *
     * @param index
     * @param e
     */
    public void set(int index, int e) {
        //1.index合法检测
        if (index < 0 || index >= size) {
            //保证用户无法访问没有元素的位置
            throw new IllegalArgumentException("Get failed.Index is illegal.");
        }
        data[index] = e;
    }

    /**
     * 查找数组是否包含某个元素e
     *
     * @param e
     */
    public boolean contains(int e) {
        for (int i = 0; i < size; i++) {//注意是size
            if (data[i] == e) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找元素e对应的索引，有可能找不到，如果找不到就返回一个特殊的索引 比如-1
     * 与contains方法类似。
     *
     * @param e
     * @return
     */
    public int find(int e) {
        for (int i = 0; i < size; i++) {//注意是size
            if (data[i] == e) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 删除一个元素
     * 删除某个索引和add类似，都是一种赋值操作，但是反向。
     * 要删除的索引之后的元素向左移。比如删除索引为1的元素，那么直接就把索引为2 的以及之后元素向左移，索引为2的移到索引为1，类推
     * 返回值 int。删除操作，返回删除的元素。
     *
     * @param index 索引
     * @return 被删除的元素
     */
    public int removeByIndex(int index) {
        //1.首先判断数组是否为空
        if (isEmpty()) {
            throw new IllegalArgumentException("remove failed.Array is Empty");
        }
        //2.判断index的合法性
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("remove failed.Require index >= 0 || index <= size");
        }

        int del = data[index];

        //目标索引后的索引都向左移
//        for (int i = size - 1; i > index; i--) {
//            data[i - 1] = data[i];
//        }
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }

        size--;
        return del;
        //不用管data[size]还保持有元素，因为index的合法性来说，用户是无法访问index==size的元素的。
    }

    public int removeFirst() {
        return removeByIndex(0);
    }

    public int removeLast() {
        return removeByIndex(size - 1);
    }

    /**
     * 删除一个元素
     * 没有考虑重复的元素 find方法类似，没有考虑重复的元素。但逻辑先这样
     * @param e
     */
    public void removeElement(int e) {
        int index = find(e);
        if (index != -1) {
            removeByIndex(index);
        }
    }

//    首次 错误写法
//    public int _removeByIndex(int index) {
//        //1.首先判断数组是否为空
//        if (isEmpty()) {
//            throw new IllegalArgumentException("remove failed.Array is Empty");
//        }
//        //2.判断index的合法性
//        if (index < 0 || index >= size) {
//            throw new IllegalArgumentException("remove failed.Require index >= 0 || index <= size");
//        }
//        //目标索引后的索引都向左移            ！！！循环写的有问题。  for循环需要加强理解  开始条件错误，循环运行条件错误
//        for (int i = size - 1; i > index; i--) {
//            data[i - 1] = data[i];
//        }
//        size--;
//        return data[index];  ！！必须方法最初直接获取并保存，移动后的元素已经不是删除的元素了
//        //不用管data[size]还保持有元素，因为index的合法性来说，用户是无法访问index==size的元素的。
//    }

    /**
     * 完成数组 data[index]的打印 ，使用StringBuilder实现
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d ,capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            } else {
                res.append(']');
            }
        }
        return res.toString();
    }
}
