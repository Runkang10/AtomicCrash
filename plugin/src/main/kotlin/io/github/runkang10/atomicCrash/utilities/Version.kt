package io.github.runkang10.atomicCrash.utilities

object Version {
    fun String.isNewerThan(v: String): Boolean {
        val v1 = split(".").map { it.toInt() }
        val v2 = v.split(".").map { it.toInt() }

        return if (v1[0] > v2[0]) true
        else if (v1[1] > v2[1]) true
        else if (v1[2] > v2[2]) true
        else false
    }
}