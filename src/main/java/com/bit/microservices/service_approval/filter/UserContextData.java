package com.bit.microservices.service_approval.filter;



public class UserContextData {
    private static final InheritableThreadLocal<UserData> userTLocal = new InheritableThreadLocal<>();

    public static void setUserData(UserData data) {
        userTLocal.set(data);
    }

    public static UserData getUserData() {
        if (userTLocal.get() == null) {
            userTLocal.set(new UserData());
        }

        return (UserData) userTLocal.get();

    }

    public static void removeData(){
        userTLocal.remove();
    }

    public static String getUserEmail(){
        return getUserData().getEmail();
    }

    public static String getUserId(){
        return getUserData().getUserId();
    }

}
