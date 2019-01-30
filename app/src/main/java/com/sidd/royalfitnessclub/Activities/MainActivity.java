package com.sidd.royalfitnessclub.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sidd.royalfitnessclub.Fragments.BillingFragment;
import com.sidd.royalfitnessclub.Fragments.DietFragment;
import com.sidd.royalfitnessclub.Fragments.MainFragment;
import com.sidd.royalfitnessclub.Fragments.ProfileFragment;
import com.sidd.royalfitnessclub.Fragments.TrainingScheduleFragment;
import com.sidd.royalfitnessclub.R;
import com.sidd.royalfitnessclub.Utils.CrossFadeWrapper;

public class MainActivity extends AppCompatActivity {
    private Drawer drawer = null;
    private AccountHeader accountHeader = null;
    private MiniDrawer miniDrawer = null;
    private Crossfader crossfader;
    private FragmentManager fragmentManager;
    public Fragment fragment;
    public Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    private static ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        progressDialog = new ProgressDialog(this);
        showDialog();

        final IProfile profile = new ProfileDrawerItem()
                .withName("Siddhant")
                .withEmail("siddhanta.ghosh.5@gmail.com");

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTranslucentStatusBar(false)
                .withSelectionListEnabled(false)
                .withSavedInstance(savedInstanceState)
                .addProfiles(profile)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_verified_user).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Diet").withIcon(GoogleMaterial.Icon.gmd_free_breakfast).withIdentifier(2),
                        new PrimaryDrawerItem().withName("Training Schedule").withIcon(GoogleMaterial.Icon.gmd_perm_contact_calendar).withIdentifier(3),
                        new SectionDrawerItem().withName("Profile Settings"),
                        new PrimaryDrawerItem().withName("Billing").withIcon(GoogleMaterial.Icon.gmd_verified_user).withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1) {
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.crossfade_content, new ProfileFragment())
                                    .addToBackStack(null)
                                    .commitAllowingStateLoss();
                        } else if (drawerItem.getIdentifier() == 2) {
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.crossfade_content, new DietFragment())
                                    .addToBackStack(null)
                                    .commitAllowingStateLoss();
                        } else if (drawerItem.getIdentifier() == 3) {
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.crossfade_content, new TrainingScheduleFragment())
                                    .addToBackStack(null)
                                    .commitAllowingStateLoss();
                        } else if (drawerItem.getIdentifier() == 4) {
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.crossfade_content, new BillingFragment())
                                    .addToBackStack(null)
                                    .commitAllowingStateLoss();
                        }
                        return false;
                    }
                })
                .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                .buildView();

        miniDrawer = drawer.getMiniDrawer();

        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel( 75, this);

        crossfader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(drawer.getSlider(), firstWidth)
                .withSecond(miniDrawer.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossFadeWrapper(crossfader));
        crossfader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.crossfade_content, new MainFragment())
                    .commit();
        }
        hideDialog();
    }

    private void hideDialog() {
        if (progressDialog.isShowing() && getWindow().getDecorView().isShown())
        {
            progressDialog.hide();
        }
    }

    private void showDialog(){
        if (!progressDialog.isShowing() && getWindow().getDecorView().isShown())
        {
            progressDialog.show();
        }

    }

}
