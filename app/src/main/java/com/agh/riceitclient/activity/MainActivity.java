package com.agh.riceitclient.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.AddFoodDTO;
import com.agh.riceitclient.dto.UpdateFoodDTO;
import com.agh.riceitclient.fragment.MealsFragment;
import com.agh.riceitclient.util.MealsListener;
import com.agh.riceitclient.util.DrawerAdapter;
import com.agh.riceitclient.util.DrawerItem;
import com.agh.riceitclient.util.SimpleItem;
import com.agh.riceitclient.util.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, MealsListener{

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_NEAR = 3;
    private static final int POS_SETTINGS = 4;
    private static final int POS_ABOUT_US = 5;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_NEAR),
                createItemFor(POS_SETTINGS),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.purple_500))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.purple_200))
                .withSelectedTextTint(color(R.color.purple_200));
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray typedArray = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[typedArray.length()];
        for(int i = 0; i < typedArray.length(); i++){
            int id = typedArray.getResourceId(i, 0);
            if(id != 0){
                icons[i] = ContextCompat.getDrawable(this,id);
            }
        }
        typedArray.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_DASHBOARD) {
            MealsFragment mealsFragment = new MealsFragment();
            transaction.replace(R.id.main_container, mealsFragment, "mealsFragment");
        }
        else if (position == POS_MY_PROFILE) {
//            RandomFragment randomFragment = new RandomFragment();
//            transaction.replace(R.id.main_container, randomFragment);
        }
        else if (position == POS_NEAR) {
//            RandomFragment randomFragment = new RandomFragment();
//            transaction.replace(R.id.main_container, randomFragment);
        }
        else if (position == POS_SETTINGS) {
//            RandomFragment randomFragment = new RandomFragment();
//            transaction.replace(R.id.main_container, randomFragment);
        }
        else if (position == POS_ABOUT_US) {
//            RandomFragment randomFragment = new RandomFragment();
//            transaction.replace(R.id.main_container, randomFragment);
        }
        else if (position == POS_LOGOUT) {
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void enqueueUpdateFood(UpdateFoodDTO updateFoodDTO) {
        MealsFragment f = (MealsFragment)getSupportFragmentManager().findFragmentByTag("mealsFragment");
        f.enqueueUpdateFood(updateFoodDTO);
    }

    @Override
    public void enqueueAddFood(AddFoodDTO addFoodDTO) {
        MealsFragment f = (MealsFragment)getSupportFragmentManager().findFragmentByTag("mealsFragment");
        f.enqueueAddFood(addFoodDTO);
    }

    @Override
    public void removeMealOrFood(boolean isMeal, long dataToRemove) {
        MealsFragment f = (MealsFragment)getSupportFragmentManager().findFragmentByTag("mealsFragment");
        f.removeMealOrFood(isMeal, dataToRemove);
    }

    @Override
    public void enqueueCreateMeal() {
        MealsFragment f = (MealsFragment)getSupportFragmentManager().findFragmentByTag("mealsFragment");
        f.enqueueCreateMeal();
    }
}
