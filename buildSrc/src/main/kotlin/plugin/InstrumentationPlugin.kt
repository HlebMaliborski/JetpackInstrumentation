import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationParameters
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import java.io.FileOutputStream
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.kotlin.dsl.create
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import transformers.ClassVisitorFactory

/**
 *
 * @author gleb.maliborsky
 */
abstract class InstrumentationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)

        cw.visit(
            Opcodes.V11,
            Opcodes.ACC_SUPER + Opcodes.ACC_PUBLIC,
            "com/devloper/squad/punkbeer/Test123",
            null,
            "java/lang/Object",
            arrayOf("kotlin/jvm/functions/Function0")
        )

        var fieldVisitor: FieldVisitor =
            cw.visitField(Opcodes.ACC_PRIVATE, "a", "Lkotlin/jvm/functions/Function0;", null, null)
        fieldVisitor.visitEnd()
        var methodVisitor: MethodVisitor =
            cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Lkotlin/jvm/functions/Function0;)V", null, null)
        methodVisitor.visitCode()
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 1)
        methodVisitor.visitFieldInsn(
            Opcodes.PUTFIELD,
            "com/devloper/squad/punkbeer/Test123",
            "a",
            "Lkotlin/jvm/functions/Function0;"
        )
        methodVisitor.visitInsn(Opcodes.RETURN)
        methodVisitor.visitMaxs(2, 2)
        methodVisitor.visitEnd()
        methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "invoke", "()Ljava/lang/Object;", null, null)
        methodVisitor.visitCode()
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        methodVisitor.visitFieldInsn(
            Opcodes.GETFIELD,
            "com/devloper/squad/punkbeer/Test123",
            "a",
            "Lkotlin/jvm/functions/Function0;"
        )
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEINTERFACE,
            "kotlin/jvm/functions/Function0",
            "invoke",
            "()Ljava/lang/Object;",
            true
        )
        methodVisitor.visitInsn(Opcodes.ARETURN)
        methodVisitor.visitMaxs(1, 1)
        methodVisitor.visitEnd()
        cw.visitEnd()

        // Write the bytes as a class file

        // Write the bytes as a class file
        val bytes = cw.toByteArray()
        try {
            FileOutputStream("Test123.class").use { stream -> stream.write(bytes) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extension = target.extensions.create<InstrumentationPluginExtension>("instrumentation")

        // After that we have to listen when the Android Gradle plugin is applied to the project and retrieve the
        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_ALL_CLASSES)
            variant.instrumentation.transformClassesWith<InstrumentationPluginParameters>(InstrumentationPluginFactory::class.java,
                InstrumentationScope.ALL,
                { params ->
                    params.instrumentedClasses.set(extension.instrumentedClasses)
                }
            )
        }
    }

    abstract class InstrumentationPluginFactory : AsmClassVisitorFactory<InstrumentationPluginParameters> {
        override fun createClassVisitor(
            classContext: ClassContext, nextClassVisitor: ClassVisitor,
        ): ClassVisitor {
            return ClassVisitorFactory.getClassVisitor(
                classContext.currentClassData.className,
                nextClassVisitor
            )
        }

        override fun isInstrumentable(classData: ClassData): Boolean {

            if (classData.className.contains("Test")) {
                System.out.println(classData.className)
            }
            return parameters.get().instrumentedClasses.get().contains(classData.className)
        }
    }

    interface InstrumentationPluginParameters : InstrumentationParameters {
        @get:Input
        val instrumentedClasses: ListProperty<String>
    }
}

interface InstrumentationPluginExtension {
    val instrumentedClasses: ListProperty<String>
}
