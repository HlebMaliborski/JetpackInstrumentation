package transformers

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*
import transformers.RangeSliderClassVisitor.RangeSliderMethodVisitor.Companion.RANGE_SLIDER_METHOD_DESCRIPTOR
import transformers.RangeSliderClassVisitor.RangeSliderMethodVisitor.Companion.RANGE_SLIDER_METHOD_NAME


public class RangeSliderClassVisitor(
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
        return if (RANGE_SLIDER_METHOD_NAME.equals(name) && RANGE_SLIDER_METHOD_DESCRIPTOR.equals(descriptor)) {
            RangeSliderMethodVisitor(methodVisitor)
        } else {
            methodVisitor
        }
    }

    companion object {
        public const val RANGE_SLIDER_CLASS = "androidx.compose.material.SliderKt\$RangeSlider$2\$gestureEndAction$1"
        fun instrumentClass(classFilterName: String): Boolean {
            return RANGE_SLIDER_CLASS.equals(classFilterName)
        }
    }

    private class RangeSliderMethodVisitor(mv: MethodVisitor) : MethodVisitor(Opcodes.ASM9, mv) {
        override fun visitCode() {
            /*mv.visitTypeInsn(Opcodes.NEW, "com/example/jetpackinstrumentation/RangeSliderCallback")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "com/example/jetpackinstrumentation/RangeSliderCallback",
                "<init>",
                "()V",
                false
            )

            mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "com/example/jetpackinstrumentation/RangeSliderCallback",
                "test",
                "()V",
                false
            )*/
            super.visitCode()
            /*mv.visitTypeInsn(Opcodes.NEW, "com/example/jetpackinstrumentation/RangeSliderCallback")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "com/example/jetpackinstrumentation/RangeSliderCallback",
                "<init>",
                "()V",
                false
            )

            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(
                GETFIELD,
                "androidx/compose/material/SliderKt\$RangeSlider\$2\$gestureEndAction$1",
                "\$maxPx",
                "F"
            );
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(
                GETFIELD,
                "androidx/compose/material/SliderKt\$RangeSlider$2\$gestureEndAction$1",
                "\$maxPx",
                "F"
            );

            mv.visitVarInsn(ALOAD, 0)

            mv.visitFieldInsn(
                GETFIELD,
                "androidx/compose/material/SliderKt\$RangeSlider$2\$gestureEndAction$1",
                "\$valueRange",
                "Lkotlin/ranges/ClosedFloatingPointRange;"
            );

            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(
                GETFIELD,
                "androidx/compose/material/SliderKt\$RangeSlider$2\$gestureEndAction$1",
                "\$rawOffsetStart",
                "Landroidx/compose/runtime/MutableState;"
            );
            mv.visitMethodInsn(
                INVOKEINTERFACE,
                "androidx/compose/runtime/MutableState",
                "getValue",
                "()Ljava/lang/Object;",
                true
            )
            mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F", false);

            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(
                GETFIELD,
                "androidx/compose/material/SliderKt\$RangeSlider$2\$gestureEndAction$1",
                "\$rawOffsetEnd",
                "Landroidx/compose/runtime/MutableState;"
            );
            mv.visitMethodInsn(
                INVOKEINTERFACE,
                "androidx/compose/runtime/MutableState",
                "getValue",
                "()Ljava/lang/Object;",
                true
            )
            mv.visitTypeInsn(CHECKCAST, "java/lang/Number");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F", false);
            mv.visitMethodInsn(
                INVOKESTATIC,
                "kotlin/ranges/RangesKt",
                "rangeTo",
                "(FF)Lkotlin/ranges/ClosedFloatingPointRange;",
                false
            );

            mv.visitMethodInsn(
                INVOKESTATIC,
                "androidx/compose/material/SliderKt\$RangeSlider$2",
                "invoke\$scaleToUserValue",
                "(FFLkotlin/ranges/ClosedFloatingPointRange;Lkotlin/ranges/ClosedFloatingPointRange;)Lkotlin/ranges/ClosedFloatingPointRange;",
                false
            );

            mv.visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "com/example/jetpackinstrumentation/RangeSliderCallback",
                "test",
                "(Lkotlin/ranges/ClosedFloatingPointRange;)V",
                false
            )*/
        }

        override fun visitEnd() {
            println("ddddd")

            super.visitEnd()
        }

        companion object {
            public const val RANGE_SLIDER_METHOD_NAME = "invoke"
            public const val RANGE_SLIDER_METHOD_DESCRIPTOR =
                "(Z)V"
        }
    }
}
/*
methodVisitor.visitVarInsn(ALOAD, 0);
* methodVisitor.visitFieldInsn(GETFIELD, "tm/alashow/ui/material/SliderKt$RangeSlider$2$gestureEndAction$1$1$1", "$rawOffsetStart", "Landroidx/compose/runtime/MutableState;");
methodVisitor.visitMethodInsn(INVOKEINTERFACE, "androidx/compose/runtime/MutableState", "getValue", "()Ljava/lang/Object;", true);
methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F", false);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "tm/alashow/ui/material/SliderKt$RangeSlider$2$gestureEndAction$1$1$1", "$rawOffsetEnd", "Landroidx/compose/runtime/MutableState;");
methodVisitor.visitMethodInsn(INVOKEINTERFACE, "androidx/compose/runtime/MutableState", "getValue", "()Ljava/lang/Object;", true);
methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/Number");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Number", "floatValue", "()F", false);
methodVisitor.visitMethodInsn(INVOKESTATIC, "kotlin/ranges/RangesKt", "rangeTo", "(FF)Lkotlin/ranges/ClosedFloatingPointRange;", false);
methodVisitor.visitMethodInsn(INVOKESTATIC, "tm/alashow/ui/material/SliderKt$RangeSlider$2", "access$invoke$scaleToUserValue", "(FFLkotlin/ranges/ClosedFloatingPointRange;Lkotlin/ranges/ClosedFloatingPointRange;)Lkotlin/ranges/ClosedFloatingPointRange;", false);*/
