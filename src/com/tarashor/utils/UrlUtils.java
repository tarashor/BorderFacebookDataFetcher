package com.tarashor.utils;

import java.util.Locale;
import java.util.Map;


/**
 * Created by Taras on 03.01.2017.
 */
public class UrlUtils {

    public static final String QUERY_ITEM_FORMAT = "%1$s=%2$s";
    public static final String QUERY_ITEM_SEPARATOR = "&";

    public static String createQueryString(Map<String, String> params) {
        String query = "";
        boolean isFirstParam = true;
        for(Map.Entry<String, String> entry : params.entrySet()) {
            String queryItem = String.format(QUERY_ITEM_FORMAT, entry.getKey(), entry.getValue());

            if (isFirstParam){
                isFirstParam = false;
            } else {
                queryItem = QUERY_ITEM_SEPARATOR + queryItem;
            }

            query += queryItem;
        }

        return query;
    }
}
