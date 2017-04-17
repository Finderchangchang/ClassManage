package wai.clas.model;


/**
 * 单选题
 * Created by 酱面 on 2017/4/9.
 */
public class Xuanze{
    private String drivingProblem1;
    private String aChoice;
    private String bChoice;
    private String cChoice;
    private String dChoice;
    private String choiveAnswer;//正确答案

    public Xuanze(String drivingProblem1, String aChoice, String bChoice, String cChoice, String dChoice, String choiveAnswer) {
        this.drivingProblem1 = drivingProblem1;
        this.aChoice = aChoice;
        this.bChoice = bChoice;
        this.cChoice = cChoice;
        this.dChoice = dChoice;
        this.choiveAnswer = choiveAnswer;
    }

    public String getDrivingProblem1() {
        return drivingProblem1;
    }

    public void setDrivingProblem1(String drivingProblem1) {
        this.drivingProblem1 = drivingProblem1;
    }

    public String getaChoice() {
        return aChoice;
    }

    public void setaChoice(String aChoice) {
        this.aChoice = aChoice;
    }

    public String getbChoice() {
        return bChoice;
    }

    public void setbChoice(String bChoice) {
        this.bChoice = bChoice;
    }

    public String getcChoice() {
        return cChoice;
    }

    public void setcChoice(String cChoice) {
        this.cChoice = cChoice;
    }

    public String getdChoice() {
        return dChoice;
    }

    public void setdChoice(String dChoice) {
        this.dChoice = dChoice;
    }

    public String getChoiveAnswer() {
        return choiveAnswer;
    }

    public void setChoiveAnswer(String choiveAnswer) {
        this.choiveAnswer = choiveAnswer;
    }
}
