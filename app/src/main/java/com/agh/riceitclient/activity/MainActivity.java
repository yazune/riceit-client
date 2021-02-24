package com.agh.riceitclient.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.FoodAddDTO;
import com.agh.riceitclient.dto.SportAddDTO;
import com.agh.riceitclient.dto.ManualParametersDTO;
import com.agh.riceitclient.dto.UserDetailsUpdateDTO;
import com.agh.riceitclient.fragment.GoalFragment;
import com.agh.riceitclient.fragment.MealFragment;
import com.agh.riceitclient.fragment.SportFragment;
import com.agh.riceitclient.fragment.UserDetailsFragment;
import com.agh.riceitclient.listener.UserDetailsListener;
import com.agh.riceitclient.listener.GoalListener;
import com.agh.riceitclient.listener.MealListener;
import com.agh.riceitclient.util.DrawerAdapter;
import com.agh.riceitclient.util.DrawerItem;
import com.agh.riceitclient.util.FoodAddTransfer;
import com.agh.riceitclient.util.FoodUpdateTransfer;
import com.agh.riceitclient.util.IdTransfer;
import com.agh.riceitclient.util.SimpleItem;
import com.agh.riceitclient.util.SpaceItem;
import com.agh.riceitclient.listener.SportListener;
import com.agh.riceitclient.util.SportUpdateTransfer;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener,
        MealListener,
        UserDetailsListener,
        SportListener,
        GoalListener {

    private static final int POS_CLOSE = 0;
    private static final int POS_MEAL = 1;
    private static final int POS_DETAILS = 2;
    private static final int POS_GOAL = 3;
    private static final int POS_SPORT = 4;
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
        toolbar.setTitle("");
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
                createItemFor(POS_MEAL).setChecked(true),
                createItemFor(POS_DETAILS),
                createItemFor(POS_GOAL),
                createItemFor(POS_SPORT),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));

        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_MEAL);
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

    public void popBackStackTillEntry(int entryIndex){
        if (getSupportFragmentManager() == null){
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() <= entryIndex){
            return;
        }
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(entryIndex);
        if (entry != null){
            getSupportFragmentManager().popBackStackImmediate(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_MEAL) {
            MealFragment mealFragment = new MealFragment();
            transaction.replace(R.id.main_container, mealFragment, "mealFragment");
        }
        else if (position == POS_DETAILS) {
            UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
            transaction.replace(R.id.main_container, userDetailsFragment, "userDetailsFragment");
        }
        else if (position == POS_GOAL) {
            GoalFragment goalFragment = new GoalFragment();
            transaction.replace(R.id.main_container, goalFragment, "goalFragment");
        }
        else if (position == POS_SPORT) {
            SportFragment sportFragment = new SportFragment();
            transaction.replace(R.id.main_container, sportFragment, "sportFragment");
        }
        else if (position == POS_LOGOUT) {
            finish();
        }

        popBackStackTillEntry(0);

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void enqueueUpdateFood(FoodUpdateTransfer foodUpdateTransfer) {
        MealFragment f = (MealFragment)getSupportFragmentManager().findFragmentByTag("mealFragment");
        f.enqueueUpdateFood(foodUpdateTransfer);
    }

    @Override
    public void enqueueAddFood(FoodAddDTO foodAddDTO) {
        MealFragment f = (MealFragment)getSupportFragmentManager().findFragmentByTag("mealFragment");
        f.enqueueAddFood(foodAddDTO);
    }

    @Override
    public void removeMealOrFood(boolean isMeal, long dataToRemove) {
        MealFragment f = (MealFragment)getSupportFragmentManager().findFragmentByTag("mealFragment");
        f.removeMealOrFood(isMeal, dataToRemove);
    }

    @Override
    public void enqueueCreateMeal() {
        MealFragment f = (MealFragment)getSupportFragmentManager().findFragmentByTag("mealFragment");
        f.enqueueCreateMeal();
    }

    @Override
    public void enqueueUpdateUserDetails(UserDetailsUpdateDTO userDetailsUpdateDTO) {
        UserDetailsFragment f = (UserDetailsFragment)getSupportFragmentManager().findFragmentByTag("userDetailsFragment");
        f.enqueueUpdateUserDetails(userDetailsUpdateDTO);
    }

    @Override
    public void enqueueAddSport(SportAddDTO sportAddDTO) {
        SportFragment f = (SportFragment) getSupportFragmentManager().findFragmentByTag("sportFragment");
        f.enqueueAddSport(sportAddDTO);
    }

    @Override
    public void enqueueRemoveSport(IdTransfer idTransfer) {
        SportFragment f = (SportFragment) getSupportFragmentManager().findFragmentByTag("sportFragment");
        f.enqueueRemoveSport(idTransfer);
    }

    @Override
    public void enqueueUpdateSport(SportUpdateTransfer sportUpdateTransfer) {
        SportFragment f = (SportFragment) getSupportFragmentManager().findFragmentByTag("sportFragment");
        f.enqueueUpdateSport(sportUpdateTransfer);
    }

    @Override
    public void enqueueUpdateManualParameters(ManualParametersDTO manualParametersDTO) {
        GoalFragment f = (GoalFragment) getSupportFragmentManager().findFragmentByTag("goalFragment");
        f.enqueueUpdateManualParameters(manualParametersDTO);
    }
}
