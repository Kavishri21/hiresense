package com.hiresense.util;
import java.util.ArrayList;
import java.util.List;

public class SkillExtractorUtil {

    private static final String[] SKILLS = {
            "java", "spring", "spring boot", "hibernate",
            "sql", "postgresql", "mysql",
            "html", "css", "javascript", "react",
            "python", "machine learning", "api", "rest"
    };

    public static List<String> extractSkills(String resumeText) {
        List<String> foundSkills = new ArrayList<>();
        String text = resumeText.toLowerCase();

        for (String skill : SKILLS) {
            if (text.contains(skill)) {
                foundSkills.add(skill);
            }
        }
        return foundSkills;
    }
}
