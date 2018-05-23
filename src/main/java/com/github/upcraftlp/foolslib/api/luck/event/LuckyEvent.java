package com.github.upcraftlp.foolslib.api.luck.event;

import com.github.upcraftlp.foolslib.api.luck.ILuckyEvent;

import java.util.Random;

public abstract class LuckyEvent implements ILuckyEvent {

    protected static final Random RANDOM = new Random();

}
