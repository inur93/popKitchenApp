package dk.pop.kitchenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.EditText;

import com.androidquery.AQuery;

public class PrimaryActivity extends AppCompatActivity implements View.OnClickListener{

    private AQuery aq;
    private EditText groupCreationSearchField;
    private ListViewCompat groupCreationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        aq = new AQuery(this);
        groupCreationSearchField = aq.id(R.id.groupCreationSearchField).getEditText();
        aq.id(R.id.groupCreationCreateBtn).clicked(this);
        aq.id(R.id.groupCreationSearchBtn).clicked(this);
        aq.id(R.id.groupCreationListView).getListView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.groupCreationSearchBtn:
                break;
            case R.id.groupCreationCreateBtn:
                break;
        }
    }
}
