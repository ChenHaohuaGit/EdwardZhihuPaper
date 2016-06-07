package com.example.edwardlucci.edwardzhihupaper.util;

import java.util.ArrayList;
import java.util.List;

public class HtmlUtil {
    public static String structHtml(String oriStr, List<String> cssList) {
        StringBuilder htmlString = new StringBuilder("<html><head>");
        for (String css : cssList) {
            htmlString.append(structCssLink(css));
        }
        htmlString.append("</head><body>");
        htmlString.append("<style>img{max-width:340px !important;}</style>");
        htmlString.append(oriStr);
        htmlString.append("</body></html>");
        return htmlString.toString();
    }

    public static String structHtml(String oriStr, String cssStr) {
        StringBuilder htmlString = new StringBuilder("<html><head>");
        htmlString.append(structCssLink(cssStr));
        htmlString.append("</head><body>");
        htmlString.append("<style>img{max-width:340px !important;}</style>");
        htmlString.append(oriStr);
        htmlString.append("</body></html>");
        return htmlString.toString();
    }

    public static String structCssLink(String css) {
        return "<link type=\\\"text/css\\\" rel=\\\"stylesheet\\\" href=\\\"" + css + "\">";
    }
}
