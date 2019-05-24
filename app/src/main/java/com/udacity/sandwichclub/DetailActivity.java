package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mDescriptionTextView;
    TextView mIngredientsTextView;
    TextView mAlsoKnownAsTextView;
    TextView mPlaceOfOriginTextView;

    Sandwich mSandwichData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        mPlaceOfOriginTextView = (TextView) findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        mSandwichData = JsonUtils.parseSandwichJson(json);
        if (mSandwichData == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwichData.getImage())
                .into(ingredientsIv);

        setTitle(mSandwichData.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mDescriptionTextView.setText(mSandwichData.getDescription());
        mPlaceOfOriginTextView.setText(mSandwichData.getPlaceOfOrigin());

        int numNames = mSandwichData.getAlsoKnownAs().size();
        for(int i = 0; i < numNames; i++)
        {
            String endOfLine = i < numNames - 1 ? "\n" : "";
            mAlsoKnownAsTextView.append(mSandwichData.getAlsoKnownAs().get(i) + endOfLine);
        }

        int numIngredients = mSandwichData.getIngredients().size();
        for(int i = 0; i < numIngredients; i++)
        {
            String endOfLine = i < numIngredients - 1 ? "\n" : "";
            mIngredientsTextView.append(mSandwichData.getIngredients().get(i) + endOfLine);
        }
    }
}
