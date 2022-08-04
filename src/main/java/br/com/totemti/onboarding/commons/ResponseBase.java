package br.com.totemti.onboarding.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
public class ResponseBase<T> {
    private T data;
    private boolean success;

    public static <T> ResponseBase<T> of(T data, boolean success) {
        return new ResponseBase<T>(data, success);
    }
}