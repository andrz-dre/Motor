package jsontokotlin.extensions.wu.seal

import javax.swing.JPanel
import jsontokotlin.extensions.Extension
import jsontokotlin.model.classscodestruct.DataClass
import jsontokotlin.model.classscodestruct.KotlinClass
import jsontokotlin.model.classscodestruct.NormalClass

/**
 * Extension support disable kotlin data class, after enable this, all kotlin data classes will be changed to [NormalClass]
 */
object DisableDataClassSupport : Extension() {

    const val configKey = "wu.seal.disable_data_class_support"

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {

        if (kotlinClass is DataClass && getConfig(configKey).toBoolean()) {
            with(kotlinClass) {
                return NormalClass(annotations, name, properties, parentClassTemplate, modifiable)
            }
        } else {
            return kotlinClass
        }
    }
}