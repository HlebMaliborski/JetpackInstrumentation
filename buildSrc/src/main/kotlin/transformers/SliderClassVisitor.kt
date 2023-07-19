package transformers

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*
import transformers.SliderClassVisitor.SliderMethodVisitor.Companion.SLIDER_METHOD_DESCRIPTOR
import transformers.SliderClassVisitor.SliderMethodVisitor.Companion.SLIDER_METHOD_NAME

/**
 *
 * @author gleb.maliborsky
 */
public class SliderClassVisitor(
    api: Int = Opcodes.ASM9,
    classVisitor: ClassVisitor
) : ClassVisitor(api, classVisitor) {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        System.out.println("Jopa" + name)
        System.out.println("Jopa2" + descriptor)
        return if (SLIDER_METHOD_NAME.equals(name) && SLIDER_METHOD_DESCRIPTOR.equals(descriptor)) {
            SliderMethodVisitor(methodVisitor)
        } else {
            methodVisitor
        }
    }

    companion object {
        public const val SLIDER_CLASS = "com.example.jetpackinstrumentation.JopaKonaKt"
        fun instrumentClass(classFilterName: String): Boolean {
            return SLIDER_CLASS.equals(classFilterName)
        }
    }

    private class SliderMethodVisitor(mv: MethodVisitor) : MethodVisitor(Opcodes.ASM9, mv) {
        override fun visitCode() {
            mv.visitTypeInsn(NEW, "com/example/jetpackinstrumentation/TestFunc")
            mv.visitInsn(DUP)
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(
                INVOKESPECIAL,
                "com/example/jetpackinstrumentation/TestFunc",
                "<init>",
                "(Lkotlin/jvm/functions/Function0;)V",
                false
            );
            mv.visitVarInsn(ASTORE, 0)

            mv.visitInsn(ICONST_0)
            mv.visitVarInsn(ISTORE, 2)
            /* mv.visitTypeInsn(Opcodes.NEW, "com/example/jetpackinstrumentation/SliderCallback")
             mv.visitInsn(Opcodes.DUP)
             mv.visitMethodInsn(
                 Opcodes.INVOKESPECIAL,
                 "com/example/jetpackinstrumentation/SliderCallback",
                 "<init>",
                 "()V",
                 false
             )

             mv.visitMethodInsn(
                 Opcodes.INVOKEVIRTUAL,
                 "com/example/jetpackinstrumentation/SliderCallback",
                 "test",
                 "()V",
                 false
             )*/
            super.visitCode()
        }

        companion object {
            public const val SLIDER_METHOD_NAME = "Slider111"
            public const val SLIDER_METHOD_DESCRIPTOR =
                "(Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;II)V"
        }
    }
}
