package com.example.myapp;

public class quizi {

    String option1,option2,option3,answer;

    public quizi(String option1, String option2, String option3, String answer) {
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answer = answer;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getAnswer() {
        return answer;
    }
}
