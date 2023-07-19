package transformers

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import transformers.JopaClassVisitor.JopaMethodVisitor.Companion.JOPA_METHOD_DESCRIPTOR
import transformers.JopaClassVisitor.JopaMethodVisitor.Companion.JOPA_METHOD_NAME

public const val JOPA_CLASS = "com.example.jetpackinstrumentation.JopaKonaKt"

/**
 *
 * @author gleb.maliborsky
 */
public class JopaClassVisitor(
    api: Int = Opcodes.ASM9,
    classVisitor: ClassVisitor,
) : ClassVisitor(api, classVisitor) {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (JOPA_METHOD_NAME.equals(name) && JOPA_METHOD_DESCRIPTOR.equals(descriptor)) {
            JopaMethodVisitor(methodVisitor)
        } else {
            methodVisitor
        }
    }

    public companion object {
        fun instrumentClass(classFilterName: String): Boolean {
            return JOPA_CLASS.equals(classFilterName)
        }
    }

    private class JopaMethodVisitor(mv: MethodVisitor) : MethodVisitor(Opcodes.ASM9, mv) {
        override fun visitCode() {
            println("ldldld333")
            mv.visitLdcInsn("asd")
            mv.visitVarInsn(Opcodes.ASTORE, 1)
            mv.visitVarInsn(Opcodes.ALOAD, 1)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "getBytes", "()[B", false);
            mv.visitInsn(Opcodes.RETURN)
            super.visitCode()
        }

        companion object {
            public const val JOPA_METHOD_NAME = "test"
            public const val JOPA_METHOD_DESCRIPTOR = "()V"
        }
    }
}
