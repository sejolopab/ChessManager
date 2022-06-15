package com.example.ajedrez.view;

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

import com.example.ajedrez.model.Lesson;
import com.example.ajedrez.model.Student;
import com.example.ajedrez.R;
import com.example.ajedrez.utils.DownloadManager;
import com.example.ajedrez.utils.GenericMethodsManager;
import com.example.ajedrez.view.assistance.StudentsAssistListFragment;
import com.example.ajedrez.view.assistance.StudentsAssistanceListener;
import com.example.ajedrez.view.lessons.LessonInfoFragment;
import com.example.ajedrez.view.lessons.LessonsListFragment;
import com.example.ajedrez.view.students.InfoStudentFragment;
import com.example.ajedrez.view.students.NewStudentFragment;
import com.example.ajedrez.view.students.StudentsListFragment;
import com.example.ajedrez.view.students.StudentsListener;
import com.example.ajedrez.view.lessons.LessonsListener;
import com.example.ajedrez.view.subjects.SubjectReaderFragment;
import com.example.ajedrez.view.subjects.SubjectsListFragment;
import com.example.ajedrez.view.subjects.SubjectsListFragment.SubjectsListener;
import com.example.ajedrez.view.students.StudentUpdatedListener;
import com.example.ajedrez.view.lessons.LessonInfoFragment.LessonInfoListener;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StudentsAssistanceListener,
        StudentsListener, LessonsListener, SubjectsListener, StudentUpdatedListener, LessonInfoListener {

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
    public void returnToList() {
        showStudentsFragment();
    }

    @Override
    public void onAssistanceItemClick() {
        hideKeyboard(this);
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
