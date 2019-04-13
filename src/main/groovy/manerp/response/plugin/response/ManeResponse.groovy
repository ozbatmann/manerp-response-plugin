package manerp.response.plugin.response

class ManeResponse<T>
{

    HashMap header
    T data
    StatusCode statusCode
    String message
    String messageCode

    ManeResponse()
    {
        statusCode = StatusCode.OK
        data = null
        message = null
        messageCode = null
        header = null
    }

    ManeResponse(T data)
    {
        this()
        this.data = data
    }

    ManeResponse(T data, StatusCode statusCode)
    {
        this(data)
        this.statusCode = statusCode
    }

    HashMap getHeader()
    {
        return header
    }

    void setHeader(HashMap header)
    {
        this.header = header
    }

    String getMessageCode()
    {
        return messageCode
    }

    void setMessageCode(String messageCode)
    {
        this.messageCode = messageCode
    }

    T getData()
    {
        return data
    }

    void setData(T data)
    {
        this.data = data
    }

    StatusCode getStatusCode()
    {
        return statusCode
    }

    void setStatusCode(StatusCode statusCode)
    {
        this.statusCode = statusCode
    }

    String getMessage()
    {
        return message
    }

    void setMessage(String message)
    {
        this.message = message
    }

}

