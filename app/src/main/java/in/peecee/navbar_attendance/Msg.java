package in.peecee.navbar_attendance;


import android.widget.Toast;

public class Msg
{private static  MainActivity MA;
    void SetMA(MainActivity MA){this.MA=MA;}
    static void show(String msg)
    {                  Toast.makeText(MA.getApplicationContext(), msg,Toast.LENGTH_SHORT).show();

    }

}
