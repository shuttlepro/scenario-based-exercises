package com.shuttle.sceneexer.anticorruptionlayer.exception;

/**
 * @author: Shuttle
 * @description: ShopException
 */
public class ShopException extends RuntimeException {

    public ShopException(String message) {
        super(message);
    }

    public static ShopException throwDuplicateOrderException(String existingOrder, String incomingOrder) throws ShopException {
        throw new ShopException("The order is already placed but has an incorrect data:\n"
                + "Incoming order:  " + incomingOrder + "\n"
                + "Existing order:  " + existingOrder);
    }

}
