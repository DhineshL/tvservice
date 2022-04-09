package com.dhinesh.tvservice.exception;

public class AlreadyLikedTvShow extends RuntimeException{
    public AlreadyLikedTvShow(String message) {
        super(message);
    }
}
