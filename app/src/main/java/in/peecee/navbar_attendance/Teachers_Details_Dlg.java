package in.peecee.navbar_attendance;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Teachers_Details_Dlg extends MainActivity {


    public void show(Context context, String msg){
        Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
    }

    public void showDialog(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.teachers_details);
        dialog.show();

        final EditText CollegeName = dialog.findViewById(R.id.college_name);
        final EditText TeacherName = dialog.findViewById(R.id.teacher_name);
        final EditText SubjectName = dialog.findViewById(R.id.subject_name);

        Button mBtn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button mBtn_ok = dialog.findViewById(R.id.btn_ok);

        mBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        mBtn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { String frollstring=TeacherName.getText().toString();
                String lrollstring=SubjectName.getText().toString();
                String classdiv=CollegeName.getText().toString();
            }
        });
    }

}
