package transformers

import org.objectweb.asm.ClassVisitor
import transformers.ClickableClassVisitor.Companion.CLICKBLE_CLASS
import transformers.RangeSliderClassVisitor.Companion.RANGE_SLIDER_CLASS
import transformers.SliderClassVisitor.Companion.SLIDER_CLASS

public object ClassVisitorFactory {
    fun getClassVisitor(classFilterName: String, classVisitor: ClassVisitor): ClassVisitor {
        println(classFilterName)
        return when (classFilterName) {
            JOPA_CLASS -> JopaClassVisitor(classVisitor = classVisitor)
            else -> classVisitor
        }
    }
}


