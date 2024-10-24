/*
 * This file is written by and is property of Samuel Maina Mwangi.
 */
package com.AmazingDevOpsLMS.exceptions;

/**
 * Exception class for resource not found
 * 
 * ResourceNotFoundException.java
 * 
 * @author Samuel Maina
 *
 * 02-22-2022
 *
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceNotFound) {
        super(resourceNotFound);
    }

}
