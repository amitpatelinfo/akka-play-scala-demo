package com.typesafe.reactive.workshop;

/**
 * Created by amit on 10/09/15.
 */
public class DrunkException extends IllegalStateException{

    public DrunkException(){
        super();
    }

    public DrunkException(String message){
        super(message);
    }
}
