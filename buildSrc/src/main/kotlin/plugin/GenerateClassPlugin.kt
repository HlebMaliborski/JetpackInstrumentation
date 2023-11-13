package plugin

import java.io.FileOutputStream
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ACC_PRIVATE
import org.objectweb.asm.Opcodes.ACC_PUBLIC
import org.objectweb.asm.Opcodes.ACC_SUPER
import org.objectweb.asm.Opcodes.ALOAD
import org.objectweb.asm.Opcodes.ARETURN
import org.objectweb.asm.Opcodes.GETFIELD
import org.objectweb.asm.Opcodes.INVOKEINTERFACE
import org.objectweb.asm.Opcodes.INVOKESPECIAL
import org.objectweb.asm.Opcodes.PUTFIELD
import org.objectweb.asm.Opcodes.RETURN
import org.objectweb.asm.Opcodes.V11

/**
 *
 * @author gleb.maliborsky
 */
abstract class GenerateClassPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)

        cw.visit(
            V11,
            ACC_SUPER + ACC_PUBLIC,
            "com/devloper/squad/punkbeer/Test1234",
            null,
            "java/lang/Object",
            arrayOf("kotlin/jvm/functions/Function0")
        )

        var fieldVisitor: FieldVisitor = cw.visitField(ACC_PRIVATE, "a", "Lkotlin/jvm/functions/Function0;", null, null)
        fieldVisitor.visitEnd()
        var methodVisitor: MethodVisitor =
            cw.visitMethod(ACC_PUBLIC, "<init>", "(Lkotlin/jvm/functions/Function0;)V", null, null)
        methodVisitor.visitCode()
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitVarInsn(ALOAD, 1)
        methodVisitor.visitFieldInsn(
            PUTFIELD,
            "com/devloper/squad/punkbeer/Test1234",
            "a",
            "Lkotlin/jvm/functions/Function0;"
        )
        methodVisitor.visitInsn(RETURN)
        methodVisitor.visitMaxs(2, 2)
        methodVisitor.visitEnd()
        methodVisitor = cw.visitMethod(ACC_PUBLIC, "invoke", "()Ljava/lang/Object;", null, null)
        methodVisitor.visitCode()
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitFieldInsn(
            GETFIELD,
            "com/devloper/squad/punkbeer/Test1234",
            "a",
            "Lkotlin/jvm/functions/Function0;"
        )
        methodVisitor.visitMethodInsn(
            INVOKEINTERFACE,
            "kotlin/jvm/functions/Function0",
            "invoke",
            "()Ljava/lang/Object;",
            true
        )
        methodVisitor.visitInsn(ARETURN)
        methodVisitor.visitMaxs(1, 1)
        methodVisitor.visitEnd()
        cw.visitEnd()

        // Write the bytes as a class file

        // Write the bytes as a class file
        val bytes = cw.toByteArray()
        try {
            FileOutputStream("app/src/main/assets/Test1234.class").use { stream -> stream.write(bytes) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
