package ast

import org.graphite.ast.Program

class AstTestHelper {
    fun testAst(program: Program, programString: String) {
        assert(program.toString() == programString) {
            "Programs string is not `$programString`! got `$program`"
        }

        println("Program string: $program")
        println("Program tree string: ${program.toStringTree()}")
    }
}