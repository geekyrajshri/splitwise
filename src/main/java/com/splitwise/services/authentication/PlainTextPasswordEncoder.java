package com.splitwise.services.authentication;

public class PlainTextPasswordEncoder implements PasswordEncoder {

    private static PlainTextPasswordEncoder INSTANCE;

    private PlainTextPasswordEncoder() {
    }

    public static PlainTextPasswordEncoder getINSTANCE(){
        if (INSTANCE==null){
            synchronized (PlainTextPasswordEncoder.class){
                if (INSTANCE==null){
                    INSTANCE = new PlainTextPasswordEncoder();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String encode(String password, String username) {
        return password;
    }
}
