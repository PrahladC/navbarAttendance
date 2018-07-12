package in.peecee.navbar_attendance;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Model model = new Model();
    CreateDivDlg CDD=new CreateDivDlg();
    TextAdapter TA;
    static  int currentDivision = 0;
    TextView divEdit, Date;
    private Button buttonInvert;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TA = new TextAdapter(this);
        final GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(TA);

        for (int i = 0; i < 120; i++) {
            TA.numbers[i] = String.format("%d", 5000 + i + 1);
        }

/*        buttonInvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
        model.LoadDivisions();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                int firstPosition = gridView.getFirstVisiblePosition();
                int childPosition = position - firstPosition;
                TextView txtView = (TextView) gridView.getChildAt(childPosition);
                Integer tt = new Integer(position);
                if (TA.selectedPositions.contains(tt)) {
                    txtView.setBackgroundColor(Color.parseColor("#fbdcbb"));
                    TA.selectedPositions.remove(tt);
                } else {
                    txtView.setBackgroundColor(Color.RED);
                    TA.selectedPositions.add((Integer) position);
                }
            }
        });


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        View view =getSupportActionBar().getCustomView();

        Date = (TextView) findViewById(R.id.date);
        Date.setText(model.GetDateTimeString());

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDivision--;
                TA.Divisions.clear();
                if (currentDivision < 0) currentDivision = model.Divisions.size() - 1;
                TA.DisplayDivision(model.Divisions.get(currentDivision));
                divEdit.setText(model.GetDivisionTitle(currentDivision));
            }
        });

        divEdit = (TextView) findViewById(R.id.divname);

        divEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"You are trying to edit Text", Toast.LENGTH_LONG).show();

                CDD.editmode=true;
                CDD.tempDivTitle=model.GetDivisionTitle(currentDivision);
                String temp,temp2[];
                temp=model.GetRollStartFinish(currentDivision);
                temp2=temp.split("-");
                CDD.tempFroll=temp2[0];
                CDD.tempLroll=temp2[1];
                CDD.showDialog(MainActivity.this);

            }
        });

        divEdit.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            { DeleteDivision(); return true;  }
        });

        ImageButton imageButton2= (ImageButton)view.findViewById(R.id.action_bar_forward);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDivision++;
                TA.Divisions.clear();
                if (currentDivision > model.Divisions.size() - 1) currentDivision = 0;
                TA.DisplayDivision(model.Divisions.get(currentDivision));
                divEdit.setText(model.GetDivisionTitle(currentDivision));

            }
        });


        final ImageButton goButton= (ImageButton)view.findViewById(R.id.Go);
        goButton.setOnClickListener(new View.OnClickListener() {

             public void onClick(View v) {

                 if (!flag) {
                     goButton.setImageResource(R.drawable.web_cam);
                     flag=true;
                 }
                 else {
                     goButton.setImageResource(R.drawable.web_cam_red);
                     flag=false;
                 }
             }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CDD.SetRef(model);
        CDD.SetMA(this);

        CDD.LoadDivisionsFromPrefs();currentDivision=0;
        DisplayDivision();   //on init currentDivision=0;


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.teacher_details) {
            new Teachers_Details_Dlg().showDialog(MainActivity.this);
            return true;
        }

        if (id == R.id.create) {
            CDD.editmode=false;
            // Toast.makeText(getBaseContext(), "create", Toast.LENGTH_SHORT).show();
            CDD.showDialog(MainActivity.this);

            return true;
        }

        if (id == R.id.load) {
            return true;
        }

        if (id == R.id.save) {
            String AL=GetAttendanceLine(); //current AAPAPAPP...
            model.SaveList(AL);
            Toast.makeText(getBaseContext(), "Attendance Saved", Toast.LENGTH_LONG).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_viewreporttilldate) {

        }

        else if (id == R.id.nav_monthlyReport) {
            // Handle the camera action
        } else if (id == R.id.nav_yearlyReport) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    String GetAttendanceLine(){
        String Line="";
        for(int i=0;i<TA.numbers.length;i++){
            Integer tt=new Integer(i);
            if(TA.selectedPositions.contains(tt))
            Line+="A";
            else
            Line+="P";
        }
        return Line;
    }

    void DeleteDivision()
    {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete This Division ?");   // Set a title for alert dialog
        //builder.setMessage("Test Message"); No Message required

        // Set the alert dialog yes button click listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                if(model.Divisions.size()>1)
                {
                    model.Divisions.remove(currentDivision);
                    currentDivision--;
                    if(currentDivision<0) currentDivision=0;
                    DisplayDivision();
                    Toast.makeText(getApplicationContext(),
                            "Division Deleted",Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set the alert dialog no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing on No button clicked
            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    void DisplayDivision()   //// Display division with index currentdivision
    {   TA.Divisions.clear();
        TA.DisplayDivision(model.Divisions.get(currentDivision));
        divEdit.setText(model.GetDivisionTitle(currentDivision));
    }


}
