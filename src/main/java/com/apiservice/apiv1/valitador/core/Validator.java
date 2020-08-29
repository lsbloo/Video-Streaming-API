package com.apiservice.apiv1.valitador.core;

/**
 * respresentação de um objeto validator;
 * @author osvaldoairon
 *
 * @param <T>
 */
public interface Validator<T> {

    public void validate(Result result,  T object);

}