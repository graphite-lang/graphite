package org.graphite

import org.graphite.repl.Repl
import java.io.File

fun main(args: Array<String>) {
    val repl = Repl()

    if (args.getOrNull(index = 0) != null) {
        val file = File(args[0])

        if (file.exists()) {
            repl.run(buffer = file.readText())
        }
    } else {
        repl.start()
    }
}
