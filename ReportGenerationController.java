package com.example.stephen_app;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReportGenerationController implements Initializable {
    Parent root;
    Stage stage;

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> class_of_pupil;
    @FXML
    private ComboBox<String> term;
    Date date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> classList = FXCollections.observableArrayList("Lower Primary","Upper Primary","Nursery");
        ObservableList<String> termList = FXCollections.observableArrayList("One","Two","Three");
        term.setItems(termList);
        class_of_pupil.setItems(classList);
    }

    public void backHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("report_options.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public String getComment(int value){
        if(value > 95) return "Excellent";
        else if (value > 90) return "Very Good";
        else if(value > 80) return "Good";
        else if(value > 70) return "Fair";
        else return "You can do better";
    }

    public String HeadTeacherReport_Upper(String div){
        return div.equals("I") ? "Very Good Performance": div.equals("II") ? "Good Performance" : div.equals("III") ? "Quite Good Aim Higher" :div.equals("IV") ? "Work hard for better marks" : "Double your effort for better results";
    }

    public String ClassTeacherReport_Upper(String div){
        return div.equals("I") ? "Always attentive": div.equals("II") ? "Good work" : div.equals("III") ? "You can do better" :div.equals("IV") ? "Work Harder" : "You can still improve";
    }

    public String HeadTeacherReport_Lower(String div){
        return div.equals("A") ? "Very Good Performance": div.equals("B") ? "Good Performance" : div.equals("C") ? "Quite Good Aim Higher" :div.equals("D") ? "Work hard for better marks" : "Double your effort for better results";
    }

    public String ClassTeacherReport_Lower(String div){
        return div.equals("A") ? "Always attentive": div.equals("B") ? "Good work" : div.equals("C") ? "You can do better" :div.equals("D") ? "Work Harder" : "You can still improve";
    }

    public void generateReport(ActionEvent event) throws IOException, DocumentException {
        if(!id.getText().equals("")&& !name.getText().equals("")&& class_of_pupil.getValue() != null && term.getValue() != null){
            String[] names = name.getText().split(" ");
            Font font = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD);
            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN,9, Font.BOLD);
            date = new Date();
            String dest = names.length > 1 ? "D://Reports/"+names[0]+names[1]+".pdf":"D://Reports/"+names[0]+".pdf";
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            document.setPageSize(PageSize.A4);
            String imageFile = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\reportBadge.png";
            Image img = Image.getInstance(imageFile);
            img.scaleAbsolute(500,100);
            document.add(img);
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            String std_class = class_of_pupil.getValue();
            document.add(new Paragraph("Name: "+name.getText()+"                                            Level: "+std_class,font1));
            String index = id.getText();
            String std_name = name.getText();
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnected();
            try{

                //Upper Primary
                Statement statement = connection.createStatement();
                ResultSet rs,rb,rm;
                if(std_class.equalsIgnoreCase("upper primary")){
                    String div="",math = "",english="",sst="",science="",totalValue = "",aggMath="",aggEng="",aggSST="",aggScience="",aggTotal = "",commE="",commS="",commSST="",commM="";
                    String div_Bot="",math_Bot = "",english_Bot="",sst_Bot="",science_Bot="",totalValue_Bot = "";
                    String div_Mid="",math_Mid = "",english_Mid="",sst_Mid="",science_Mid="",totalValue_Mid = "";
                    //ResultSet from DB


                    //Beginning of Term
                    String query_Bot = "select * from  pupil,bot_u where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 3";
                    rb = statement.executeQuery(query_Bot);
                    if(rb.next()){
                        String pupil_Class = rb.getString("Class");
                        int qBotMath = rb.getInt("math");
                        int qBotEnglish = rb.getInt("eng");
                        int qBotScience = rb.getInt("science");
                        int qBotSST = rb.getInt("sst");
                        Mid_UpperPrimaryController pr = new Mid_UpperPrimaryController();
                        int agBotMath = pr.getAgg(qBotMath);
                        int agBotEnglish = pr.getAgg(qBotEnglish);
                        int agBotScience = pr.getAgg(qBotScience);
                        int agBotSST = pr.getAgg(qBotSST);
                        int totalAggBot = agBotMath+agBotEnglish+agBotScience+agBotSST;
                        div_Bot = pr.getDiv(totalAggBot);
                        math_Bot = Integer.toString(qBotMath);
                        english_Bot = Integer.toString(qBotEnglish);
                        science_Bot = Integer.toString(qBotScience);
                        sst_Bot = Integer.toString(qBotSST);
                        totalValue_Bot = Integer.toString(qBotEnglish+qBotScience+qBotMath+qBotSST);
                    }else{
                        div_Bot = "";
                        math_Bot = "";
                        english_Bot = "";
                        science_Bot = "";
                        sst_Bot = "";
                        totalValue_Bot = "";
                    }


                    //Mid Term
                    String query_Mid = "select * from  pupil,mid_u where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 3";
                    rm = statement.executeQuery(query_Mid);
                    if(rm.next()){
                        String pupil_cls = rm.getString("Class");
                        int qMidMath = rm.getInt("math");
                        int qMidEnglish = rm.getInt("eng");
                        int qMidScience = rm.getInt("science");
                        int qMidSST = rm.getInt("sst");
                        Mid_UpperPrimaryController pr = new Mid_UpperPrimaryController();
                        int agMidMath = pr.getAgg(qMidMath);
                        int agMidEnglish = pr.getAgg(qMidEnglish);
                        int agMidScience = pr.getAgg(qMidScience);
                        int agMidSST = pr.getAgg(qMidSST);
                        int totalAggMid = agMidMath+agMidEnglish+agMidScience+agMidSST;
                        div_Mid = pr.getDiv(totalAggMid);
                        math_Mid = Integer.toString(qMidMath);
                        english_Mid = Integer.toString(qMidEnglish);
                        science_Mid = Integer.toString(qMidScience);
                        sst_Mid = Integer.toString(qMidSST);
                        totalValue_Mid = Integer.toString(qMidEnglish+qMidScience+qMidMath+qMidSST);
                    }else{
                        div_Mid = "";
                        math_Mid = "";
                        english_Mid = "";
                        science_Mid = "";
                        sst_Mid = "";
                        totalValue_Mid = "";
                    }



                    //End Of Term
                    String q = "select * from  pupil,end_u where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 3";
                    rs = statement.executeQuery(q);
                    if(rs.next()){
                        String stdClass = rs.getString("Class");
                        document.add(new Paragraph("Term: "+term.getValue()+"                                            Class: "+stdClass,font1));
                        int qMath = rs.getInt("math");
                        int qEnglish = rs.getInt("eng");
                        int qScience = rs.getInt("science");
                        int qSST = rs.getInt("sst");
                        Mid_UpperPrimaryController pr = new Mid_UpperPrimaryController();
                        int agMath = pr.getAgg(qMath);
                        int agEnglish = pr.getAgg(qEnglish);
                        int agScience = pr.getAgg(qScience);
                        int agSST = pr.getAgg(qSST);
                        int totalAgg = agMath+agEnglish+agScience+agSST;
                        div = pr.getDiv(totalAgg);
                        aggMath = Integer.toString(agMath);
                        aggEng = Integer.toString(agEnglish);
                        aggScience = Integer.toString(agScience);
                        aggSST = Integer.toString(agSST);
                        aggTotal = Integer.toString(totalAgg);
                        math = Integer.toString(qMath);
                        english = Integer.toString(qEnglish);
                        science = Integer.toString(qScience);
                        sst = Integer.toString(qSST);
                        commE = getComment(qEnglish);
                        commM = getComment(qMath);
                        commS = getComment(qScience);
                        commSST = getComment(qSST);
                        totalValue = Integer.toString((rs.getInt("sst")+rs.getInt("science")+rs.getInt("eng")+rs.getInt("math")));

                        document.add(new Paragraph("  "));

                        Paragraph p1 = new Paragraph("PUPIL'S ASSESSMENT REPORT\n\n", font);
                        Paragraph p12 = new Paragraph("\nEND OF TERM PUPIL'S PERFORMANCE\n\n", font);
                        p1.setAlignment(Element.ALIGN_CENTER);
                        p1.setFont(new Font(Font.FontFamily.HELVETICA,Font.UNDERLINE));
                        p12.setAlignment(Element.ALIGN_CENTER);
                        document.add(p1);


                        ///////////////////////////////////////////////////////////
                        //Creating the tables
                        PdfPTable table1 = new PdfPTable(7);
                        table1.setWidthPercentage(100);
                        PdfPTable table2 = new PdfPTable(5);
                        table2.setWidthPercentage(100);


                        /////////////////////////////////////////////////////////
                        //creating cells and data for table1
                        /**You can add more Phrases to accommodate the individual subjects and marks then create more  cells
                         * or you can create a single cell then re-assign everytime with new data and add to the tables
                         * You can replace what's in the strings with the resultSet data that is from the database so you load
                         * data directly to the pdf but before that you have to create a connection above
                         */

                        Phrase ph1 = new Phrase("INTER ASSESS",font1);
                        Phrase ph2 = new Phrase("MID EXAM",font1);
                        Phrase ph3 = new Phrase("  ",font1);
                        Phrase ph4 = new Phrase("   ",font1);
                        Phrase ph5 = new Phrase("MATHS",font1);
                        Phrase phE = new Phrase("ENGLISH",font1);
                        Phrase phS = new Phrase("SCIENCE",font1);
                        Phrase phSST = new Phrase("Social Studies",font1);
                        Phrase phTotal = new Phrase("TOTAL",font1);
                        Phrase phG = new Phrase("GRADE",font1);
                        Phrase ph6 = new Phrase("     ",font1);
                        Phrase res_BotMath = new Phrase(math_Bot,font1);
                        Phrase res_BotEnglish = new Phrase(english_Bot,font1);
                        Phrase res_BotSST = new Phrase(sst_Bot,font1);
                        Phrase res_BotScience = new Phrase(science_Bot,font1);
                        Phrase res_BotTotal = new Phrase(totalValue_Bot,font1);
                        Phrase res_BotGrade = new Phrase(div_Bot,font1);
                        Phrase res_MidMath = new Phrase(math_Mid,font1);
                        Phrase res_MidEnglish = new Phrase(english_Mid,font1);
                        Phrase res_MidSST = new Phrase(sst_Mid,font1);
                        Phrase res_MidScience = new Phrase(science_Mid,font1);
                        Phrase res_MidTotal = new Phrase(totalValue_Mid,font1);
                        Phrase res_MidGrade = new Phrase(div_Mid,font1);

                        PdfPCell cell1 = new PdfPCell(ph1);
                        cell1.setRowspan(2);
                        PdfPCell cell2 = new PdfPCell(ph2);
                        cell2.setRowspan(2);
                        PdfPCell cell3 = new PdfPCell(ph3);
                        PdfPCell cell4 = new PdfPCell(ph4);
                        PdfPCell cell5 = new PdfPCell(ph5);
                        PdfPCell cellG = new PdfPCell(phG);
                        PdfPCell cellE = new PdfPCell(phE);
                        PdfPCell cellS = new PdfPCell(phS);
                        PdfPCell cellSST = new PdfPCell(phSST);
                        PdfPCell cellTotal = new PdfPCell(phTotal);
                        PdfPCell cell6 = new PdfPCell(ph6);
                        PdfPCell c_BotMath = new PdfPCell(res_BotMath);
                        PdfPCell c_BotEnglish = new PdfPCell(res_BotEnglish);
                        PdfPCell c_BotSST = new PdfPCell(res_BotSST);
                        PdfPCell c_BotScience = new PdfPCell(res_BotScience);
                        PdfPCell c_BotTotal = new PdfPCell(res_BotTotal);
                        PdfPCell c_BotGrade = new PdfPCell(res_BotGrade);
                        PdfPCell c_MidMath = new PdfPCell(res_MidMath);
                        PdfPCell c_MidEnglish = new PdfPCell(res_MidEnglish);
                        PdfPCell c_MidSST = new PdfPCell(res_MidSST);
                        PdfPCell c_MidScience = new PdfPCell(res_MidScience);
                        PdfPCell c_MidTotal = new PdfPCell(res_MidTotal);
                        PdfPCell c_MidGrade = new PdfPCell(res_MidGrade);
                        /////////////////////////////////////////////////////

                        //creating cells and data for table2
                        Phrase ph11 = new Phrase("SUBJECT",font1);
                        Phrase ph12 = new Phrase("%",font1);
                        Phrase ph13 = new Phrase("AGG",font1);
                        Phrase ph14 = new Phrase("COMMENT",font1);
                        Phrase ph15 = new Phrase("GRADING",font1);
                        Phrase ph16 = new Phrase("MATHEMATICS",font1);
                        Phrase ph1E = new Phrase("ENGLISH",font1);
                        Phrase ph1S = new Phrase("SCIENCE",font1);
                        Phrase ph1SST = new Phrase("SOCIAL STUDIES",font1);
                        Phrase ph17 = new Phrase("TOTAL",font1);
                        //////////////////////////////////////////////////
                        /**Use a results values below instead of the ---- string so data you load data directly from the database
                         * to the pdf report.
                         * but you have to create more phrases to add all data or use a loop carefully to add data to pdf
                         */
                        Phrase ph18 = new Phrase("  ",font1);
                        ///////////////////////////////////////////////

                        /////////////////////
                        Paragraph grades = new Paragraph("(A).90-100=> D1 (B).80-89=> D2 " +
                                "(C).70-79=> C3 (D).60-69=> C4 (E).55-59=> C5 (F).50-54=> C6 (G).45-49=> P7 (H).40-44=> P8 (I).0-39=> F9",font2);
                        grades.setAlignment(Element.ALIGN_CENTER);
                        //////////////////////////////////

                        PdfPCell cl1 = new PdfPCell(ph11);
                        PdfPCell cl2 = new PdfPCell(ph12);
                        PdfPCell cl3 = new PdfPCell(ph13);
                        PdfPCell cl4 = new PdfPCell(ph14);
                        PdfPCell cl5 = new PdfPCell(ph15);
                        PdfPCell cl6 = new PdfPCell(ph16);
                        PdfPCell clE = new PdfPCell(ph1E);
                        PdfPCell clS = new PdfPCell(ph1S);
                        PdfPCell clSST = new PdfPCell(ph1SST);
                        PdfPCell cl7 = new PdfPCell(ph17);
                        PdfPCell cl8 = new PdfPCell(ph18);

                        PdfPCell cl9 = new PdfPCell(grades);
                        cl9.setRowspan(9);
                        //////////////////////////////////////////////

                        //adding cells to table1
                        for (int i = 1; i<=7; i++){
                            if (i == 1)table1.addCell(cell1);
                            else if(i == 2)table1.addCell(cell5);
                            else if(i == 3) table1.addCell(cellE);
                            else if(i == 4) table1.addCell(cellS);
                            else if(i == 5) table1.addCell(cellSST);
                            else if(i == 6) table1.addCell(cellTotal);
                            else if(i == 7) table1.addCell(cellG);

                        }

                        //adding marks
                        for (int i = 1; i<= 6; i++) {
                            if (i == 1)table1.addCell(c_BotMath);
                            else if(i == 2)table1.addCell(c_BotEnglish);
                            else if(i == 3) table1.addCell(c_BotScience);
                            else if(i == 4) table1.addCell(c_BotSST);
                            else if(i == 5) table1.addCell(c_BotTotal);
                            else if(i == 6) table1.addCell(c_BotGrade);
                        }

                        //adding mid exam marks

                        for (int i = 1; i<=7; i++){
                            if (i == 1) {
                                table1.addCell(cell2);
                            }else {
                                if (i == 2)table1.addCell(c_MidMath);
                                else if(i == 3)table1.addCell(c_MidEnglish);
                                else if(i == 4) table1.addCell(c_MidScience);
                                else if(i == 5) table1.addCell(c_MidSST);
                                else if(i == 6) table1.addCell(c_MidTotal);
                                else if(i == 7) table1.addCell(c_MidGrade);
                            }
                        }
                        for (int i = 1; i<= 7; i++) table1.addCell(cell4);
                        ////////////////////////////////////////////////////////////////////////////////////////

                        //adding cells to table2 manually
                        table2.addCell(cl1);
                        table2.addCell(cl2);
                        table2.addCell(cl3);
                        table2.addCell(cl4);
                        table2.addCell("    ");

                        //creating phrases for results
                        Phrase resMath = new Phrase(math,font1);
                        Phrase resEnglish = new Phrase(english,font1);
                        Phrase resSST = new Phrase(sst,font1);
                        Phrase resScience = new Phrase(science,font1);
                        Phrase resTotal = new Phrase(totalValue,font1);
                        Phrase aM = new Phrase(aggMath,font1);
                        Phrase aE = new Phrase(aggEng,font1);
                        Phrase aSS = new Phrase(aggSST,font1);
                        Phrase aS = new Phrase(aggScience,font1);
                        Phrase aT = new Phrase(aggTotal,font1);
                        Phrase commentMath = new Phrase(commM,font1);
                        Phrase commentEnglish = new Phrase(commE,font1);
                        Phrase commentScience = new Phrase(commS,font1);
                        Phrase commentSST = new Phrase(commSST,font1);


                        PdfPCell cMath = new PdfPCell(resMath);
                        PdfPCell cEnglish = new PdfPCell(resEnglish);
                        PdfPCell cSST = new PdfPCell(resSST);
                        PdfPCell cScience = new PdfPCell(resScience);
                        PdfPCell cTotal = new PdfPCell(resTotal);
                        PdfPCell cAm = new PdfPCell(aM);
                        PdfPCell cAe = new PdfPCell(aE);
                        PdfPCell cAs = new PdfPCell(aS);
                        PdfPCell cAss = new PdfPCell(aSS);
                        PdfPCell cAt = new PdfPCell(aT);
                        PdfPCell comE = new PdfPCell(commentEnglish);
                        PdfPCell comM = new PdfPCell(commentMath);
                        PdfPCell comS = new PdfPCell(commentScience);
                        PdfPCell comSST = new PdfPCell(commentSST);

                        //adding cells with a loop
                        for (int i = 1; i<=22; i++){
                            if (i == 1) table2.addCell(cl6);
                            else if (i == 2) table2.addCell(cMath);
                            else if (i == 3) table2.addCell(cAm);
                            else if (i == 4) table2.addCell(comM);
                            else if (i == 5) table2.addCell(cl5);
                            else if (i == 6) table2.addCell(clE);
                            else if (i == 7) table2.addCell(cEnglish);
                            else if (i == 8) table2.addCell(cAe);
                            else if (i == 9) table2.addCell(comE);
                            else if (i == 10)table2.addCell(cl9);
                            else if (i == 11) table2.addCell(clS);
                            else if (i == 12) table2.addCell(cScience);
                            else if (i == 13) table2.addCell(cAs);
                            else if (i == 14) table2.addCell(comS);
                            else if (i == 15) table2.addCell(clSST);
                            else if (i == 16) table2.addCell(cSST);
                            else if (i == 17) table2.addCell(cAss);
                            else if (i == 18) table2.addCell(comSST);
                            else if (i == 19)table2.addCell(cl7);
                            else if (i == 20)table2.addCell(cTotal);
                            else if (i == 21)table2.addCell(cAt);
                            else if (i == 22)table2.addCell("");
                            else table2.addCell(cl8);

                        }
                        /////////////////////////////////////////////////////////////////////////////////////////

                        document.add(new Paragraph(" "));
                        document.add(table1);
                        document.add(p12);
                        document.add(table2);
                        document.add(new Paragraph(" "));
                        document.add(new Paragraph("CLASS TEACHER'S REPORT:",font1));
                        document.add(new Paragraph("Total Marks: "+totalValue+"                   Total Aggregates: "+aggTotal+"             Grade: "+div));
                        document.add(new Paragraph("Remarks: "+ClassTeacherReport_Upper(div)));
                        document.add(new Paragraph(""));
                        document.add(new Paragraph("GENERAL REPORT:",font1));
                        document.add(new Paragraph("Cleanliness: Generally Clean and organized"));
                        document.add(new Paragraph("Conduct: Very Good                                               C/T Sign:__________________"));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("HeadMaster/HeadMistress' Report :"+HeadTeacherReport_Upper(div),font1));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("Next Term Starts On:...........................",font1));
                        document.add(new Paragraph("1. This Report was issued without any erasure or alteration whatsoever"));
                        document.add(new Paragraph("2. This Report is not valid without HeadMaster's official stamp and signature"));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("  "));
                        Paragraph motto = new Paragraph("\"BUILDING SOLID FOUNDATION ON THE ROCK\" Matt 5:24-29",font1);
                        motto.setAlignment(Element.ALIGN_CENTER);
                        document.add(motto);

                        document.add(new Paragraph(" "));
                        document.add(new Paragraph(" "));
                        document.add(new Paragraph(String.valueOf(date),font2));


                        document.close();
                        JOptionPane.showMessageDialog(null,"Report Generated Successfully");
                        id.clear();
                        name.clear();
                    }else{
                        JOptionPane.showMessageDialog(null,"Student details do not match or Student doesn't exist in our database");
                    }
                }else if(std_class.equalsIgnoreCase("Lower primary")){
                    String div="",math = "",english="",lit1="",lit2="",reading="",re="",totalValue = "",aggMath="",aggEng="",aggLit1="",aggLit2="",aggRe="",aggReading="",aggTotal = "",commE="",commLit1="",commLit2="",commM="",commReading="",commRe="";
                    String div_Bot="",math_Bot = "",english_Bot="",lit1_Bot="",lit2_Bot="",reading_Bot="",re_Bot="",totalValue_Bot = "";
                    String div_Mid="",math_Mid = "",english_Mid="",lit1_Mid="",lit2_Mid="",reading_Mid="",re_Mid="",totalValue_Mid = "";
                    //ResultSet from DB

                    //Beginning of Term
                    String query_Bot = "select * from  pupil,bot_l where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 2";
                    rb = statement.executeQuery(query_Bot);
                    if(rb.next()) {
                        String stdClass = rb.getString("Class");
                        int qBot_Math = rb.getInt("math");
                        int qBot_English = rb.getInt("eng");
                        int qBot_Lit1 = rb.getInt("lit1");
                        int qBot_Lit2 = rb.getInt("lit2");
                        int qBot_Reading = rb.getInt("reading");
                        int qBot_Re = rb.getInt("re");
                        Mid_LowerPrimaryController pr = new Mid_LowerPrimaryController();
                        int agBot_Math = pr.getAgg(qBot_Math);
                        int agBot_English = pr.getAgg(qBot_English);
                        int agBot_Lit1 = pr.getAgg(qBot_Lit1);
                        int agBot_Reading = pr.getAgg(qBot_Reading);
                        int totalAgg_Bot = agBot_Math + agBot_English + agBot_Lit1 + agBot_Reading;
                        div_Bot = pr.getDiv(totalAgg_Bot);
                        math_Bot = Integer.toString(qBot_Math);
                        english_Bot = Integer.toString(qBot_English);
                        lit1_Bot = Integer.toString(qBot_Lit1);
                        lit2_Bot = Integer.toString(qBot_Lit2);
                        re_Bot = Integer.toString(qBot_Re);
                        reading_Bot = Integer.toString(qBot_Reading);
                        totalValue_Bot = Integer.toString(qBot_English+qBot_Lit2+qBot_Reading+qBot_Lit1+qBot_Re+qBot_Math);
                    }
                    else{
                        div_Bot = "";
                        math_Bot = "";
                        english_Bot = "";
                        lit1_Bot = "";
                        lit2_Bot = "";
                        re_Bot = "";
                        reading_Bot = "";
                        totalValue_Bot = "";
                    }

                    //Mid Term
                    String query_Mid = "select * from  pupil,mid_l where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 2";
                    rm = statement.executeQuery(query_Mid);
                    if(rm.next()) {
                        String stdClass = rm.getString("Class");
                        int qMid_Math = rm.getInt("math");
                        int qMid_English = rm.getInt("eng");
                        int qMid_Lit1 = rm.getInt("lit1");
                        int qMid_Lit2 = rm.getInt("lit2");
                        int qMid_Reading = rm.getInt("reading");
                        int qMid_Re = rm.getInt("re");
                        Mid_LowerPrimaryController pr = new Mid_LowerPrimaryController();
                        int agMid_Math = pr.getAgg(qMid_Math);
                        int agMid_English = pr.getAgg(qMid_English);
                        int agMid_Lit1 = pr.getAgg(qMid_Lit1);
                        int agMid_Reading = pr.getAgg(qMid_Reading);
                        int totalAgg_Mid = agMid_Math + agMid_English + agMid_Lit1 + agMid_Reading;
                        div_Mid = pr.getDiv(totalAgg_Mid);
                        math_Mid = Integer.toString(qMid_Math);
                        english_Mid = Integer.toString(qMid_English);
                        lit1_Mid = Integer.toString(qMid_Lit1);
                        lit2_Mid = Integer.toString(qMid_Lit2);
                        re_Mid = Integer.toString(qMid_Re);
                        reading_Mid = Integer.toString(qMid_Reading);
                        totalValue_Mid = Integer.toString(qMid_English+qMid_Lit2+qMid_Reading+qMid_Lit1+qMid_Re+qMid_Math);
                    }else{
                        div_Mid = "";
                        math_Mid = "";
                        english_Mid = "";
                        lit1_Mid = "";
                        lit2_Mid = "";
                        re_Mid = "";
                        reading_Mid = "";
                        totalValue_Mid = "";
                    }


                    //End of Term
                    String query_End = "select * from  pupil,end_l where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 2";
                    rs = statement.executeQuery(query_End);
                    if(rs.next()) {
                        String stdClass = rs.getString("Class");
                        document.add(new Paragraph("Term: " + term.getValue() + "                                            Class: " + stdClass,font1));
                        int q_Math = rs.getInt("math");
                        int q_English = rs.getInt("eng");
                        int qLit1 = rs.getInt("lit1");
                        int qLit2 = rs.getInt("lit2");
                        int qReading = rs.getInt("reading");
                        int qRe = rs.getInt("re");
                        Mid_LowerPrimaryController pr = new Mid_LowerPrimaryController();
                        int agMath = pr.getAgg(q_Math);
                        int agEnglish = pr.getAgg(q_English);
                        int agLit1 = pr.getAgg(qLit1);
                        int agLit2 = pr.getAgg(qLit2);
                        int agRe = pr.getAgg(qRe);
                        int agReading = pr.getAgg(qReading);
                        int totalAgg = agMath + agEnglish + agLit1 + agReading;
                        div = pr.getDiv(totalAgg);
                        aggMath = Integer.toString(agMath);
                        aggEng = Integer.toString(agEnglish);
                        aggLit1 = Integer.toString(agLit1);
                        aggReading = Integer.toString(agReading);
                        aggLit2 = Integer.toString(agLit2);
                        aggRe = Integer.toString(agRe);
                        aggTotal = Integer.toString(totalAgg);
                        math = Integer.toString(q_Math);
                        english = Integer.toString(q_English);
                        lit1 = Integer.toString(qLit1);
                        lit2 = Integer.toString(qLit2);
                        re = Integer.toString(qRe);
                        reading = Integer.toString(qReading);
                        commE = getComment(q_English);
                        commM = getComment(q_Math);
                        commLit1 = getComment(qLit1);
                        commLit2 = getComment(qLit2);
                        commRe = getComment(qRe);
                        commReading = getComment(qReading);
                        totalValue = Integer.toString((rs.getInt("math") + rs.getInt("lit1") + rs.getInt("lit2") + rs.getInt("re")+rs.getInt("reading")+rs.getInt("eng")));
                        document.add(new Paragraph("  "));

                        Paragraph p1 = new Paragraph("PUPIL'S ASSESSMENT REPORT\n\n", font);
                        Paragraph p12 = new Paragraph("\nEND OF TERM PUPIL'S PERFORMANCE\n\n", font);
                        p1.setAlignment(Element.ALIGN_CENTER);
                        p1.setFont(new Font(Font.FontFamily.HELVETICA, Font.UNDERLINE));
                        p12.setAlignment(Element.ALIGN_CENTER);
                        document.add(p1);


                        ///////////////////////////////////////////////////////////
                        //Creating the tables
                        PdfPTable table1 = new PdfPTable(9);
                        table1.setWidthPercentage(100);
                        PdfPTable table2 = new PdfPTable(5);
                        table2.setWidthPercentage(100);

                        Phrase ph1 = new Phrase("INTER ASSESS", font1);
                        Phrase ph2 = new Phrase("MID EXAM", font1);
                        Phrase ph3 = new Phrase("  ", font1);
                        Phrase ph4 = new Phrase("   ", font1);
                        Phrase ph5 = new Phrase("Math", font1);
                        Phrase phE = new Phrase("English", font1);
                        Phrase phLit1 = new Phrase("Lit 1", font1);
                        Phrase phLit2 = new Phrase("Lit 2", font1);
                        Phrase phRe = new Phrase("Reading", font1);
                        Phrase phReading = new Phrase("Re", font1);
                        Phrase phTotal = new Phrase("TOTAL", font1);
                        Phrase phG = new Phrase("GRADE", font1);
                        Phrase ph6 = new Phrase("     ", font1);
                        Phrase res_BotMath = new Phrase(math_Bot, font1);
                        Phrase res_BotEnglish = new Phrase(english_Bot, font1);
                        Phrase res_BotLit1 = new Phrase(lit1_Bot, font1);
                        Phrase res_BotLit2 = new Phrase(lit2_Bot, font1);
                        Phrase res_BotReading = new Phrase(reading_Bot, font1);
                        Phrase res_BotRe = new Phrase(re_Bot, font1);
                        Phrase res_BotTotal = new Phrase(totalValue_Bot, font1);
                        Phrase res_BotGrade = new Phrase(div_Bot, font1);
                        Phrase res_MidMath = new Phrase(math_Mid, font1);
                        Phrase res_MidEnglish = new Phrase(english_Mid, font1);
                        Phrase res_MidLit1 = new Phrase(lit1_Mid, font1);
                        Phrase res_MidLit2 = new Phrase(lit2_Mid, font1);
                        Phrase res_MidRe = new Phrase(re_Mid, font1);
                        Phrase res_MidReading = new Phrase(reading_Mid, font1);
                        Phrase res_MidTotal = new Phrase(totalValue_Mid, font1);
                        Phrase res_MidGrade = new Phrase(div_Mid, font1);

                        PdfPCell cell1 = new PdfPCell(ph1);
                        cell1.setRowspan(2);
                        PdfPCell cell2 = new PdfPCell(ph2);
                        cell2.setRowspan(2);
                        PdfPCell cell3 = new PdfPCell(ph3);
                        PdfPCell cell4 = new PdfPCell(ph4);
                        PdfPCell cell5 = new PdfPCell(ph5);
                        PdfPCell cellG = new PdfPCell(phG);
                        PdfPCell cellE = new PdfPCell(phE);
                        PdfPCell cellLit1 = new PdfPCell(phLit1);
                        PdfPCell cellLit2 = new PdfPCell(phLit2);
                        PdfPCell cellRe = new PdfPCell(phRe);
                        PdfPCell cellReading = new PdfPCell(phReading);
                        PdfPCell cellTotal = new PdfPCell(phTotal);
                        PdfPCell cell6 = new PdfPCell(ph6);
                        PdfPCell c_BotMath = new PdfPCell(res_BotMath);
                        PdfPCell c_BotEnglish = new PdfPCell(res_BotEnglish);
                        PdfPCell c_BotLit2 = new PdfPCell(res_BotLit2);
                        PdfPCell c_BotLit1 = new PdfPCell(res_BotLit1);
                        PdfPCell c_BotRe = new PdfPCell(res_BotRe);
                        PdfPCell c_BotReading = new PdfPCell(res_BotReading);
                        PdfPCell c_BotTotal = new PdfPCell(res_BotTotal);
                        PdfPCell c_BotGrade = new PdfPCell(res_BotGrade);
                        PdfPCell c_MidMath = new PdfPCell(res_MidMath);
                        PdfPCell c_MidEnglish = new PdfPCell(res_MidEnglish);
                        PdfPCell c_MidLit2 = new PdfPCell(res_MidLit2);
                        PdfPCell c_MidLit1 = new PdfPCell(res_MidLit1);
                        PdfPCell c_MidRe = new PdfPCell(res_MidRe);
                        PdfPCell c_MidReading = new PdfPCell(res_MidReading);
                        PdfPCell c_MidTotal = new PdfPCell(res_MidTotal);
                        PdfPCell c_MidGrade = new PdfPCell(res_MidGrade);
                        /////////////////////////////////////////////////////

                        //creating cells and data for table2
                        Phrase ph11 = new Phrase("SUBJECT", font1);
                        Phrase ph12 = new Phrase("%", font1);
                        Phrase ph13 = new Phrase("AGG", font1);
                        Phrase ph14 = new Phrase("COMMENT", font1);
                        Phrase ph15 = new Phrase("GRADING", font1);
                        Phrase ph16 = new Phrase("MATHEMATICS", font1);
                        Phrase ph1E = new Phrase("ENGLISH", font1);
                        Phrase ph1Lit1 = new Phrase("LIT 1A", font1);
                        Phrase ph1Lit2 = new Phrase("LIT 2", font1);
                        Phrase ph1Re = new Phrase("RE", font1);
                        Phrase ph1Reading = new Phrase("LIT 1B", font1);
                        Phrase ph17 = new Phrase("TOTAL", font1);
                        Phrase ph18 = new Phrase("  ", font1);
                        ///////////////////////////////////////////////

                        /////////////////////
                        Paragraph grades = new Paragraph("\n(A).90-100=> D1 (B).85-89=> D2 " +
                                "(C).75-84=> C3 (D).65-74=> C4 (E).60-64=> C5 (F).55-59=> C6 (G).50-54=> P7 (H).45-49=> P8 (I).0-44=> F9",font2);
                        grades.setAlignment(Element.ALIGN_CENTER);
                        //////////////////////////////////

                        PdfPCell cl1 = new PdfPCell(ph11);
                        PdfPCell cl2 = new PdfPCell(ph12);
                        PdfPCell cl3 = new PdfPCell(ph13);
                        PdfPCell cl4 = new PdfPCell(ph14);
                        PdfPCell cl5 = new PdfPCell(ph15);
                        PdfPCell cl6 = new PdfPCell(ph16);
                        PdfPCell clE = new PdfPCell(ph1E);
                        PdfPCell clLit1 = new PdfPCell(ph1Lit1);
                        PdfPCell clLit2 = new PdfPCell(ph1Lit2);
                        PdfPCell clRe = new PdfPCell(ph1Re);
                        PdfPCell clReading = new PdfPCell(ph1Reading);
                        PdfPCell cl7 = new PdfPCell(ph17);
                        PdfPCell cl8 = new PdfPCell(ph18);

                        PdfPCell cl9 = new PdfPCell(grades);
                        cl9.setRowspan(9);
                        //////////////////////////////////////////////

                        //adding cells to table1
                        for (int i = 1; i <= 9; i++) {
                            if (i == 1) table1.addCell(cell1);
                            else if (i == 2) table1.addCell(cell5);
                            else if (i == 3) table1.addCell(cellE);
                            else if (i == 4) table1.addCell(cellLit1);
                            else if (i == 5) table1.addCell(cellLit2);
                            else if (i == 6) table1.addCell(cellReading);
                            else if (i == 7) table1.addCell(cellRe);
                            else if (i == 8) table1.addCell(cellTotal);
                            else if (i == 9) table1.addCell(cellG);

                        }

                        //adding marks
                        for (int i = 1; i <= 8; i++) {
                            if (i == 1) table1.addCell(c_BotMath);
                            else if (i == 2) table1.addCell(c_BotEnglish);
                            else if (i == 3) table1.addCell(c_BotLit1);
                            else if (i == 4) table1.addCell(c_BotLit2);
                            else if (i == 5) table1.addCell(c_BotRe);
                            else if (i == 6) table1.addCell(c_BotReading);
                            else if (i == 7) table1.addCell(c_BotTotal);
                            else if (i == 8) table1.addCell(c_BotGrade);
                        }

                        //adding mid exam marks

                        for (int i = 1; i <= 9; i++) {
                            if (i == 1) table1.addCell(cell2);
                            else {
                                if (i == 2) table1.addCell(c_MidMath);
                                else if (i == 3) table1.addCell(c_MidEnglish);
                                else if (i == 4) table1.addCell(c_MidLit1);
                                else if (i == 5) table1.addCell(c_MidLit2);
                                else if (i == 6) table1.addCell(c_MidReading);
                                else if (i == 7) table1.addCell(c_MidRe);
                                else if (i == 8) table1.addCell(c_MidTotal);
                                else if (i == 9) table1.addCell(c_MidGrade);
                            }
                        }
                        for (int i = 1; i <= 9; i++) table1.addCell(cell4);
                        ////////////////////////////////////////////////////////////////////////////////////////

                        //adding cells to table2 manually
                        table2.addCell(cl1);
                        table2.addCell(cl2);
                        table2.addCell(cl3);
                        table2.addCell(cl4);
                        table2.addCell("    ");

                        //creating phrases for results
                        Phrase resMath = new Phrase(math, font1);
                        Phrase resEnglish = new Phrase(english, font1);
                        Phrase resLit1 = new Phrase(lit1, font1);
                        Phrase resLit2 = new Phrase(lit2, font1);
                        Phrase resReading = new Phrase(reading, font1);
                        Phrase resRe = new Phrase(re, font1);
                        Phrase resTotal = new Phrase(totalValue, font1);
                        Phrase aM = new Phrase(aggMath, font1);
                        Phrase aE = new Phrase(aggEng, font1);
                        Phrase aLit2 = new Phrase(aggLit2, font1);
                        Phrase aLit1 = new Phrase(aggLit1, font1);
                        Phrase aRe = new Phrase(aggRe, font1);
                        Phrase aReading = new Phrase(aggReading, font1);
                        Phrase aT = new Phrase(aggTotal, font1);
                        Phrase commentMath = new Phrase(commM, font1);
                        Phrase commentEnglish = new Phrase(commE, font1);
                        Phrase commentLit1 = new Phrase(commLit1, font1);
                        Phrase commentLit2 = new Phrase(commLit2, font1);
                        Phrase commentReading = new Phrase(commReading, font1);
                        Phrase commentRe = new Phrase(commRe, font1);


                        PdfPCell cMath = new PdfPCell(resMath);
                        PdfPCell cEnglish = new PdfPCell(resEnglish);
                        PdfPCell cLit2 = new PdfPCell(resLit2);
                        PdfPCell cLit1 = new PdfPCell(resLit1);
                        PdfPCell cRe = new PdfPCell(resRe);
                        PdfPCell cReading = new PdfPCell(resReading);
                        PdfPCell cTotal = new PdfPCell(resTotal);
                        PdfPCell cAm = new PdfPCell(aM);
                        PdfPCell cAe = new PdfPCell(aE);
                        PdfPCell cA_lit1 = new PdfPCell(aLit1);
                        PdfPCell cA_lit2 = new PdfPCell(aLit2);
                        PdfPCell cA_Re = new PdfPCell(aRe);
                        PdfPCell cA_Reading = new PdfPCell(aReading);
                        PdfPCell cAt = new PdfPCell(aT);
                        PdfPCell comE = new PdfPCell(commentEnglish);
                        PdfPCell comM = new PdfPCell(commentMath);
                        PdfPCell comLit1 = new PdfPCell(commentLit1);
                        PdfPCell comLit2 = new PdfPCell(commentLit2);
                        PdfPCell comRe = new PdfPCell(commentRe);
                        PdfPCell comReading = new PdfPCell(commentReading);

                        //adding cells with a loop
                        for (int i = 1; i <= 30; i++) {
                            if (i == 1) table2.addCell(cl6);
                            else if (i == 2) table2.addCell(cMath);
                            else if (i == 3) table2.addCell(cAm);
                            else if (i == 4) table2.addCell(comM);
                            else if (i == 5) table2.addCell(cl5);
                            else if (i == 6) table2.addCell(clE);
                            else if (i == 7) table2.addCell(cEnglish);
                            else if (i == 8) table2.addCell(cAe);
                            else if (i == 9) table2.addCell(comE);
                            else if (i == 10) table2.addCell(cl9);
                            else if (i == 11) table2.addCell(clLit1);
                            else if (i == 12) table2.addCell(cLit1);
                            else if (i == 13) table2.addCell(cA_lit1);
                            else if (i == 14) table2.addCell(comLit1);
                            else if (i == 15) table2.addCell(clReading);
                            else if (i == 16) table2.addCell(cReading);
                            else if (i == 17) table2.addCell(cA_Reading);
                            else if (i == 18) table2.addCell(comReading);
                            else if (i == 19) table2.addCell(clLit2);
                            else if (i == 20) table2.addCell(cLit2);
                            else if (i == 21) table2.addCell(cA_lit2);
                            else if (i == 22) table2.addCell(comLit2);
                            else if (i == 23) table2.addCell(clRe);
                            else if (i == 24) table2.addCell(cRe);
                            else if (i == 25) table2.addCell(cA_Re);
                            else if (i == 26) table2.addCell(comRe);
                            else if (i == 27) table2.addCell(cl7);
                            else if (i == 28) table2.addCell(cTotal);
                            else if (i == 29) table2.addCell(cAt);
                            else if (i == 30) table2.addCell("");
                            else table2.addCell(cl8);

                        }
                        /////////////////////////////////////////////////////////////////////////////////////////

                        document.add(new Paragraph(" "));
                        document.add(table1);
                        document.add(p12);
                        document.add(table2);
                        document.add(new Paragraph(" "));
                        document.add(new Paragraph("CLASS TEACHER'S REPORT:",font1));
                        document.add(new Paragraph("Total Marks: " + totalValue + "                   Total Aggregates: " + aggTotal + "             Div: " + div));
                        document.add(new Paragraph("Remarks: "+ClassTeacherReport_Lower(div)));
                        document.add(new Paragraph(""));
                        document.add(new Paragraph("GENERAL REPORT:",font1));
                        document.add(new Paragraph("Cleanliness: Very Clean and organized"));
                        document.add(new Paragraph("Conduct: Very Disciplined and Organized                                               C/T Sign:____________________"));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("HeadMaster/HeadMistress' Report:"+HeadTeacherReport_Lower(div),font1));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("Next Term Starts On:..........................",font1));
                        document.add(new Paragraph("1. This Report was issued without any erasure or alteration whatsoever"));
                        document.add(new Paragraph("2. This Report is not valid without HeadMaster's official stamp and signature"));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("  "));
                        Paragraph motto = new Paragraph("\"BUILDING SOLID FOUNDATION ON THE ROCK\" Prov 4:7", font1);
                        motto.setAlignment(Element.ALIGN_CENTER);
                        document.add(motto);

                        document.add(new Paragraph(String.valueOf(date),font2));

                        document.close();
                        JOptionPane.showMessageDialog(null, "Report Generated Successfully");
                        id.clear();
                        name.clear();
                    }else{
                        JOptionPane.showMessageDialog(null,"Student details do not match or Student doesn't exist in our database");
                    }
                }else if(std_class.equalsIgnoreCase("nursery")){
                    String shading="",bkWorking="",sharing="",games="",E_comp="",E_spoken = "",err="",numbers="",reading="",alphabet = "",lrr="",singing="",drawing="",cleanliness="",gb = "",th="";
                    //ResultSet from DB
                    String q = "select * from  pupil,nursery where ID = pupil_ID and ID = '"+index+"' and Name = '"+std_name+"' and Level = 1";
                    rs = statement.executeQuery(q);
                    if(rs.next()){
                        String stdClass = rs.getString("Class");
                        document.add(new Paragraph("Term: "+term.getValue()+"                                            Class: "+stdClass,font1));
                        document.add(new Paragraph("  "));
                        E_comp = rs.getString("comprehension");
                        E_spoken = rs.getString("spoken");
                        err = rs.getString("RReadiness");
                        numbers = rs.getString("numbers");
                        reading = rs.getString("reading");
                        alphabet = rs.getString("alphabet");
                        lrr = rs.getString("luganda");
                        singing = rs.getString("singing");
                        drawing = rs.getString("drawing");
                        cleanliness = rs.getString("cleanliness");
                        gb = rs.getString("GBehaviour");
                        th = rs.getString("THabits");
                        shading = rs.getString("shading");
                        sharing = rs.getString("sharing");
                        games = rs.getString("games");
                        bkWorking = rs.getString("BWorking");

                        //Creating the table
                        PdfPTable std_table = new PdfPTable(4);
                        std_table.setWidthPercentage(100);

                        Phrase ph1 = new Phrase("ENGLISH COMPREHENSION",font2);
                        Phrase ph2 = new Phrase("ENGLISH SPOKEN",font);
                        Phrase ph3 = new Phrase("  ",font1);
                        Phrase ph4 = new Phrase("   ",font1);
                        Phrase ph5 = new Phrase("ENGLISH READING READINESS",font2);
                        Phrase ph6 = new Phrase("SHARING",font);
                        Phrase ph7 = new Phrase("NUMBERS",font);
                        Phrase ph8 = new Phrase("READING",font);
                        Phrase ph9 = new Phrase("ALPHABET",font);
                        Phrase ph10 = new Phrase("SHADINGS",font);
                        Phrase ph11 = new Phrase("LUGANDA READING READINESS",font2);
                        Phrase ph12 = new Phrase("SINGING",font);
                        Phrase ph13 = new Phrase("DRAWING/PAINTING",font);
                        Phrase ph14 = new Phrase("GAMES",font);
                        Phrase ph15 = new Phrase("CLEANLINESS",font);
                        Phrase ph16 = new Phrase("GENERAL BEHAVIOUR",font2);
                        Phrase ph17 = new Phrase("TOILET BEHAVIOUR",font2);
                        Phrase ph18 = new Phrase("BOOK WORKING",font);

                        PdfPCell cell1 = new PdfPCell(ph1);
                        PdfPCell cell2 = new PdfPCell(ph2);
                        PdfPCell cell3 = new PdfPCell(ph3);
                        PdfPCell cell4 = new PdfPCell(ph4);
                        PdfPCell cell5 = new PdfPCell(ph5);
                        PdfPCell cell6 = new PdfPCell(ph6);
                        PdfPCell cell7 = new PdfPCell(ph7);
                        PdfPCell cell8 = new PdfPCell(ph8);
                        PdfPCell cell9 = new PdfPCell(ph9);
                        PdfPCell cell10 = new PdfPCell(ph10);
                        PdfPCell cell11 = new PdfPCell(ph11);
                        PdfPCell cell12 = new PdfPCell(ph12);
                        PdfPCell cell13 = new PdfPCell(ph13);
                        PdfPCell cell14 = new PdfPCell(ph14);
                        PdfPCell cell15 = new PdfPCell(ph15);
                        PdfPCell cell16 = new PdfPCell(ph16);
                        PdfPCell cell17 = new PdfPCell(ph17);
                        PdfPCell cell18 = new PdfPCell(ph18);



                        PdfPCell[] cells = new PdfPCell[16];
                        cells[0] = cell1;
                        cells[1] = cell2;
                        cells[2] = cell5;
                        cells[3] = cell6;
                        cells[4] = cell7;
                        cells[5] = cell8;
                        cells[6] = cell9;
                        cells[7] = cell10;
                        cells[8] = cell11;
                        cells[9] = cell12;
                        cells[10] = cell13;
                        cells[11] = cell14;
                        cells[12] = cell15;
                        cells[13] = cell16;
                        cells[14] = cell17;
                        cells[15] = cell18;

                        //phrases for results
                        Phrase resPh1 = new Phrase(E_comp,font1);
                        Phrase resPh2 = new Phrase(E_spoken,font1);
                        Phrase resPh3 = new Phrase(err,font1);
                        Phrase resPh4 = new Phrase(sharing,font1);
                        Phrase resPh5 = new Phrase(numbers,font1);
                        Phrase resPh6 = new Phrase(reading,font1);
                        Phrase resPh7 = new Phrase(alphabet,font1);
                        Phrase resPh8 = new Phrase(shading,font1);
                        Phrase resPh9 = new Phrase(lrr,font1);
                        Phrase resPh10 = new Phrase(singing,font1);
                        Phrase resPh11 = new Phrase(drawing,font1);
                        Phrase resPh12 = new Phrase(games,font1);
                        Phrase resPh13 = new Phrase(cleanliness,font1);
                        Phrase resPh14 = new Phrase(gb,font1);
                        Phrase resPh15 = new Phrase(th,font1);
                        Phrase resPh16 = new Phrase(bkWorking,font1);



                        //cells for results
                        PdfPCell resCell1 = new PdfPCell(resPh1);
                        PdfPCell resCell2 = new PdfPCell(resPh2);
                        PdfPCell resCell3 = new PdfPCell(resPh3);
                        PdfPCell resCell4 = new PdfPCell(resPh4);
                        PdfPCell resCell5 = new PdfPCell(resPh5);
                        PdfPCell resCell6 = new PdfPCell(resPh6);
                        PdfPCell resCell7 = new PdfPCell(resPh7);
                        PdfPCell resCell8 = new PdfPCell(resPh8);
                        PdfPCell resCell9 = new PdfPCell(resPh9);
                        PdfPCell resCell10 = new PdfPCell(resPh10);
                        PdfPCell resCell11 = new PdfPCell(resPh11);
                        PdfPCell resCell12 = new PdfPCell(resPh12);
                        PdfPCell resCell13 = new PdfPCell(resPh13);
                        PdfPCell resCell14 = new PdfPCell(resPh14);
                        PdfPCell resCell15 = new PdfPCell(resPh15);
                        PdfPCell resCell16 = new PdfPCell(resPh16);

                        //arrayList for results
                        PdfPCell[] results = new PdfPCell[16];
                        results[0] = resCell1;
                        results[1] = resCell2;
                        results[2] = resCell3;
                        results[3] = resCell4;
                        results[4] = resCell5;
                        results[5] = resCell6;
                        results[6] = resCell7;
                        results[7] = resCell8;
                        results[8] = resCell9;
                        results[9] = resCell10;
                        results[10] = resCell11;
                        results[11] = resCell12;
                        results[12] = resCell13;
                        results[13] = resCell14;
                        results[14] = resCell15;
                        results[15] = resCell16;

                        for(PdfPCell x : results){
                            x.setPaddingLeft(35);
                        }

                        String compImage = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\comprehension.jpg";
                        Image comImage = Image.getInstance(compImage);
                        comImage.scaleAbsolute(70,70);
                        PdfPCell cellComImage = new PdfPCell(comImage);
                        cellComImage.setPadding(10);
                        cellComImage.setPaddingLeft(25);

                        String spokenImage = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\spoken.jpg";
                        Image spkImage = Image.getInstance(spokenImage);
                        spkImage.scaleAbsolute(70,70);
                        PdfPCell cellSpkImage = new PdfPCell(spkImage);
                        cellSpkImage.setPadding(10);
                        cellSpkImage.setPaddingLeft(25);

                        String eReadImage = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\englishReading.jpg";
                        Image erImage = Image.getInstance(eReadImage);
                        erImage.scaleAbsolute(70,70);
                        PdfPCell cellErImage = new PdfPCell(erImage);
                        cellErImage.setPadding(10);
                        cellErImage.setPaddingLeft(25);

                        String share = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\sharing.jpg";
                        Image shareImage = Image.getInstance(share);
                        shareImage.scaleAbsolute(70,70);
                        PdfPCell cellShareImage = new PdfPCell(shareImage);
                        cellShareImage.setPadding(10);
                        cellShareImage.setPaddingLeft(25);

                        String numbersImage  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\numbers.jpg";
                        Image nImage = Image.getInstance(numbersImage);
                        nImage.scaleAbsolute(70,70);
                        PdfPCell cellNumsImage = new PdfPCell(nImage);
                        cellNumsImage.setPadding(10);
                        cellNumsImage.setPaddingLeft(25);

                        String img1  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\reading.png";
                        Image rImage = Image.getInstance(img1);
                        rImage.scaleAbsolute(70,70);
                        PdfPCell cellRImage = new PdfPCell(rImage);
                        cellRImage.setPadding(10);
                        cellRImage.setPaddingLeft(25);

                        String img2  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\alphabet.jpg";
                        Image aImage = Image.getInstance(img2);
                        aImage.scaleAbsolute(70,70);
                        PdfPCell cellAImage = new PdfPCell(aImage);
                        cellAImage.setPadding(10);
                        cellAImage.setPaddingLeft(25);

                        String img3  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\shadings.jpg";
                        Image sImage = Image.getInstance(img3);
                        sImage.scaleAbsolute(70,70);
                        PdfPCell cellSImage = new PdfPCell(sImage);
                        cellSImage.setPadding(10);
                        cellSImage.setPaddingLeft(25);

                        String img4  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\luganda.jpg";
                        Image lImage = Image.getInstance(img4);
                        lImage.scaleAbsolute(70,70);
                        PdfPCell cellLImage = new PdfPCell(lImage);
                        cellLImage.setPadding(10);
                        cellLImage.setPaddingLeft(25);

                        String img5  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\singing.png";
                        Image SiImage = Image.getInstance(img5);
                        SiImage.scaleAbsolute(70,70);
                        PdfPCell cellSiImage = new PdfPCell(SiImage);
                        cellSiImage.setPadding(10);
                        cellSiImage.setPaddingLeft(25);

                        String img6  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\drawing.png";
                        Image dImage = Image.getInstance(img6);
                        dImage.scaleAbsolute(70,70);
                        PdfPCell cellDImage = new PdfPCell(dImage);
                        cellDImage.setPadding(10);
                        cellDImage.setPaddingLeft(25);

                        String img7  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\games.jpg";
                        Image gImage = Image.getInstance(img7);
                        gImage.scaleAbsolute(70,70);
                        PdfPCell cellGImage = new PdfPCell(gImage);
                        cellGImage.setPadding(10);
                        cellGImage.setPaddingLeft(25);

                        String img8  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\cleanliness.jpg";
                        Image cImage = Image.getInstance(img8);
                        cImage.scaleAbsolute(70,70);
                        PdfPCell cellCImage = new PdfPCell(cImage);
                        cellCImage.setPadding(10);
                        cellCImage.setPaddingLeft(25);

                        String img9  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\gh.jpg";
                        Image gbImage = Image.getInstance(img9);
                        gbImage.scaleAbsolute(70,70);
                        PdfPCell cellGbImage = new PdfPCell(gbImage);
                        cellGbImage.setPadding(10);
                        cellGbImage.setPaddingLeft(25);

                        String img10  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\th.jpg";
                        Image thImage = Image.getInstance(img10);
                        thImage.scaleAbsolute(70,70);
                        PdfPCell cellThImage = new PdfPCell(thImage);
                        cellThImage.setPadding(10);
                        cellThImage.setPaddingLeft(25);

                        String img11  = "C:\\Users\\jimda\\IdeaProjects\\Stephen_App\\src\\main\\resources\\com\\example\\stephen_app\\images\\bk.jpg";
                        Image bkImage = Image.getInstance(img11);
                        bkImage.scaleAbsolute(70,70);
                        PdfPCell cellBkImage = new PdfPCell(bkImage);
                        cellBkImage.setPadding(10);
                        cellBkImage.setPaddingLeft(25);

                        //add contents
                        for (int i = 0; i<48; i++) {
                            if(i < 4) std_table.addCell(cells[i]);
                            else if(i == 4) std_table.addCell(cellComImage);
                            else if(i == 5) std_table.addCell(cellSpkImage);
                            else if(i == 6) std_table.addCell(cellErImage);
                            else if(i == 7) std_table.addCell(cellShareImage);
                            else if(i <= 11) std_table.addCell(results[i - 8]);
                            else if(i <= 15) std_table.addCell(cells[i - 8]);
                            else if(i == 16) std_table.addCell(cellNumsImage);
                            else if(i == 17) std_table.addCell(cellRImage);
                            else if(i == 18) std_table.addCell(cellAImage);
                            else if(i == 19) std_table.addCell(cellSImage);
                            else if(i <= 23) std_table.addCell(results[i - 16]);
                            else if(i <= 27) std_table.addCell(cells[i - 16]);
                            else if(i == 28) std_table.addCell(cellLImage);
                            else if(i == 29) std_table.addCell(cellSiImage);
                            else if(i == 30) std_table.addCell(cellDImage);
                            else if(i == 31) std_table.addCell(cellGImage);
                            else if(i <= 35) std_table.addCell(results[i - 24]);
                            else if(i <= 39) std_table.addCell(cells[i - 24]);
                            else if(i == 40) std_table.addCell(cellCImage);
                            else if(i == 41) std_table.addCell(cellGbImage);
                            else if(i == 42) std_table.addCell(cellThImage);
                            else if(i == 43) std_table.addCell(cellBkImage);
                            else std_table.addCell(results[i - 32]);
                        }

                        //creating phrases for results
                        document.add(new Paragraph("  "));
                        document.add(std_table);
                        document.add(new Paragraph("Class Teacher's Report: Very Good",font1));
                        document.add(new Paragraph("Signature:_____________________  ",font1));
                        document.add(new Paragraph("HeadMaster/HeadMistress' Report: Good Performance. Aim Higher",font1));
                        document.add(new Paragraph("Signature:_____________________  ",font1));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("Next Term Starts On:..........................",font1));
                        document.add(new Paragraph("  "));
                        document.add(new Paragraph("  "));


                        document.add(new Paragraph(String.valueOf(date),font2));
                        document.close();
                        JOptionPane.showMessageDialog(null,"Report Generated Successfully");
                        id.clear();
                        name.clear();
                    }else{
                        JOptionPane.showMessageDialog(null,"Student details do not match or Student doesn't exist in our database");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Fill all required fields");
        }
    }
}

