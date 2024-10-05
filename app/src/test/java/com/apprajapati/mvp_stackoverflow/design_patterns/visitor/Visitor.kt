package com.apprajapati.mvp_stackoverflow.design_patterns.visitor

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Visitor design pattern : used for Separation between algorithm and the objects they operate on
functionality is separate from where it can be applied.

 */

interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>) : R
}

class FixedPriceContract(val costPerYear : Long) : ReportElement {

    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialsContract(val costPerHour: Long, val hours : Long) : ReportElement {

    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long) : ReportElement {

    override fun <R> accept(visitor: ReportVisitor<R>): R {
        return visitor.visit(this)
    }

}

//out R means going to be returning R.
interface ReportVisitor<out R> {
    fun visit(contract : FixedPriceContract) : R
    fun visit(contract : TimeAndMaterialsContract) : R
    fun visit(contract : SupportContract) : R
}

class MonthlyCostReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear / 12

    override fun visit(contract: TimeAndMaterialsContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyReportVisitor : ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear

    override fun visit(contract: TimeAndMaterialsContract): Long  = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12

}

class VisitorTest {

    @Test
    fun testVisitorPattern(){
        //can rename based on appropriate use cases
        val project1  = FixedPriceContract(costPerYear = 10000)
        val project2 = SupportContract(costPerMonth = 500)
        val project3 = TimeAndMaterialsContract(costPerHour = 100, hours = 8)
        val project4 = TimeAndMaterialsContract(costPerHour = 150, hours = 10)

        val projects = arrayListOf(project1, project2, project3, project4)

        val monthlyCostVisitor = MonthlyCostReportVisitor()
        val monthlycost = projects.map {
            it.accept(monthlyCostVisitor)
        }.sum()
        println("Monthly cost: $monthlycost")
        Assertions.assertThat(monthlycost).isEqualTo(3633)


        val yearlyReportVisitor = YearlyReportVisitor()
        val yearlyCost = projects.map {
            it.accept(yearlyReportVisitor)
        }.sum()
        println("Yearly cost: $yearlyCost")
    }
}