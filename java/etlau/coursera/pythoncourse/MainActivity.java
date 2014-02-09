package etlau.coursera.pythoncourse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static ActionBar actionBar;
    private static boolean isBackStackNeeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);



        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_2,
                        android.R.id.text1,
                        getResources().getStringArray(R.array.titles)),
                this);

        actionBar.setSelectedNavigationItem(savedInstanceState != null
                ? savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM)
                : 0);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
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
        if (id == R.id.action_homepage) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://younglinux.info"));
            startActivity(browserIntent);
        } else if (id == R.id.action_penguins_page) {
            // thanks to Creative, Serious and Playful Science of Android Apps by Lawrence Angrave class
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://openclipart.org/collection/collection-detail/Moini/7245"));
            startActivity(browserIntent);
        } else if (id == R.id.action_next) {
            ActionBar actionBar = getSupportActionBar();
            int maxElem = actionBar.getNavigationItemCount();
            int current = actionBar.getSelectedNavigationIndex();
            if (current+1 < maxElem) actionBar.setSelectedNavigationItem(current + 1);
        } else {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setSelectedNavigationItem(actionBar.getSelectedNavigationIndex() - 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        if (isBackStackNeeded) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .addToBackStack("")
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
        isBackStackNeeded = true;
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.frame_main, container, false);
            WebView webView = (WebView) rootView.findViewById(R.id.section_label);
            webView.getSettings().setBuiltInZoomControls(true);
            String current = getResources().getStringArray(R.array.titles)[getArguments().getInt(ARG_SECTION_NUMBER) - 1];
            String filename = current.substring(0, current.indexOf('.'));
            webView.loadUrl(String.format("file:///android_asset/younglinux.info/book/export/html/%s.html", filename));
            return rootView;
        }
    }

}
