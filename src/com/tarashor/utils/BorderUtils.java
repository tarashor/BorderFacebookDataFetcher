package com.tarashor.utils;

import com.tarashor.api.models.Post;
import com.tarashor.db.FilteredPost;
import com.tarashor.db.StatItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Taras on 05.01.2017.
 */
public class BorderUtils {
    public static final String BORDER_PASS_POL_RAVA_RUSKA = "Рава-Руська";
    public static final String BORDER_PASS_POL_SHEGYNI = "Шегині";
    public static final String BORDER_PASS_POL_SMILNYTSJA = "Смільниця";
    public static final String BORDER_PASS_POL_GRUSHIV = "Грушів";
    public static final String BORDER_PASS_POL_KRAKOVETS = "Краковець";
    public static final String BORDER_PASS_POL_JAGODYN = "Ягодин авто";
    public static final String BORDER_PASS_POL_USTYLUG = "Устилуг";
    public static final String BORDER_PASS_POL_UGRYNIV = "Угринів";

    public static final String BORDER_PASS_HUNG_VYLOK = "Вилок";
    public static final String BORDER_PASS_HUNG_LUJANKA = "Лужанка";
    public static final String BORDER_PASS_HUNG_TYSA = "Тиса";
    public static final String BORDER_PASS_HUNG_KOSYNO = "Косино";

    public static final String BORDER_PASS_ROM_PORUBNE = "Порубне";
    public static final String BORDER_PASS_ROM_DJAKOVO = "Дяково";
    public static final String BORDER_PASS_ROM_SOLOTVYNO = "Солотвино";

    public static final String BORDER_PASS_SLOV_BEREZNYJ = "Березний";
    public static final String BORDER_PASS_SLOV_UZHOROD = "Ужгород";

    public static final String BORDER_PASS_MOLD_MAMALYGA = "Мамалига";

    public static final String[] ADDITINAL_WORDS = new String[]{"ппр", "на", "пропуску", "станом", "транспортних", "перед","засобів", "пунктами", "республіка","накопичень", "кордон"};

    public static int getPostScore(Post post){
        int result = 0;
        String message = post.getMessage();
        if (message != null){
            message = message.toLowerCase();
            String[] allPasses = getAllPasses();
            for (String pass : allPasses){
                result += message.contains(pass.toLowerCase()) ? 2 : 0;
            }
            for (String word : ADDITINAL_WORDS){
                result += message.contains(word) ? 1 : 0;
            }
        }
        return result;
    }

    public static String[] getAllPasses(){
        String[] allPasses = new String[]{
                BORDER_PASS_POL_RAVA_RUSKA,
                BORDER_PASS_POL_SHEGYNI,
                BORDER_PASS_POL_SMILNYTSJA,
                BORDER_PASS_POL_GRUSHIV,
                BORDER_PASS_POL_KRAKOVETS,
                BORDER_PASS_POL_JAGODYN,
                BORDER_PASS_POL_USTYLUG,
                BORDER_PASS_POL_UGRYNIV,
                BORDER_PASS_HUNG_VYLOK,
                BORDER_PASS_HUNG_LUJANKA,
                BORDER_PASS_HUNG_TYSA,
                BORDER_PASS_HUNG_KOSYNO,
                BORDER_PASS_ROM_PORUBNE,
                BORDER_PASS_ROM_DJAKOVO,
                BORDER_PASS_ROM_SOLOTVYNO,
                BORDER_PASS_SLOV_BEREZNYJ,
                BORDER_PASS_SLOV_UZHOROD,
                BORDER_PASS_MOLD_MAMALYGA};

        return allPasses;
    }

    public static List<StatItem> getStatItemsFromPost(String message, Date date) {
        List<StatItem> items = new ArrayList<>();
        if (message != null){
            message = message.toLowerCase();
            String[] allPasses = getAllPasses();
            for (String pass : allPasses){
                pass = pass.toLowerCase();
                int startIndexOfPass = message.indexOf(pass);
                if (startIndexOfPass >= 0){
                    int count = CommonUtils.getFirstNumberStartingFrom(message, startIndexOfPass + pass.length());
                    items.add(StatItem.createStatItem(pass, count, date));
                }
            }
        }
        return items;
    }
}
