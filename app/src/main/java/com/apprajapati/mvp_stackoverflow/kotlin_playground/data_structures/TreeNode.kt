package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

typealias Visitor<T> = (TreeNode<T>) -> Unit

class TreeNode<T>(val value: T) {

    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) = children.add(child)

    fun forEachDepthFirst(visit: Visitor<T>) {
        visit(this)

        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }

    fun forEachLevelOrder(visit: Visitor<T>) {
        visit(this)
        val queue = QueueImpl<TreeNode<T>>()
        children.forEach {
            queue.enqueue(it)
        }

        var node = queue.dequeue()
        while (node != null) {
            visit(this)

            node.children.forEach {
                queue.enqueue(it)
            }
            node = queue.dequeue()
        }
    }
}

fun makeBevTree(): TreeNode<String> {
    val tree = TreeNode("Beverages")
    val hot = TreeNode("hot")
    val cold = TreeNode("cold")
    val tea = TreeNode("Tea")

    val coffee = TreeNode("Coffee")
    val chocolate = TreeNode("Chocolate")

    val blackT = TreeNode("Black tea")
    val greenT = TreeNode("Green tea")
    val chaiT = TreeNode("Chai tea")

    val soda = TreeNode("soda")
    val milk = TreeNode("milk")

    val gingerAle = TreeNode("ginger")
    val bitterLemon = TreeNode("bitterM")

    tree.add(hot)
    tree.add(cold)

    hot.add(tea)
    hot.add(coffee)
    hot.add(chocolate)

    cold.add(soda)
    cold.add(milk)

    tea.add(blackT)
    tea.add(greenT)
    tea.add(chaiT)

    soda.add(gingerAle)
    soda.add(bitterLemon)

    return tree
}

fun main() {

    //TODO : Finish this example later.
    val tree = makeBevTree()
    tree.forEachDepthFirst {
        println(it.value)
    }
}