package dev.arch3rtemp.common.mapper

interface Mapper<I, O> {
    fun from(i: I?): O

    fun to(o: O?): I

    fun fromList(list: List<I>?): List<O> {
        return list?.mapNotNull { from(it) } ?: listOf()
    }

    fun toList(list: List<O>?): List<I> {
        return list?.mapNotNull { to(it) } ?: listOf()
    }

    companion object {
        const val ABSENT_NUMERAL = -1
    }
}