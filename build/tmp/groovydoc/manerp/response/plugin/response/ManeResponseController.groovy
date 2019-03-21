package manerp.response.plugin.response

import grails.converters.JSON

class ManeResponseController {

    def render(ManeResponse maneResponse) {

        HashMap response = new HashMap()
        response.data = maneResponse.data ?: null
        response.message = maneResponse.message ?: null
        maneResponse.header = maneResponse.header ?: ['Content-Type': 'application/json']

        maneResponse.header.each { k, v ->
            header k, v
        }

        response.status = maneResponse.statusCode.code

        render response as JSON
    }

}
