import com.android.build.api.instrumentation.*
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.internal.build.event.BuildEventListenerRegistryInternal
import org.gradle.internal.operations.*
import org.gradle.kotlin.dsl.create
import org.objectweb.asm.ClassVisitor
import transformers.ClassVisitorFactory
import javax.inject.Inject

/**
 *
 * @author gleb.maliborsky
 */
abstract class InstrumentationPlugin @Inject constructor(
    private val buildEvents: BuildEventListenerRegistryInternal
) : Plugin<Project> {
    abstract class BuildOperationsService : BuildService<BuildServiceParameters.None>, BuildOperationListener {

        var isCompose: Boolean = false
        override fun started(buildOperation: BuildOperationDescriptor, startEvent: OperationStartEvent) {
        }

        override fun progress(operationIdentifier: OperationIdentifier, progressEvent: OperationProgressEvent) {}

        override fun finished(buildOperation: BuildOperationDescriptor, finishEvent: OperationFinishEvent) {
            if (!isCompose)
                isCompose = buildOperation.displayName.contains("compose")
        }
    }

    override fun apply(target: Project) {
        val extension = target.extensions.create<InstrumentationPluginExtension>("instrumentation")
        val buildOperationsService =
            target.gradle.sharedServices.registerIfAbsent("buildOperations", BuildOperationsService::class.java) {}
        buildEvents.onOperationCompletion(buildOperationsService)

        //After that we have to listen when the Android Gradle plugin is applied to the project and retrieve the
        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_ALL_CLASSES)
            variant.instrumentation.transformClassesWith(InstrumentationPluginFactory::class.java,
                InstrumentationScope.ALL
            ) { params ->
                params.getBuildServer().set(buildOperationsService)
                params.instrumentedClasses.set(extension.instrumentedClasses)
            }
        }
    }

    abstract class InstrumentationPluginFactory : AsmClassVisitorFactory<InstrumentationPluginParameters> {
        override fun createClassVisitor(
            classContext: ClassContext, nextClassVisitor: ClassVisitor
        ): ClassVisitor {
            val b = parameters.get().getBuildServer().get().isCompose
            return ClassVisitorFactory.getClassVisitor(
                classContext.currentClassData.className,
                nextClassVisitor
            )
        }

        override fun isInstrumentable(classData: ClassData): Boolean {
            val a = 1
            return parameters.get().instrumentedClasses.get().contains(classData.className)
        }
    }

    interface InstrumentationPluginParameters : InstrumentationParameters {
        @Internal
        fun getBuildServer(): Property<BuildOperationsService>

        @get:Input
        val instrumentedClasses: ListProperty<String>
    }
}

interface InstrumentationPluginExtension {
    val instrumentedClasses: ListProperty<String>
}
