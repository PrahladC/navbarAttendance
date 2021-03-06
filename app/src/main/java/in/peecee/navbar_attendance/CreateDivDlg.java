package in.peecee.navbar_attendance;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateDivDlg {
    Model model;
    private MainActivity MA;
    public String tempDivTitle,tempFroll,tempLroll;

    boolean editmode=false;  ///if true Record is refreshed, if false it added to division array


    void SetRef(Model model){this.model=model;}
    void SetMA(MainActivity MA){this.MA=MA;}

    public void show(Context context,String msg)
    {                  Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();

    }
    public void showDialog(final Context context){
        final Dialog dialog = new Dialog(context);
        if(editmode) dialog.setTitle("Edit Division");
        else dialog.setTitle("Add New Divisoin");
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.create_div_dlg);
        dialog.show();

        final EditText ClassDiv = dialog.findViewById(R.id.division);
        final EditText FirstRoll = dialog.findViewById(R.id.firstroll);
        final EditText LastRoll = dialog.findViewById(R.id.lastroll);
        if(editmode) { ///if editmode =true offer current values
            ClassDiv.setText(tempDivTitle);
            FirstRoll.setText(tempFroll);
            LastRoll.setText(tempLroll);
        }
        Button mBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button mBtn_ok = dialog.findViewById(R.id.btn_ok);

        mBtn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        mBtn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { String frollstring=FirstRoll.getText().toString();
                String lrollstring=LastRoll.getText().toString();
                String classdiv=ClassDiv.getText().toString();

                if(frollstring.length()==0 || lrollstring.length()==0)
                    show(context,"Invlid Roll");

                int  in1 = new Integer(frollstring);
                int  in2 = new Integer(lrollstring);
                int strength=in2-in1+1;
                if(strength>500) show(context,"Class Strength > 500");
                if(classdiv.length()==0) show(context,"Class-Division Empty");
                show(context,String.format("%d",strength));
                if(editmode) ///if true add new division to Divisions Array
                {
                    model.Divisions.set(MainActivity.currentDivision, classdiv + "#" + frollstring + "-" + lrollstring + "#" + "PP");
                }
                else
                { /// else replace new division with current division
                    model.Divisions.add(classdiv + "#" + frollstring + "-" + lrollstring + "#" + "PP");
                    MainActivity.currentDivision = model.Divisions.size() - 1;
                }
                MA.DisplayDivision();
                dialog.dismiss();
            }
        });
    }


    void SaveDivisionsInPrefs()
    {   String alldivisions=model.Divisions.get(0);
        if(model.Divisions.size()>1)
        {
            for(int i=1;i< model.Divisions.size();i++) {
                alldivisions += "│";
                alldivisions += model.Divisions.get(i);
            }

        }
        String collegename="SIWS College";
        String subject="Mathematics";
        String email="My Email";
        SharedPreferences settings = MA.getSharedPreferences("DIVS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("key1", alldivisions);
        editor.putString("key2", collegename);
        editor.putString("key3", subject);
        editor.putString("key4", email);

        editor.apply();
//        Msg.show(alldivisions);

    }


    void LoadDivisionsFromPrefs()
    {  SharedPreferences settings = MA.getSharedPreferences("DIVS", 0);
        String alldivisions = settings.getString("key1", "XI-Z");
        String collegename = settings.getString("key2", "SIWS College");
        String subject = settings.getString("key3", "Mathematics");
        String email = settings.getString("key4", "my Email");

//        Msg.show(alldivisions);
        if(alldivisions.contains("│"))
        {model.Divisions.clear();
            String temp[];
            temp=alldivisions.split("│");
            for(int i=0;i<temp.length;i++)
            {
                model.Divisions.add(temp[i]);

            }

        }
        else
        {
            if (alldivisions.contains("#")) {
                model.Divisions.clear();
                model.Divisions.add(alldivisions);
            }

        }

    }


}
