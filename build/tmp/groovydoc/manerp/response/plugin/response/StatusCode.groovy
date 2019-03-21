package manerp.response.plugin.response

enum StatusCode {

    /**
     @@@@@@@@@@@@@@@@@@@@@@@@@ 2xx - Success Codes
     */

    /** EVERYTHING IS OK 200
     *
     *  You MUST NOT use 200 OK for everything
     */
    OK(200),

    /** CREATED 201
     *
     * Should be used after a successful POST creation request.
     * The response should include the Location header with a link towards the location of the created resource.
     */
    CREATED(201),

    /** ACCEPTED 202
     *
     * Means that the request has been accepted for processing but the processing has NOT been completed.
     * The response should include the Location header with a link towards the location where
     * the final response can be polled & later obtained.
     */
    ACCEPTED(202),

    /** NO CONTENT 204
     *
     * Means that the requested operation succeeded but that no content is returned.
     */
    NO_CONTENT(204),


    /*
      @@@@@@@@@@@@@@@@@@@@@@@@@ 3xx - Redirection Codes
     */

    /** MOVED 301
     *
     * The requested resource has been assigned to a new permenent URI and
     * any future references to this resource SHOULD use one of the returned URIs.
     */
    MOVED(301),

    /** SEE OTHER 303
     *
     * Indicates that a controller resource has finished its work,
     * but instead of sending a potentially unwanted response body, it sends the client the URI of a response resource.
     */
    SEE_OTHER(303),

    /** NOT MODIFIED 304
     *
     * Used when there is state information associated with a resource but the client already has the most recent version.
     */
    NOT_MODIFIED(304),

    /** TEMPORARY REDIRECT 307
     *
     * Indicates that the REST API is not going to process the client's request. Instead,
     * the client SHOULD re-submit the request to the URI specified by the response message's Location header.
     * The new request MUST use the same verb (e.g., POST if the previous request was a POST).
     */
    TEMPORARY_REDIRECT(307),


    /*
      @@@@@@@@@@@@@@@@@@@@@@@@@ 4xx - Client Error Codes
     */

    /** BAD REQUEST 400
     *  The request could not be understood by the server due to malformed syntax.
     *  The client SHOULD NOT repeat the request without modifications.
     */
    BAD_REQUEST(400),

    /** UNAUTHORIZED 401
     *
     * The request requires user authentication. The client MAY repeat the request with a suitable Authorization header field.
     */
    UNAUTHORIZED(401),

    /** FORBIDDEN 403
     *
     * The client is authenticated but not authorized An API client might be allowed to use some API resources but not some others;
     * in that case he should receive a 403 error
     */
    FORBIDDEN(403),

    /** NOT FOUND 404
     *
     * The server has not found anything matching the request URI
     */
    NOT_FOUND(404),

    /** METHOD NOT ALLOWED 405
     *
     * The server has not found anything matching the Request-URI
     */
    METHOD_NOT_ALLOWED(405),

    /** NOT ACCEPTABLE 406
     *
     * The resource identified by the request is only capable of generating response entities which
     * have content characteristics not acceptable according to the accept headers sent in the request.
     */
    NOT_ACCEPTABLE(406),

    /** UNSUPPORTED MEDIA TYPE 415
     *
     * Indicates that the API is not able to process the client's supplied media type as indicated by
     * the Content-Type request header
     */
    UNSUPPORTED_MEDIA(415),

    /** TOO MANY REQUESTS 429
     *
     * The client has sent too many requests in a given time frame.
     */
    TOO_MANY_REQUESTS(429),


    /*
     @@@@@@@@@@@@@@@@@@@@@@@@@ 5xx - Server Error Codes
     */

    /** INTERNAL SERVER ERROR 500
     *
     * The server encountered an unexpected condition which prevented it from fulfilling the request
     */
    INTERNAL_ERROR(500),

    /** SERVICE UNAVAILABLE 503
     *
     * The server is currently unable to handle the request due to a temporary overloading or maintenance of the server
     */
    UNAVAILABLE(503)

    final int code

    StatusCode(int code) {
        this.code = code
    }

    int getCode() {
        return this.code
    }
}