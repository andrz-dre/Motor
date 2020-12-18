package jsontokotlin.extensions

import com.google.gson.Gson
import com.google.gson.JsonObject
import javax.swing.JPanel
import jsontokotlin.interceptor.IImportClassDeclarationInterceptor
import jsontokotlin.interceptor.IKotlinClassInterceptor
import jsontokotlin.model.ConfigManager
import jsontokotlin.model.classscodestruct.KotlinClass

/**
 * Extension which represent a function extending the current functions of JsonToKotlinClass plugin
 * It must have a UI item to be insert into the JBList JPanel of Extensions Tab
 */
abstract class Extension : IImportClassDeclarationInterceptor, IKotlinClassInterceptor<KotlinClass> {

    private val gson = Gson()

    /**
     * set every config inside the extension
     * to keep the key primary, we could define it like : wu.seal.xxx using domain before the real config
     */
    protected fun setConfig(key: String, value: String) {
        val configs = gson.fromJson(ConfigManager.extensionsConfig, JsonObject::class.java) ?: JsonObject()
        configs.addProperty(key, value)
        ConfigManager.extensionsConfig = gson.toJson(configs)
    }

    /**
     * get every config inside the extension
     * to keep the key primary, we could define it like : wu.seal.xxx  using domain before the real config
     */
    protected fun getConfig(key: String): String {
        val configs = gson.fromJson(ConfigManager.extensionsConfig, JsonObject::class.java) ?: JsonObject()
        return configs[key]?.asString ?: ""
    }

    override fun intercept(originClassImportDeclaration: String): String {
        return originClassImportDeclaration
    }

    fun getTestHelper() = TestHelper(this)

    /**
     * Test helper for test config settings
     */
    class TestHelper(private val extension: Extension) {

        fun setConfig(key: String, value: String) {
            extension.setConfig(key,value)
        }

        fun getConfig(key: String): String {
            return extension.getConfig(key)
        }
    }
}
