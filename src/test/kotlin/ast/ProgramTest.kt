package ast

import org.graphite.ast.Program
import org.graphite.ast.expressions.IdentifierExpression
import org.graphite.ast.statements.variable.ConstStatement
import org.graphite.ast.statements.variable.VarStatement
import org.graphite.token.Token
import org.graphite.token.TokenType
import org.junit.jupiter.api.Test

class ProgramTest {
    private val astTestHelper = AstTestHelper()

    @Test
    fun `Test const statement ast`() {
        val program =
            Program(
                statements = mutableListOf(
                    ConstStatement(
                        token = Token(TokenType.CONST, "const"),
                        name =
                        IdentifierExpression(
                            token = Token(TokenType.IDENTIFIER, "ten"),
                            value = "ten",
                        ),
                        value =
                        IdentifierExpression(
                            token = Token(TokenType.INT, "10"),
                            value = "10",
                        ),
                    ),
                ),
            )

        astTestHelper.testAst(program = program, programString = "const ten = 10")
    }

    @Test
    fun `Test var statement ast`() {
        val program =
            Program(
                statements =
                mutableListOf(
                    VarStatement(
                        token = Token(TokenType.VAR, "var"),
                        name =
                        IdentifierExpression(
                            token = Token(TokenType.IDENTIFIER, "ten"),
                            value = "ten",
                        ),
                        value =
                        IdentifierExpression(
                            token = Token(TokenType.INT, "10"),
                            value = "10",
                        ),
                    ),
                ),
            )

        astTestHelper.testAst(program = program, programString = "var ten = 10")
    }
}
