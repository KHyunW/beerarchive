package com.example.beerarchive.common;

public enum AccountGrade {
    
    맥린이("🌱", 0, 19),
    맥주애호가("🍺", 20, 49),
    맥덕("🍻", 50, 199),
    인간오크통("🪣", 200, Integer.MAX_VALUE);

    private final String emoji;
    private final int minPoint;
    private final int maxPoint;

    AccountGrade(String emoji, int minPoint, int maxPoint){
        this.emoji = emoji;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public String getEmoji(){
        return emoji;
    }
    public int getMinPoint(){
        return minPoint;
    }

    // 점수 기반으로 등급 계산
    public static AccountGrade of(int point){
        for(AccountGrade grade : values()){
            if(point >= grade.minPoint && point <= grade.maxPoint){
                return grade;
            }
        }
        return 맥린이;
    }

    // 다음 등급까지 필요한 점수
    public static int pointsToTextGrade(int point){
        for(AccountGrade grade : values()){
            if(point >= grade.minPoint && point <= grade.maxPoint){
                int idx = grade.ordinal();
                if (idx < values().length - 1){
                    return values()[idx + 1].minPoint - point;
                }
                return 0;
            }
        }
        return 0;
    }

}
