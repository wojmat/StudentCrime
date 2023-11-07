package pl.edu.uwr.pum.studentcrimeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Crime mCurrentCrime;


    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get().getCrimes();

        FragmentManager manager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }


            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.crime_pager_activity_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem i) {

        switch (i.getItemId()) {

            case R.id.next_crime:
                int last = mCrimes.size();
                mViewPager.setCurrentItem(last-1);
                mCurrentCrime = mCrimes.get(last-1);
                break;

            case R.id.prev_crime:
                mViewPager.setCurrentItem(0);
                mCurrentCrime = mCrimes.get(0);
                break;

            case R.id.delete_crime:
                int id = mViewPager.getCurrentItem();
                mCurrentCrime = mCrimes.get(id);
                CrimeLab.get().removeCrime(mCurrentCrime);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(i);
        }

        return true;
    }
}
