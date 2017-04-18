package com.applab.model;

import android;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SlimStampen ss;
    private Trial trial;
    private EditText translation;
    private String tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Swahili - English word list
        final WordList swen = new WordList().loadFromResource(this, R.raw.swen);

        // Create a new SlimStampen model
        final SlimStampen model = new SlimStampen(swen);

        // Get the next trial
        trial = model.nextTrial();

        showTrial(trial);

        final Context context = getApplicationContext();
        translation = (EditText)findViewById(R.id.translationText);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tr = translation.getText().toString();
                boolean response = model.giveTranslation(tr);
                translation.setText("");

                if (trial.isStudyTrial()) {
                    if (response) {
                        Toast.makeText(context, "Learned", Toast.LENGTH_LONG).show();
                        trial = model.nextTrial();
                        showTrial(trial);
                    } else Toast.makeText(context, "Type again", Toast.LENGTH_LONG).show();
                } else {
                    if (response) {
                        Toast.makeText(context, "Correct", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(context, "Incorrect", Toast.LENGTH_LONG).show();
                    trial = model.nextTrial();
                    showTrial(trial);
                }

            }
        });

        EditText wl = (EditText)findViewById(R.id.editWordList);

        for ( int i = 0; i < swen.getItemCount(); i++ ) {
            Item item = swen.getItem(i);
            wl.append(item.getWord() + "-" + item.getTranslation() + " " + item.getActivation() + "\n");
        }

    }

    private void showTrial(Trial trial) {
        TextView tSwahili = (TextView)findViewById(R.id.textSwahili);
        tSwahili.setText( trial.getType().toString() + ": " +
                trial.getItem().getWord() + " = " +
                (trial.isStudyTrial() ? trial.getItem().getTranslation() : "") + " (" +
                trial.getItem().getActivation() + ")"
        );
    }




}
