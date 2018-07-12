package in.peecee.navbar_attendance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Model
{
    ArrayList<String> Divisions = new ArrayList<String>();
    int currentindex=0;

    String GetDivisionTitle(int index)
    {
        String temp[];
        temp = Divisions.get(index).split("#");
        return temp[0];

    }


    String GetRollStartFinish(int index)
    {
        String temp[];
        temp = Divisions.get(index).split("#");
        return temp[1];

    }


    void LoadDivisions() {
        Divisions.add("IX-A#5001-5120#P");
        Divisions.add("XI-B#141-325#P");
        Divisions.add("XI-C#321-400#P");
        Divisions.add("TY-C#721-800#P");

    }


    void SaveList(String AttendanceLine) {

        int i;
        String txtData = "";
//        modified=false;
        //      String tmpStr;
        boolean newfile=false;

        String FileNameWithPath = "/sdcard/AttendanceData.atd";
        //FileNameWithPath+="Default6.txt";
/*
        for(i=0;i<Divisions.size();i++)
        {txtData+=Divisions.get(i); txtData+='\n';}
*/
        try {
            File myFile = new File(FileNameWithPath);
            if (!myFile.exists()) { newfile=true; myFile.createNewFile();}
            else newfile=false;
            FileOutputStream fOut = new FileOutputStream(myFile,true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            if(newfile)
            {  String RString = "======== Reserved Line 1 =======\n";
                RString+= "======== Reserved Line 2 =======\n";
                myOutWriter.append(RString);
            }
            txtData += GetDateTimeString();
            txtData +="#";
            txtData += GetDivisionTitle(MainActivity.currentDivision);
            txtData += "#";
            txtData += GetRollStartFinish(MainActivity.currentDivision);
            txtData += "#";
            txtData += AttendanceLine;
            txtData += "\n";

            myOutWriter.append(txtData);
            myOutWriter.close();
            fOut.close();

        } catch (Exception e) {
            //Toast.makeText(null, e.getMessage(),
            //     Toast.LENGTH_SHORT).show();
        }
        //if(end) finish();

    }


    String GetDateTimeString()
    {   String date, dow, time;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat df2 = new SimpleDateFormat("E");
        SimpleDateFormat df3 = new SimpleDateFormat("HH:mm");
        date = df1.format(c.getTime());
        dow = df2.format(c.getTime());
        time = df3.format(c.getTime());
        return (date + " - " + dow + " - " + time);
    }

}

///////////////////// Extras
//////////////////////////////////////////////////////////////////////
///To do

        /*
            ///save pdf
            WritePDF wp = new WritePDF();
            wp.SetData(CollegeName1,Clas+"-"+Div,Subject,
                    Examiner,Exam,sMax,Date,PTstr,Set.get(1),Mrk.get(1));
            wp.SetRollArray(Roll,Mrk);
            String pdfname=FileNameWithPath.replaceAll(".mrk",".pdf");
            wp.write(pdfname);

            showtop("Saved in mrk and pdf format");
            //	showtop(pdfname);
*/