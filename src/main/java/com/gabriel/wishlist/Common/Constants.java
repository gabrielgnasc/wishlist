package com.gabriel.wishlist.Common;

public class Constants {
    public static final int MAX_LIMIT_WISHLIST = 20;
    public static final String LOCK_CUSTOMER_PREFIX = "CUSTOMER_";
    public static final int LOCK_WAIT_IN_SECONDS = 10;
    public static final int LOCK_LEASE_TIME = 60;


    public static class ErrorMessage{
        public static final String WISHLIST_EXCEEDED_0_PRODUCTS = "A lista de desejos já possui seu limite máximo de {0} produtos.";
        public static final String WISHLIST_OF_CUSTOMER_NOT_FOUND = "A lista de desejos do cliente solicitado não foi encontrada.";
        public static final String UNFINISHED_TRANSACTION = "A última operação da lista ainda não foi finalizada, tente novamente mais tarde.";
    }
}
