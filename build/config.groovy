
withConfig(configuration) {
    inline(phase: 'CONVERSION') { source, context, classNode ->
        classNode.putNodeMetaData('projectVersion', '0.1.2')
        classNode.putNodeMetaData('projectName', 'manerp-response-plugin')
        classNode.putNodeMetaData('isPlugin', 'true')
    }
}
