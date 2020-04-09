package com.example.module_home.app.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.base.BaseActivity1;
import com.example.lib.base.BaseFragment;
import com.example.lib.base.BaseFragmentActivity;
import com.example.lib_resource.utils.ARouterConstants;
import com.example.module_home.R;
import com.example.module_home.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterConstants.Home_Activity)
public class HomeActivity extends BaseFragmentActivity<ActivityHomeBinding> {
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private ViewPager vp_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mFragments = new ArrayList<>(3);
        BaseFragment homePageMainFragment = (BaseFragment) ARouter.getInstance().build(ARouterConstants.Home_Page_Main_Fragment).navigation();
        BaseFragment homePageMainFragment1 = (BaseFragment) ARouter.getInstance().build(ARouterConstants.Home_Page_Main_Fragment).navigation();
        BaseFragment personalMainFragment = (BaseFragment) ARouter.getInstance().build(ARouterConstants.Personal_Main_Fragment).navigation();
        mFragments.add(homePageMainFragment);
        mFragments.add(homePageMainFragment1);
        mFragments.add(personalMainFragment);
////        mFragments.add(BlankFragment.newInstance("记录"));
////        mFragments.add(BlankFragment.newInstance("账号"));
//        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        vp_fragment=mBinding.fragmentVp;
        vp_fragment.setAdapter(mAdapter);
//        // register listener
        mBinding.fragmentVp.addOnPageChangeListener(mPageChangeListener);
        mBinding.tabsRg.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton)mBinding.tabsRg.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mBinding.fragmentVp.setCurrentItem(i);
                    return;
                }
            }
        }
    };
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.fragmentVp.removeOnPageChangeListener(mPageChangeListener);
    }
}
