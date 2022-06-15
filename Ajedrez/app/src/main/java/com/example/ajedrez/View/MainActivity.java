package com.example.ajedrez.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.ajedrez.Model.Lesson;
import com.example.ajedrez.Model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.Utils.DownloadManager;
import com.example.ajedrez.Utils.GenericMethodsManager;
import com.example.ajedrez.View.Assistance.StudentsAssistListFragment;
import com.example.ajedrez.View.Assistance.StudentsAssistanceListener;
import com.example.ajedrez.View.Lessons.LessonInfoFragment;
import com.example.ajedrez.View.Lessons.LessonsListFragment;
import com.example.ajedrez.View.Students.InfoStudentFragment;
import com.example.ajedrez.View.Students.NewStudentFragment;
import com.example.ajedrez.View.Students.StudentsListFragment;
import com.example.ajedrez.View.Students.StudentsListener;
import com.example.ajedrez.View.Lessons.LessonsListener;
import com.example.ajedrez.View.Subjects.SubjectReaderFragment;
import com.example.ajedrez.View.Subjects.SubjectsListFragment;
import com.example.ajedrez.View.Subjects.SubjectsListFragment.SubjectsListener;
import com.example.ajedrez.View.Students.NewStudentFragment.NewStudentListener;
import com.example.ajedrez.View.Students.InfoStudentFragment.StudentInfoListener;
import com.example.ajedrez.View.Lessons.LessonInfoFragment.LessonInfoListener;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StudentsAssistanceListener,
        StudentsListener, LessonsListener, SubjectsListener, NewStudentListener, StudentInfoListener, LessonInfoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                hideKeyboard(MainActivity.this);
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showAssistanceFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof StudentsAssistListFragment) {
            StudentsAssistListFragment assistListFragment = (StudentsAssistListFragment) fragment;
            assistListFragment.setListener(this);
        } else if (fragment instanceof StudentsListFragment) {
            StudentsListFragment studentsListFragment = (StudentsListFragment) fragment;
            studentsListFragment.setListener(this);
        } else if (fragment instanceof LessonsListFragment) {
            LessonsListFragment lessonsListFragment = (LessonsListFragment) fragment;
            lessonsListFragment.setListener(this);
        } else if (fragment instanceof SubjectsListFragment) {
            SubjectsListFragment subjectsListFragment = (SubjectsListFragment) fragment;
            subjectsListFragment.setListener(this);
        } else if (fragment instanceof NewStudentFragment) {
            NewStudentFragment newStudentFragment = (NewStudentFragment) fragment;
            newStudentFragment.setListener(this);
        } else if (fragment instanceof InfoStudentFragment) {
            InfoStudentFragment infoStudentFragment = (InfoStudentFragment) fragment;
            infoStudentFragment.setListener(this);
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
        if (id == R.id.action_settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container, new SettingsFragment())
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_students) {
            showStudentsFragment();
        } else if (id == R.id.nav_assistance) {
            showAssistanceFragment();
        } else if (id == R.id.nav_lessons) {
            showLessonsFragment();
        } else if (id == R.id.nav_subjects) {
            showSubjectsFragment();
        } else if (id == R.id.nav_share) {
            DownloadManager.getInstance().download(MainActivity.this, "2_Notacion_Algebraica.pdf");
        } else if (id == R.id.nav_send) {
            GenericMethodsManager.getInstance().sendMessage(this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAssistanceFragment () {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, StudentsAssistListFragment.newInstance())
                .commitAllowingStateLoss();
    }

    private void showStudentsFragment () {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, StudentsListFragment.newInstance())
                .commitAllowingStateLoss();
    }

    private void showLessonsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, LessonsListFragment.Companion.newInstance())
                .commitAllowingStateLoss();
    }

    private void showSubjectsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, SubjectsListFragment.newInstance())
                .commitAllowingStateLoss();
    }

    @Override
    public void showAddStudentScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, NewStudentFragment.newInstance())
                .commitAllowingStateLoss();
    }

    @Override
    public void showStudentInfoScreen(Student student) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, InfoStudentFragment.newInstance(student))
                .commitAllowingStateLoss();
    }

    @Override
    public void showLessonInfoScreen(Lesson lesson) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, LessonInfoFragment.Companion.newInstance(lesson))
                .commitAllowingStateLoss();
    }

    @Override
    public void onStudentCreated() {
        showStudentsFragment();
    }

    @Override
    public void onAssistanceItemClick() {
        hideKeyboard(this);
    }

    @Override
    public void onStudentInfoUpdated() {
        showStudentsFragment();
    }

    @Override
    public void showSubjectReader(String fileName) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content_container, SubjectReaderFragment.newInstance(fileName))
                .commitAllowingStateLoss();
    }

    @Override
    public void showStudentInfo(){

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
