package com.AmazingDevOpsLMS.exceptions;

/**
 * Exception handler for double entry
 *
 * ResourceAlreadyExistException.java
 *
 * @author Samuel Maina
 *
 * 02-22-2022
 *
 * @version 1.0
 */
public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
