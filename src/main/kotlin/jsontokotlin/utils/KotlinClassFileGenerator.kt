package jsontokotlin.utils

import jsontokotlin.model.classscodestruct.KotlinClass

class KotlinClassFileGenerator {

    fun generateSingleKotlinClassFile(
        packageDeclare: String,
        kotlinClass: KotlinClass,
    ) {

    }

    fun generateMultipleKotlinClassFiles(
        kotlinClass: KotlinClass,
        packageDeclare: String
    ) {
    }

    private fun currentDirExistsFileNamesWithoutKTSuffix(): List<String> {
        return emptyList()
    }

    private fun getRenameClassMap(originNames: List<String>, currentNames: List<String>): List<Pair<String, String>> {
        if (originNames.size != currentNames.size) {
            throw IllegalArgumentException("two names list must have the same size!")
        }
        val renameMap = mutableListOf<Pair<String, String>>()
        originNames.forEachIndexed { index, originName ->
            if (originName != currentNames[index]) {
                renameMap.add(Pair(originName, currentNames[index]))
            }
        }
        return renameMap
    }

    private fun generateKotlinClassFile(
            fileName: String,
            packageDeclare: String,
            classCodeContent: String
    ) {
        val kotlinFileContent = buildString {
            if (packageDeclare.isNotEmpty()) {
                append(packageDeclare)
                append("\n\n")
            }
            val importClassDeclaration = ClassImportDeclaration.getImportClassDeclaration()
            if (importClassDeclaration.isNotBlank()) {
                append(importClassDeclaration)
                append("\n\n")
            }
            append(classCodeContent)
        }
    }
}
