package cl.company.omypay.helpcenter.shared.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * ApiResponse
 * @param <T>
 */
public class ApiResponse<T> {

    private final Boolean success;
    private final Integer code;
    private final String message;
    @JsonInclude
    private final T data;

    private ApiResponse(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(Boolean success, Integer code, String message, T data) {
        return new ApiResponse<>(success, code, message, data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, 0, "Success", data);
    }

    public static <T> ApiResponse<T> failure(Integer code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }

    public static <T> ApiResponse<T> failure() {
        return failure(1, "Unknown error");
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
