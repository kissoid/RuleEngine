/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.script.exception;

/**
 *
 * @author Ersin Turetkan
 */
public class IremException extends Exception {

    public IremException() {
        super();
    }

    public IremException(String message) {
        super(message);

    }

    public IremException(Throwable cause) {
        super(cause);
    }

}
