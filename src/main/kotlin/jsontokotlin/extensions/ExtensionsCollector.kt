package jsontokotlin.extensions

import jsontokotlin.extensions.chen.biao.KeepAnnotationSupport
import jsontokotlin.extensions.jose.han.ParcelableAnnotationSupport
import jsontokotlin.extensions.ted.zeng.PropertyAnnotationLineSupport
import jsontokotlin.extensions.wu.seal.ClassNameSuffixSupport
import jsontokotlin.extensions.wu.seal.DisableDataClassSupport
import jsontokotlin.extensions.wu.seal.ForceInitDefaultValueWithOriginJsonValueSupport
import jsontokotlin.extensions.wu.seal.KeepAnnotationSupportForAndroidX
import jsontokotlin.extensions.wu.seal.PropertyPrefixSupport
import jsontokotlin.extensions.wu.seal.PropertySuffixSupport
import jsontokotlin.extensions.xu.rui.PrimitiveTypeNonNullableSupport

/**
 * extension collect, all extensions will be hold by this class's extensions property
 */
object ExtensionsCollector {
    /**
     * all extensions
     */
    val extensions = listOf(
            KeepAnnotationSupport,
            KeepAnnotationSupportForAndroidX,
            PropertyAnnotationLineSupport,
            ParcelableAnnotationSupport,
            PropertyPrefixSupport,
            PropertySuffixSupport,
            ClassNameSuffixSupport,
            PrimitiveTypeNonNullableSupport,
            ForceInitDefaultValueWithOriginJsonValueSupport,
            DisableDataClassSupport
    )
}
